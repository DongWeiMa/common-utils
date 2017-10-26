package com.dongweima.utils.thread;

import java.util.concurrent.ThreadFactory;

/**
 * @author dongweima
 */
@SuppressWarnings("unused")
public class DwmThreadFactory implements ThreadFactory {

  private int counter;
  private String prefix;

  public DwmThreadFactory(String prefix) {
    this.prefix = prefix;
    counter = 0;
  }

  @Override
  public Thread newThread(Runnable runnable) {
    counter++;
    return new DwmThread(runnable, prefix + "-" + counter);
  }

}
