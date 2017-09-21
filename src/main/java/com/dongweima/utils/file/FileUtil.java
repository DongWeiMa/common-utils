package com.dongweima.utils.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtil {

  private static Logger logger = LoggerFactory.getLogger(FileUtil.class);

  private FileUtil() {
  }

  public static LinkedList<String> getLines(String filePath) {
    LinkedList<String> list = new LinkedList<String>();
    FileInputStream reader = null;
    BufferedReader br = null;
    InputStreamReader isr = null;
    try {
      reader = new FileInputStream(filePath);
      isr = new InputStreamReader(reader, "UTF-8");
      br = new BufferedReader(isr);
      String value;
      while ((value = br.readLine()) != null) {
        if (!value.trim().startsWith("#") && !value.trim().equals("")) {
          list.add(value);
        }
      }
    } catch (Exception e) {
      logger.error("文件读取失败 path:{}", filePath, e);
    } finally {
      if (br != null) {
        try {
          br.close();
        } catch (Exception e) {
          logger.error("文件关闭失败 path:{}", filePath, e);
        }
      }
      if (isr != null) {
        try {
          isr.close();
        } catch (Exception e) {
          logger.error("文件关闭失败 path:{}", filePath, e);
        }
      }
      if (reader != null) {
        try {
          reader.close();
        } catch (Exception e) {
          logger.error("文件关闭失败 path:{}", filePath, e);
        }
      }
    }

    return list;
  }

  //todo 目录名称之后改成可配置
  public static List<String> getFilePaths() {
    File now = new File(PathUtil.getBaseDir(), "data");
    return getFilePaths(now.getPath());
  }

  public static List<String> getFilePaths(String path) {
    List<String> list = new LinkedList<String>();
    File now = new File(path);
    if (now.isDirectory()) {
      File[] files = now.listFiles();
      if (files != null) {
        for (File file : files) {
          list.addAll(getFilePaths(file.getPath()));
        }
      }
    } else {
      list.add(now.getPath());
    }
    return list;
  }


}
