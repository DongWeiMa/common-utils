package com.dongweima.utils.excel.bean;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author dongweima
 */
@SuppressWarnings("unused")
public class ExportHeader<T> {

  private String sheetName;
  private Class<T> clazz;
  private LinkedHashMap<String, String> map;

  public String getSheetName() {
    return sheetName;
  }

  public void setSheetName(String sheetName) {
    this.sheetName = sheetName;
  }

  public Class<T> getClazz() {
    return clazz;
  }

  public void setClazz(Class<T> clazz) {
    this.clazz = clazz;
  }

  public LinkedHashMap<String, String> getMap() {
    return map;
  }

  public void setMap(LinkedHashMap<String, String> map) {
    this.map = map;
  }
}
