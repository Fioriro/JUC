package com.fioriro.c3_lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.LockSupport;

import static java.lang.Thread.sleep;

/**
 * ClassName:Park
 * Project:JUC
 * Package: com.fioriro.c3_lock
 * Description
 *
 * @Author liulei
 * @Create 2025/3/10 15:52
 * @Version 1.0
 */
public class Park {

    static Logger log = LoggerFactory.getLogger(Park.class);
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            log.debug("start...");
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.debug("park...");
            LockSupport.park();
            log.debug("resume...");
        },"t1");
        t1.start();
        sleep(2000);
        log.debug("unpark...");
        LockSupport.unpark(t1);
    }
}
