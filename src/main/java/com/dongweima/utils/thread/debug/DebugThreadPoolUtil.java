package com.dongweima.utils.thread.debug;

import com.dongweima.utils.thread.DwmThreadFactory;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author dongweima
 */
public class DebugThreadPoolUtil {

  private static final String PREFIX = "debug";
  private static final int CORE_POOL_SIZE = 40;
  private static final int MAXIMUM_POOL_SIZE = 80;
  private static final long KEEP_ALIVE_TIME = 100L;
  private static ThreadPoolExecutor pool;

  public static synchronized ThreadPoolExecutor getThreadPool() {
    if (pool == null) {
      LinkedBlockingDeque<Runnable> linkedBlockingDeque = new LinkedBlockingDeque<>();
      ThreadFactory threadFactory = new DwmThreadFactory(PREFIX);
      pool = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_TIME,
          TimeUnit.SECONDS, linkedBlockingDeque, threadFactory);
    }
    return pool;
  }
}
