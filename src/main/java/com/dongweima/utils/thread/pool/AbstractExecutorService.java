package com.dongweima.utils.thread.pool;

import com.sun.istack.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * todo 待完善. 主要用于进一步封装任务的执行. 可以用submit的方式提交任务. 也可以是execute直接提交任务. 可以批量提交任务.
 *
 * @author dongweima
 */
public abstract class AbstractExecutorService implements ExecutorService {

  /**
   * 内部都把runnable封装成callable. 提供自己封装转入,和帮你封装的两种方式.
   */
  protected <T> RunnableFuture<T> newTaskFor(Runnable runnable, T value) {
    return new FutureTask<T>(runnable, value);
  }

  protected <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
    return new FutureTask<T>(callable);
  }

  @Override
  public Future<?> submit(@NotNull Runnable task) {
    if (task == null) {
      throw new NullPointerException();
    }
    RunnableFuture<Void> ftask = newTaskFor(task, null);
    execute(ftask);
    return ftask;
  }

  @Override
  public <T> Future<T> submit(Runnable task, T result) {
    if (task == null) {
      throw new NullPointerException();
    }
    RunnableFuture<T> ftask = newTaskFor(task, result);
    execute(ftask);
    return ftask;
  }

  @Override
  public <T> Future<T> submit(Callable<T> task) {
    if (task == null) {
      throw new NullPointerException();
    }
    RunnableFuture<T> ftask = newTaskFor(task);
    execute(ftask);
    return ftask;
  }

  /**
   * the main mechanics of invokeAny.
   */
  private <T> T doInvokeAny(Collection<? extends Callable<T>> tasks,
      boolean timed, long nanos)
      throws InterruptedException, ExecutionException, TimeoutException {
    if (tasks == null) {
      throw new NullPointerException();
    }
    return null;
  }

  @Override
  public <T> T invokeAny(Collection<? extends Callable<T>> tasks)
      throws InterruptedException, ExecutionException {
    try {
      return doInvokeAny(tasks, false, 0);
    } catch (TimeoutException cannotHappen) {
      assert false;
      return null;
    }
  }

  @Override
  public <T> T invokeAny(Collection<? extends Callable<T>> tasks,
      long timeout, TimeUnit unit)
      throws InterruptedException, ExecutionException, TimeoutException {
    return doInvokeAny(tasks, true, unit.toNanos(timeout));
  }

  @Override
  public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks)
      throws InterruptedException {
    if (tasks == null) {
      throw new NullPointerException();
    }
    return null;
  }

  @Override
  public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks,
      long timeout, TimeUnit unit)
      throws InterruptedException {
    if (tasks == null) {
      throw new NullPointerException();
    }
    return null;
  }

}
