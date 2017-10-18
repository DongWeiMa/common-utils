package com.dongweima.utils.db.hbase;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author dongweima
 */
@SuppressWarnings("unused")
@ToString
@EqualsAndHashCode
public class Row {

  private String rowKey;
  private Map<String, Family> families;

  public Row() {
    families = new HashMap<>();
  }

  public String getRowKey() {
    return rowKey;
  }

  public void setRowKey(String rowKey) {
    this.rowKey = rowKey;
  }

  public Family getFamily(String family) {
    if (families == null) {
      families = new HashMap<>(16);
    }
    return families.get(family);
  }

  /**
   * todo 这里最好copy一下,而不是直接暴露
   */
  public List<Family> getFamilies() {
    return new LinkedList<>(families.values());
  }

  public void addFamily(Family family) {
    String familyName = family.getName();
    if (familyName == null) {
      return;
    }
    families.put(familyName, family);
  }

  public void setCellValue(String familyName, String qualifierName, String value) {
    Family family = families.get(familyName);
    if (family == null) {
      family = new Family(familyName);
      families.put(familyName, family);
    }
    Qualifier qualifier = family.getQualifier(qualifierName);
    if (qualifier == null) {
      qualifier = new Qualifier(qualifierName, null);
      family.addQualifier(qualifier);
    }
    qualifier.setValue(value);
  }

  public String getCellValue(String familyName, String qualifierName) {
    String value;
    try {
      value = getFamily(familyName).getQualifier(qualifierName).getValue();
    } catch (Exception e) {
      value = null;
    }
    return value;
  }

  public Row shallowCopy(){
    Row row = new Row();
    row.families = this.families;
    row.rowKey = rowKey;
    return row;
  }
}
