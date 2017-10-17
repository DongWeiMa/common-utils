package com.dongweima.utils.excel

import com.dongweima.utils.file.PathUtil
import org.junit.Test
import spock.lang.Specification

class ExcelUtilTest extends Specification {

  @Test
  void testReadXlsx() {
    given:
    List<Class> list = new LinkedList<>()
    list.add(Student.class)
    list.add(Teacher.class)
    File file = new File(PathUtil.getBaseDir(), "金山初中通讯录.xlsx")
    when:
    Map<String, Set<List>> result = ExcelUtil.readExcel(file.getPath(), list)
    Set<List<Student>> s = result.get(Student.class.getName())
    Set<List<Teacher>> t = result.get(Teacher.class.getName())
    then:
    result.keySet().size() == 2
    s.size() == 1
    s.getAt(0).toArray().size() == 1582
    t.size() == 1
    t.getAt(0).toArray().size() == 128
  }

  @Test
  void testReadXls() {
    given:
    List<Class> list = new LinkedList<>()
    list.add(Student.class)
    list.add(Teacher.class)
    File file = new File(PathUtil.getBaseDir(), "浦江县中余乡中心小学.xls")

    when:
    Map<String, Set<List>> result = ExcelUtil.readExcel(file.getPath(), list)
    Set<List<Student>> s = result.get(Student.class.getName())
    Set<List<Teacher>> t = result.get(Teacher.class.getName())

    then:
    result.keySet().size() == 2
    s.size() == 1
    s.getAt(0).toArray().size() == 465
    t.size() == 1
    t.getAt(0).toArray().size() == 32
  }

  @Test
  void testExportXls() {
    given:
    List<Student> list = new LinkedList<>()
    for (int i = 0; i < 100; i++) {
      Student student = new Student();
      student.setPhone1(i + "")
      student.setPhone2(i + "")
      list.add(student)
    }
    File file = new File(PathUtil.getBaseDir(), "test.xls")
    when:
    ExcelUtil.listExport(list, file.getPath())
    then:
    file.exists()
  }

  @Test
  void testExportXlsx() {
    given:
    List<Student> list = new LinkedList<>()
    for (int i = 0; i < 100; i++) {
      Student student = new Student();
      student.setPhone1(i + "")
      student.setPhone2(i + "")
      list.add(student)
    }
    File file = new File(PathUtil.getBaseDir(), "test.xlsx")
    when:
    ExcelUtil.listExport(list, file.getPath())
    then:
    file.exists()
  }

  private List<Student> getStudents() {

    return students
  }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme