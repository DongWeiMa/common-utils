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
   * 单元格所属于的列的名称,也可以是名称的一部分.
   * 第一个名称在导出时作为列名.
   */
  String[] name() default {};
}
