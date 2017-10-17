package com.dongweima.utils.string;

/**
 * string 工具类.
 *
 * @author dongweima
 */
@SuppressWarnings("unused")
public class StringUtil {

  /**
   * 将string的第一个字符大写.
   */
  public static String firstCharToUpCase(String s) {
    if (s == null || s.length() == 0) {
      return s;
    }
    if (s.length() == 1) {
      return s.toUpperCase();
    }
    return s.substring(0, 1).toUpperCase()
        + s.substring(1, s.length());
  }
}
