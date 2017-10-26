package com.dongweima.utils.thread.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 不可重入锁,所只能被单个线程持有一次, 状态只有0,1
 *
 * @author dongweima
 */
public class NotReentrantLock implements Lock {

  private final Sync sync = new Sync();

  @Override
  public void lock() {
    sync.acquire(1);
  }

  @Override
  public void lockInterruptibly() throws InterruptedException {
    sync.acquireInterruptibly(1);
  }

  @Override
  public boolean tryLock() {
    return sync.tryAcquire(1);
  }

  @Override
  public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
    return sync.tryAcquireNanos(1, unit.toNanos(time));
  }

  @Override
  public void unlock() {
    sync.release(1);
  }

  @Override
  public Condition newCondition() {
    return sync.newCondition();
  }

  /**
   * 同步器实现可重写的三个方法.锁调用队列同步器模板方法. 两者结合就能写出锁了.只要还是队列同步器.
   */
  private static class Sync extends AbstractQueuedSynchronizer {

    //是否被当前线程所独占
    @Override
    protected boolean isHeldExclusively() {
      return getState() == 1;
    }

    @Override
    protected boolean tryAcquire(int acquire) {
      if (compareAndSetState(0, 1)) {
        setExclusiveOwnerThread(Thread.currentThread());
        return true;
      }
      return false;
    }

    @Override
    protected boolean tryRelease(int release) {
      if (getState() == 0) {
        throw new IllegalArgumentException();
      }
      setExclusiveOwnerThread(null);
      setState(0);
      return true;
    }

    /**
     * 返回一个condition,每个condition包含一个condition队列.
     */
    Condition newCondition() {
      return new ConditionObject();
    }
  }
}
