package com.dongweima.utils.db.hbase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Qualifier {

  private String name;
  private String value;

}
