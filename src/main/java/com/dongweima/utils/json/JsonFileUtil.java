package com.dongweima.utils.json;

import com.alibaba.fastjson.JSONObject;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class JsonFileUtil {

  //把json格式的字符串写到文件
  public static void writeJsonFile(String filePath, String sets) throws IOException {
    FileWriter fw = null;
    PrintWriter out = null;
    try {
      fw = new FileWriter(filePath);
      out = new PrintWriter(fw);
      out.write(sets);
      out.println();

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (fw != null) {
        fw.close();
      }
      if (out != null) {
        out.close();
      }
      ;
    }

  }

  public static JSONObject readJsonFile(String filePath) {
    return JSONObject.parseObject(readFile(filePath));
  }

  public static String readFile(String path) {
    BufferedReader reader = null;
    String laststr = "";
    try {
      FileInputStream fileInputStream = new FileInputStream(path);
      InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
      reader = new BufferedReader(inputStreamReader);
      String tempString = null;
      while ((tempString = reader.readLine()) != null) {
        laststr += tempString;
      }
      reader.close();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (reader != null) {
        try {
          reader.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return laststr;
  }
}

