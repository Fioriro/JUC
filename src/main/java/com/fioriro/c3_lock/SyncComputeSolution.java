package com.fioriro.c3_lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * ClassName:SyncComputeSolution
 * Project:JUC
 * Package: com.fioriro.c3_lock
 * Description
 *
 * @Author liulei
 * @Create 2025/3/10 10:54
 * @Version 1.0
 */
public class SyncComputeSolution {

    static int counter = 0;
    static final Object room = new Object();
    public static final Logger log = LoggerFactory.getLogger(SyncComputeSolution.class);

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                synchronized (room) {
                    counter++;
                }
            }
        }, "t1");
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                synchronized (room) {
                    counter--;
                }
            }
        }, "t2");
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        log.debug("{}", counter);
    }
}
