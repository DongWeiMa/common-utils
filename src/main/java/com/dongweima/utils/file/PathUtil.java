package com.dongweima.utils.file;

/**
 * @author dongweima
 */
public class PathUtil {

  private static String baseDir;

  private PathUtil() {

  }

  public static String getBaseDir() {
    if (baseDir == null) {
      baseDir = PathUtil.class.getResource("/").getPath();
    }
    return baseDir;
  }
}
