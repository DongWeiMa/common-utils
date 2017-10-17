package com.dongweima.utils.excel

import com.dongweima.utils.excel.annotion.ExcelCell
import com.dongweima.utils.excel.annotion.ExcelSheet

@ExcelSheet(name = ["老师", "教师"])
class Teacher {
  @ExcelCell(name = ["姓名", "名字"])
  private String name
  @ExcelCell(name = ["手机号码1", "手机号码一"])
  private String phone1
  @ExcelCell(name = ["手机号码2", "手机号码二"])
  private String phone2
  @ExcelCell(name = "性别")
  private String sex
  @ExcelCell(name = "生日")
  private String birth
  @ExcelCell(name = "工号")
  private String number

  String getName() {
    return name
  }

  void setName(String name) {
    this.name = name
  }

  String getPhone1() {
    return phone1
  }

  void setPhone1(String phone1) {
    this.phone1 = phone1
  }

  String getPhone2() {
    return phone2
  }

  void setPhone2(String phone2) {
    this.phone2 = phone2
  }

  String getSex() {
    return sex
  }

  void setSex(String sex) {
    this.sex = sex
  }

  String getBirth() {
    return birth
  }

  void setBirth(String birth) {
    this.birth = birth
  }

  String getNumber() {
    return number
  }

  void setNumber(String number) {
    this.number = number
  }
}
