package com.dongweima.utils.db.hbase;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Family {

  private String name;
  private Map<String, Qualifier> qualifiers;

  public Family() {
    qualifiers = new HashMap<String, Qualifier>();
  }

  public Family(String familyName) {
    qualifiers = new HashMap<String, Qualifier>();
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

  public void addQualifier(String qualifierName) {
    Qualifier qualifier = qualifiers.get(qualifierName);
    if (qualifier == null) {
      qualifier = new Qualifier(qualifierName);
      qualifiers.put(qualifierName, qualifier);
    }
  }

  public void addQualifier(Qualifier qualifier) {
    if (qualifiers.get(qualifier.getName()) == null) {
      qualifiers.put(qualifier.getName(), qualifier);
    }
  }

  public List<Qualifier> getQualifiers() {
    return new LinkedList<Qualifier>(qualifiers.values());
  }
}
