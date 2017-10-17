package com.dongweima.utils.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class LogFactory {

  private static String APP_NAME;

  public Logger getLog(Class clazz, String logType) {
    String name = APP_NAME + "_" + logType + "_" + clazz.getName();
    return LoggerFactory.getLogger(name);
  }

}
