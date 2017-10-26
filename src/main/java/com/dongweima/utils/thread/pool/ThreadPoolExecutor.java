package com.dongweima.utils.thread.pool;

import java.util.HashSet;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @author dongweima
 */
public class ThreadPoolExecutor extends AbstractExecutorService {

  private final HashSet workers = new HashSet();
  private volatile int corePoolSize;
  private volatile int maxPoolSize;
  private volatile long keepAliveTime;
  private volatile RejectedExecutionHandler handler;
  private volatile boolean allowCoreThreadTimeOut;
  private volatile ThreadFactory threadFactory;
  /**
   * 简单一点是无界队列,但是无界队列的缺点很明显:高负荷下容易消耗完机器的资源. 因此强制有界队列.
   */
  private volatile BlockingQueue<Runnable> workQueue;

  /**
   * 创建线程池强制使用这种方案,不采用默认策略,毕竟自己用.
   */
  public ThreadPoolExecutor(
      int corePoolSize,
      int maxPoolSize,
      long keepAliveTime,
      TimeUnit unit,
      BlockingQueue<Runnable> workQueue,
      ThreadFactory threadFactory,
      RejectedExecutionHandler handler
  ) {
    if (corePoolSize < 0 ||
        maxPoolSize <= 0 ||
        maxPoolSize < corePoolSize ||
        keepAliveTime < 0) {
      throw new IllegalArgumentException();
    }
    if (workQueue == null || threadFactory == null || handler == null) {
      throw new NullPointerException();
    }
    if (workQueue.remainingCapacity() == Integer.MAX_VALUE) {
      throw new RuntimeException("不能使用无界队列");
    }
    this.corePoolSize = corePoolSize;
    this.maxPoolSize = maxPoolSize;
    this.keepAliveTime = unit.toNanos(keepAliveTime);
    this.workQueue = workQueue;
    this.threadFactory = threadFactory;
    this.handler = handler;
  }

  /**
   * @param command 任务
   */
  @Override
  public void execute(Runnable command) {
    //执行任务
    //这里变成放入任务,并初始化线程的操作
  }

  @Override
  public void shutdown() {

  }

  @Override
  public List<Runnable> shutdownNow() {
    return null;
  }

  @Override
  public boolean isShutdown() {
    return false;
  }

  @Override
  public boolean isTerminated() {
    return false;
  }

  @Override
  public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
    return false;
  }

  public ThreadFactory getThreadFactory() {
    return threadFactory;
  }

  @Override
  protected void finalize() {
    shutdown();
  }

  protected void addWorker() {

  }

  private final class Worker extends AbstractQueuedSynchronizer
      implements Runnable {

    final Thread thread;
    Runnable firstTask;
    Integer state;

    Worker(Runnable firstTask) {
      this.firstTask = firstTask;
      this.thread = getThreadFactory().newThread(this);
    }

    @Override
    public void run() {
      runWorker(this);
    }

    public void runWorker(Worker w) {
      Thread wt = Thread.currentThread();
      Runnable task = w.firstTask;
      w.firstTask = null;
      //关于锁
      //无限循环任务 从队列中拉取任务 然后执行.
      //如果中断,则禁止使用.
      //需要解决哪些问题
      //哪些东西是共享的
      //比如work的状态
      //还有就是继承的类的概念搞清楚
    }
  }
}
