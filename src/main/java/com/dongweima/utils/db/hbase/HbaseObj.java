package com.dongweima.utils.db.hbase;

import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;

public class HbaseObj {

  private String tableName;
  private Map<String, Family> families;
  private String startRow;
  private String endRow;
  private Filter filter;
  private boolean isReversed = false;

  public HbaseObj() {
    families = new HashMap<String, Family>();
  }

  public boolean getIsReversed() {
    return isReversed;
  }

  public void setReversed(boolean reversed) {
    isReversed = reversed;
  }

  public HbaseObj buildFamily(String family) {
    families.put(family, new Family(family));
    return this;
  }

  public HbaseObj buildQulifier(String familyName, String qulifierName) {
    Family family = families.get(familyName);
    if (family == null) {
      family = new Family(familyName);
      families.put(familyName, family);
    }
    family.addQualifier(qulifierName);
    return this;
  }

  public HbaseObj buildTableName(String tableName) {
    this.tableName = tableName;
    return this;
  }

  public HbaseObj buildStartRow(String startRow) {
    this.startRow = startRow;
    return this;
  }

  public HbaseObj buildEndRow(String endRow) {
    this.endRow = endRow;
    return this;
  }

  public HbaseObj buildFilter(Filter filter) {
    if (filter == null) {
      return this;
    }
    if (filter instanceof FilterList) {
      this.filter = filter;
    } else if (this.filter != null) {
      ((FilterList) this.filter).addFilter(filter);
    } else {
      this.filter = new FilterList(filter);
    }
    return this;
  }

  public HbaseObj buildIsReversed(boolean isReversed) {
    this.isReversed = isReversed;
    return this;
  }

  public Filter getFilter() {
    return filter;
  }

  public void setFilter(Filter filter) {
    this.filter = filter;
  }

  public List<Family> getFamilies() {
    return new LinkedList<Family>(families.values());
  }

  public void setFamilies(List<Family> families) {
    for (Family family : families) {
      this.families.put(family.getName(), family);
    }
  }

  public String getTableName() {
    return tableName;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }


  public String getStartRow() {
    return startRow;
  }

  public void setStartRow(String startRow) {
    this.startRow = startRow;
  }

  public String getEndRow() {
    return endRow;
  }

  public void setEndRow(String endRow) {
    this.endRow = endRow;
  }

  @Override
  public String toString() {
    return JSONObject.toJSONString(this);
  }
}
