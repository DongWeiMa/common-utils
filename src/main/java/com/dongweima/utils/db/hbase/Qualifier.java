package com.dongweima.utils.db.hbase;

public class Qualifier {

  private String name;
  private String value;

  public Qualifier() {

  }

  public Qualifier(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}
