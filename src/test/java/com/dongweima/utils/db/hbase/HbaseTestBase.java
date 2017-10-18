package com.dongweima.utils.db.hbase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.List;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseTestingUtility;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.PageFilter;
import org.junit.BeforeClass;
import org.junit.Test;

public class HbaseTestBase {

  private static final HBaseTestingUtility testUtil = new HBaseTestingUtility();

  private static final String user_group = "user_group:user_event";
  private static BaseHbase hbaseBase;
  private static Configuration conf;

  @BeforeClass
  public static void beforeClass() throws Throwable {
    System.setProperty("hadoop.home.dir", "d:\\winutil\\");
    testUtil.startMiniCluster();
    conf = testUtil.getConfiguration();
    hbaseBase = new BaseHbase() {
      @Override
      public Configuration getConf() {
        return conf;
      }
    };
    conf = hbaseBase.getConf();
    hbaseBase.createNameSpace("user_group");
    String[] families = {"user", "1", "2", "3", "4", "5"};
    hbaseBase.createTable("user_group:user_event", families);
    hbaseBase.createTable("test", families);
    //生成数据
    createData();
  }

  /**
   * 数据说明 3个事件 事件元数据定义在mysql表中 分别是 阅读事件，点击事件 这两种元事件数据 2个阅读事件 两个点击事件 一个色彩事件的上层重复事件
   */
  private static void createData() throws Throwable {
    Long groupId = 1L;
    for (int i = 0; i < 10; i++) {
      Row row = new Row();
      String userId = Integer.toString(i);
      row.setRowKey("000000000" + groupId + userId);
      row.setCellValue("user", "id", userId);
      row.setCellValue("user", "name", "userName" + userId);
      row.setCellValue("1", "code", Integer.toString(i % 2));
      row.setCellValue("2", "code", Integer.toString(i % 3));
      //为了测试默认值 默认只有i为偶数才有code
      if (i % 2 == 0) {
        row.setCellValue("3", "code", Integer.toString(i % 2));
      }
      hbaseBase.put(user_group, row);
    }
  }


  @Test
  public void testDropTable() throws Exception {
    hbaseBase.dropTable("test");
    assertEquals(false, hbaseBase.tableExist("test"));
  }

  @Test
  public void testCreateTable() throws Throwable {
    String tableName = "createTable";
    hbaseBase.createTable(tableName, new String[]{"families"});
    assertEquals(true, hbaseBase.tableExist(tableName));
  }

  @Test
  public void testTableExist() {
    assertEquals(false, hbaseBase.tableExist("hhhh"));
    assertEquals(true, hbaseBase.tableExist(user_group));
  }

  @Test
  public void testPut() throws Exception {
    Row row = new Row();
    String rowKey = "00000000091";
    row.setRowKey(rowKey);
    row.setCellValue("user", "id", Integer.toString(1));
    hbaseBase.put(user_group, row);
    HbaseObj obj = new HbaseObj()
        .buildQulifier("user", "id")
        .buildTableName(user_group)
        .buildStartRow(rowKey)
        .buildEndRow(rowKey);
    List<Row> list = hbaseBase.scan(obj);
    assertEquals(list.size(), 1);
  }

  @Test
  public void should_return_the_all_qualifier_in_the_family_when_scan() throws Exception {
    //选中 一个列簇 应当返回该列簇所有列
    HbaseObj obj = new HbaseObj()
        .buildTableName(user_group)
        .buildFamily("user")
        .buildStartRow("00000000011")
        .buildEndRow("00000000019");
    List<Row> result = hbaseBase.scan(obj);
    assertEquals(8, result.size());
    assertEquals(2, result.get(0).getFamilies().get(0).getQualifiers().size());
  }

  @Test
  public void should_return_one_qualifier_in_the_family_when_we_ask_one_qualifier_although_we_set_family()
      throws Exception {
    //选中 一个列簇 应当返回该列簇所有列
    HbaseObj obj = new HbaseObj()
        .buildTableName(user_group)
        .buildFamily("user")//这个是显式设定列簇 但是实际已经在qualifier设定的时候已经隐式设定了
        .buildQulifier("user", "id")
        .buildStartRow("00000000011")
        .buildEndRow("00000000019");
    List<Row> result = hbaseBase.scan(obj);
    assertEquals(8, result.size());
    assertEquals(1, result.get(0).getFamilies().get(0).getQualifiers().size());
  }

  @Test
  public void should_return_2_or_more_lines_when_i_user_page_filter_limit_2_and_no_set_end_row() {
    HbaseObj obj = new HbaseObj()
        .buildTableName(user_group)
        .buildFamily("user")//这个是显式设定列簇 但是实际已经在qualifier设定的时候已经隐式设定了
        .buildStartRow("00000000011")
        .buildFilter(new FilterList(new PageFilter(2)));
    List<Row> result = hbaseBase.scan(obj);
    assertFalse(2 > result.size());
    assertEquals("00000000011", result.get(0).getRowKey());
    assertEquals("00000000012", result.get(1).getRowKey());
  }

  @Test
  public void should_return_reversed_list_and_2_or_more_lines_when_i_use_page_filter_limit_2_and_set_isReversed_true() {
    HbaseObj obj = new HbaseObj()
        .buildTableName(user_group)
        .buildFamily("user")
        .buildStartRow("00000000013")
        .buildFilter(new FilterList(new PageFilter(2)))
        .buildIsReversed(true);
    List<Row> result = hbaseBase.scan(obj);
    PageFilter page = new PageFilter(10);
    assertFalse(2 > result.size());
    assertEquals("00000000013", result.get(0).getRowKey());
    assertEquals("00000000012", result.get(1).getRowKey());
  }

  @Test
  public void testCreateNameSpace() throws Exception {
    String name = "creatNamespace";
    hbaseBase.createNameSpace(name);
    assertEquals(true, hbaseBase.namespaceExist(name));
  }

  @Test
  public void testNamespaceExist() {
    String name = "user_group";
    assertEquals(true, hbaseBase.namespaceExist(name));
    assertEquals(false, hbaseBase.namespaceExist("hhhhh"));
  }
}
