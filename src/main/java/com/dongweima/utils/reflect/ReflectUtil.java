package com.dongweima.utils.reflect;

import com.dongweima.utils.string.StringUtil;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 反射工具.
 *
 * @author dongweima
 */
@SuppressWarnings("unused")
public class ReflectUtil {

  private static final Logger LOGGER = LoggerFactory.getLogger(ReflectUtil.class);
  private static final String GET = "get";
  private static final String SET = "set";
  private static final String IS = "is";

  /**
   * 获取field的属性值.
   */
  @SuppressWarnings("unChecked")
  public static Object getFieldValue(String fieldName, Object o)
      throws NoSuchFieldException, IllegalAccessException {
    Object key;
    try {
      String getter = getMethodName(o.getClass(), fieldName);
      Method method = o.getClass().getMethod(getter);
      key = method.invoke(o);
      return key;
    } catch (Exception e) {
      LOGGER.debug("getter反射失败");
      LOGGER.debug(e.getMessage());
    }

    try {
      Field field = o.getClass().getDeclaredField(fieldName);
      boolean accessFlag = field.isAccessible();
      field.setAccessible(true);
      key = field.get(o);
      field.setAccessible(accessFlag);
    } catch (Exception e) {
      LOGGER.debug(e.getMessage(), e);
      throw e;
    }
    return key;
  }

  /**
   * 直接写入,不用setter方法.
   */
  @SuppressWarnings("unchecked")
  public static void setFieldValueByName(String fieldName, Object o, Object param)
      throws NoSuchFieldException, IllegalAccessException {
    try {
      Class clazz = o.getClass();
      Method method = clazz.getMethod(setMethodName(clazz, fieldName));
      boolean accessFlag = method.isAccessible();
      method.setAccessible(true);
      method.invoke(o, param);
      method.setAccessible(accessFlag);
      return;
    } catch (Exception e) {
      LOGGER.debug("反射失败");
      LOGGER.debug(e.getMessage());
    }

    //失败则尝试使用field直接设置
    try {
      Field field = o.getClass().getDeclaredField(fieldName);
      boolean accessFlag = field.isAccessible();
      field.setAccessible(true);
      field.set(o, param);
      field.setAccessible(accessFlag);
    } catch (Exception e) {
      LOGGER.debug(e.getMessage(), e);
      throw e;
    }
  }

  /**
   * 获取get方法的名字. 一般情况是:field的第一个字符大写,前面加上get 特殊情况:is开头的情况. 对于is开头的情况.
   * 先尝试用field的名称获取其get方法,获取到则返回,否则执行第一种情况的处理方法.
   */
  @SuppressWarnings({"unchecked"})
  private static String getMethodName(Class clazz, String fieldName) {
    //如何处理is
    if (fieldName.startsWith(IS)) {
      try {
        Method method = clazz.getMethod(fieldName);
        if (method == null) {
          throw new RuntimeException();
        }
        return fieldName;
      } catch (Exception e) {
        LOGGER.debug("{} 该类的{}字段的get方法不是{}", clazz.getName(), fieldName, fieldName);
      }
    }
    return GET + StringUtil.firstCharToUpCase(fieldName);
  }

  @SuppressWarnings({"unchecked"})
  private static String setMethodName(Class clazz, String fieldName) {
    //如何处理is
    if (fieldName.startsWith(IS)) {
      try {
        Field field = clazz.getField(fieldName);
        Method method = clazz.getMethod(fieldName, field.getType());
        if (method == null) {
          throw new RuntimeException();
        }
        return fieldName;
      } catch (Exception e) {
        LOGGER.debug("{} 该类的{}字段的get方法不是{}", clazz.getName(), fieldName, fieldName);
      }
    }
    return SET + StringUtil.firstCharToUpCase(fieldName);
  }
}
