package com.dongweima.utils.thread.pool;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 当任务队列和线程池都满时,触发该类处理. 处理策略是,抛异常. ThreadPoolExecutor中有预定义 四种处理方式.这里选用直接抛出异常的方式.
 *
 * @author dongweima
 */
public class RejectedExecutionHandlerImpl implements RejectedExecutionHandler {

  @Override
  public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
    throw new RejectedExecutionException("Task " + r.toString() +
        " rejected from " +
        executor.toString());
  }
}
