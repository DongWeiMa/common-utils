package com.dongweima.utils.excel.bean;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
 * @param <T> 是哪一种bean来存放一行excel数据.
 * @author dongweima
 */
public class Header<T> {

  private Class<T> clazz;
  /**
   * 记录列名和bean中属性的对应关系. 列名可能有多个.
   */
  private Map<String, String> map;
  private ArrayList<String> sheetNames;


  public ArrayList<String>  getSheetNames() {
    return sheetNames;
  }

  public void setSheetNames(ArrayList<String>  sheetNames) {
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
