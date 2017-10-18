package com.dongweima.utils.check;

import java.util.regex.PatternSyntaxException;

/**
 * 手机号码校验类.
 *
 * @author dongweima
 */
@SuppressWarnings("unused")
public class PhoneFormatCheckUtils {

  private static final int THREE = 3;
  private static final int EIGHT = 8;
  private static final char ZERO = '0';
  private static final char NIGHT = '9';
  private static final int ELEVEN = 11;

  /**
   * 检查手机号是否违法.
   */
  @SuppressWarnings("unused")
  public static boolean isPhoneLegal(String str) {
    if (str == null) {
      return false;
    }
    str = str.trim();
    return isShortPhone(str) || isChinaPhoneLegal(str);
  }

  private static boolean isShortPhone(String str) {
    //短号4-7
    str = str.trim();
    if (str.length() > THREE && str.length() < EIGHT) {
      for (int i = 0; i < str.length(); i++) {
        char c = str.charAt(i);
        if (c < ZERO || c > NIGHT) {
          return false;
        }
      }
      return true;
    }
    return false;
  }

  /**
   * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数 此方法中前三位格式有： 13+任意数 15+除4的任意数 18+除1和4的任意数 17+除9的任意数 147.
   */
  private static boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {
    str = str.trim();
    if (str.length() == ELEVEN) {
      for (int i = 0; i < str.length(); i++) {
        char c = str.charAt(i);
        if (c < ZERO || c > NIGHT) {
          return false;
        }
      }
      return true;
    }
    return false;
  }
}

