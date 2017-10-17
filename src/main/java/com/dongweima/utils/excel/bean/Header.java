package com.dongweima.utils.excel.bean;

import java.util.Map;
import java.util.Set;

public class Header<T> {

  private Class<T> clazz;
  //记录列名和bean中属性的对应关系
  //列名可能有多个
  private Map<String, String> map;
  private Set<String> sheetNames;

  public Set<String> getSheetNames() {
    return sheetNames;
  }

  public void setSheetNames(Set<String> sheetNames) {
    this.sheetNames = sheetNames;
  }

  public Map<String, String> getMap() {
    return map;
  }

  public void setMap(Map<String, String> map) {
    this.map = map;
  }

  public Class<T> getClazz() {
    return clazz;
  }

  public void setClazz(Class<T> clazz) {
    this.clazz = clazz;
  }
}