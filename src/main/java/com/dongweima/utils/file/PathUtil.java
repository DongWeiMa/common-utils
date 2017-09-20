package com.dongweima.utils.file;

public class PathUtil {

  private static String baseDir;

  private PathUtil() {

  }

  public static String getBaseDir() {
    if (baseDir == null) {
      baseDir = PathUtil.class.getResource("/").getPath();
      //baseDir = baseDir.substring(5, baseDir.length())  ;
    }
    return baseDir;
  }
}
