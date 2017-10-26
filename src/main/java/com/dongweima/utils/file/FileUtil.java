package com.dongweima.utils.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dongweima
 */
@SuppressWarnings("unused")
public class FileUtil {

  private static Logger logger = LoggerFactory.getLogger(FileUtil.class);

  private FileUtil() {
  }

  public static LinkedList<String> getLines(String filePath) {
    LinkedList<String> list = new LinkedList<>();
    FileInputStream reader = null;
    BufferedReader br = null;
    InputStreamReader isr = null;
    try {
      reader = new FileInputStream(filePath);
      isr = new InputStreamReader(reader, "UTF-8");
      br = new BufferedReader(isr);
      String value;
      while ((value = br.readLine()) != null) {
        if (!value.trim().startsWith("#") && !"".equals(value.trim())) {
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

  /**
   * todo 目录名称之后改成可配置
   */
  public static List<String> getFilePaths() {
    File now = new File(PathUtil.getBaseDir(), "data");
    return getFilePaths(now.getPath());
  }

  public static List<String> getFilePaths(String path) {
    List<String> list = new LinkedList<>();
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

  /**
   * @param source jar中的文件  文件路径使用相对路径 以/开头 /a/test.txt
   * @param dest 文件路径使用相对路径 以不以/开头 如a/test.txt
   */
  public static void copyFileInJarToClasspath(String source, String dest) throws IOException {
    InputStream in = null;
    FileOutputStream fo = null;
    File file = new File(PathUtil.getBaseDir(), dest);
    if (!file.exists()) {
      createFileInClassPath(file.getPath());
      try {
        in = FileUtil.class.getResourceAsStream(source);
        fo = new FileOutputStream(file);
        int i;
        while ((i = in.read()) != -1) {
          fo.write(i);
        }
      } catch (Throwable e) {
        logger.error("copy failed", e);
      } finally {
        if (in != null) {
          try {
            in.close();
          } catch (Exception e) {
            logger.error("close failed", e);
          }
        }
        if (fo != null) {
          try {
            fo.close();
          } catch (Exception e) {
            logger.error("close failed", e);
          }
        }
      }
    }
  }

  /**
   * @param filePath 使用相对路径 如a/test.txt
   */
  public static void createFileInClassPath(String filePath) throws IOException {
    //创建目录以及文件
    int i = -1;
    int j;
    while ((j = filePath.indexOf(File.separator, i + 1)) > 0) {
      File file = new File(filePath.substring(0, j));
      if (!file.exists()) {
        boolean ok = file.mkdir();
        if (!ok) {
          throw new IOException("文件夹创建失败" + file.getPath());
        }
      }
      i = j;
    }
    File file = new File(filePath);
    if (!file.exists()) {
      boolean ok = file.createNewFile();
      if (!ok) {
        throw new IOException("文件创建失败" + filePath);
      }
    }
  }

  public static void main(String[] args) throws IOException {
    FileUtil.createFileInClassPath("a/b/c/d/a.txt");
  }
}
