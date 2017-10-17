package com.dongweima.utils.excel.annotion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记sheet.
 *
 * @author dongweima
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface ExcelSheet {

  /**
   * name sheet的名称.
   */
  String[] name() default {"default"};
}
