package com.dongweima.utils.json;

public class JsonStringUtil {

  /**
   * 将字符串格式化成JSON的格式. 将一行的json变成展开的json
   */
  public static String stringToJson(String strJson) {
    // 计数tab的个数
    int tabNum = 0;
    StringBuilder jsonFormat = new StringBuilder();
    int length = strJson.length();

    char last = 0;
    for (int i = 0; i < length; i++) {
      char c = strJson.charAt(i);
      if (c == '{') {
        tabNum++;
        jsonFormat.append(c).append("\n");
        jsonFormat.append(getSpaceOrTab(tabNum));
      } else if (c == '}') {
        tabNum--;
        jsonFormat.append("\n");
        jsonFormat.append(getSpaceOrTab(tabNum));
        jsonFormat.append(c);
      } else if (c == ',') {
        jsonFormat.append(c).append("\n");
        jsonFormat.append(getSpaceOrTab(tabNum));
      } else if (c == ':') {
        jsonFormat.append(c).append(" ");
      } else if (c == '[') {
        tabNum++;
        char next = strJson.charAt(i + 1);
        if (next == ']') {
          jsonFormat.append(c);
        } else {
          jsonFormat.append(c).append("\n");
          jsonFormat.append(getSpaceOrTab(tabNum));
        }
      } else if (c == ']') {
        tabNum--;
        if (last == '[') {
          jsonFormat.append(c);
        } else {
          jsonFormat.append("\n").append(getSpaceOrTab(tabNum)).append(c);
        }
      } else {
        jsonFormat.append(c);
      }
      last = c;
    }
    return jsonFormat.toString();
  }

  private static String getSpaceOrTab(int tabNum) {
    StringBuilder sbTab = new StringBuilder();
    for (int i = 0; i < tabNum; i++) {
      sbTab.append('\t');
    }
    return sbTab.toString();
  }
}  