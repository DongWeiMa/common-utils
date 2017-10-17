package com.dongweima.utils.excel.annotion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记单元格.
 *
 * @author dongweima
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface ExcelCell {

  /**
   * 单元格所属于的列的名称
   */
  String[] name() default {};

  /**
   * 单元格所属列的名称的一部分内容. 在解析excel时用来匹配
   */
  String[] contains() default {};
}
