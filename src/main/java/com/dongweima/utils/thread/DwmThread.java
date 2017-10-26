package com.dongweima.utils.thread;

import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dognweima
 */
public class DwmThread extends Thread {

  private static final Logger LOGGER = LoggerFactory.getLogger(DwmThread.class);
  private Date creationDate;
  private Date startDate;
  private Date finishDate;

  public DwmThread(Runnable target, String name) {
    super(target, name);
    setCreationDate();
  }

  @Override
  public void run() {
    setStartDate();
    //保证子线程不崩溃
    try {
      super.run();
    } catch (Throwable throwable) {
      LOGGER.error(throwable.getMessage(), throwable);
    }
    setFinishDate();
  }

  public void setCreationDate() {
    creationDate = new Date();
  }

  public void setStartDate() {
    startDate = new Date();
  }

  public void setFinishDate() {
    finishDate = new Date();
  }

  public long getExecutionTime() {
    return finishDate.getTime() - startDate.getTime();
  }

  @Override
  public String toString() {
    StringBuffer buffer = new StringBuffer();
    buffer.append(getName());
    buffer.append(":  Creation Date: ");
    buffer.append(creationDate);
    buffer.append(" : Running time: ");
    buffer.append(getExecutionTime());
    buffer.append(" Milliseconds.");
    return buffer.toString();
  }

}
