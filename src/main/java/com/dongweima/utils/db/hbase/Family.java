package com.dongweima.utils.db.hbase;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 列簇.
 *
 * @author dongweima
 */
@SuppressWarnings("unused")
@ToString
@EqualsAndHashCode
public class Family {

  private String name;
  private Map<String, Qualifier> qualifiers;

  public Family() {
    qualifiers = new HashMap<>();
  }

  public Family(String familyName) {
    qualifiers = new HashMap<>();
    name = familyName;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Qualifier getQualifier(String qualifierName) {
    return qualifiers.get(qualifierName);
  }

  /**
   * 添加列.
   *
   * @param qualifierName 列名
   */
  public void addQualifier(String qualifierName) {
    Qualifier qualifier = qualifiers.get(qualifierName);
    if (qualifier == null) {
      qualifier = new Qualifier(qualifierName, null);
      qualifiers.put(qualifierName, qualifier);
    }
  }

  /**
   * 添加列.
   * @param qualifier 列
   */
  public void addQualifier(Qualifier qualifier) {
    if (qualifier == null) {
      return;
    }
    qualifiers.putIfAbsent(qualifier.getName(), qualifier);
  }

  public List<Qualifier> getQualifiers() {
    return new LinkedList<>(qualifiers.values());
  }
}
