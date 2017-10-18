package com.dongweima.utils.log;

import com.dongweima.utils.file.PathUtil;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dongweima
 */
@SuppressWarnings("unused")
public class ScriptLogUtil {

  private static final Logger LOGGER = LoggerFactory.getLogger(ScriptLogUtil.class);
  private static final String INFO = "file.log";
  private static final String ERROE = "error.log";
  private static final String WARN = "warn.log";

  static {
    File file = new File(PathUtil.getBaseDir(), "0log");
    if (!file.exists()) {
      try {
        boolean ok = file.mkdir();
        if (!ok) {
          throw new IOException("文件创建失败" + file.getPath());
        }
      } catch (Exception e) {
        LOGGER.warn(e.getMessage(), e);
      }
    }
  }

  public static void write(String message, String fileName) {
    FileWriter writer = null;
    try {
      File file = new File(PathUtil.getBaseDir() + "/0log", fileName);
      if (!file.exists()) {
        boolean ok = file.createNewFile();
        if (!ok) {
          throw new IOException("文件创建失败" + file.getPath());
        }
      }
      writer = new FileWriter(file.getPath(), true);
      writer.write(message + "\t\r");
    } catch (IOException e) {
      LOGGER.warn(e.getMessage(), e);
    } finally {
      if (writer != null) {
        try {
          writer.close();
        } catch (Exception e) {
          LOGGER.warn(e.getMessage(), e);
        }
      }
    }
  }

  public static void error(String message) {
    write(message, ERROE);
  }

  public static void warn(String message) {
    write(message, WARN);
  }

  public static void info(String message) {
    write(message, INFO);
  }
}
