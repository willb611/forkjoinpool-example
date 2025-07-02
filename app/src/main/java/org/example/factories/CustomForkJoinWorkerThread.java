package org.example.factories;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinWorkerThread;

public class CustomForkJoinWorkerThread extends ForkJoinWorkerThread {
  protected CustomForkJoinWorkerThread(ThreadGroup group, ForkJoinPool pool, boolean preserveThreadLocals) {
    super(group, pool, preserveThreadLocals);
  }

  protected CustomForkJoinWorkerThread(ForkJoinPool pool) {
    super(pool);
  }
}
