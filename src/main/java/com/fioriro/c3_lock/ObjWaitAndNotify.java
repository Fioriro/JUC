package com.fioriro.c3_lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.lang.Thread.sleep;

/**
 * ClassName:ObjWaitAndNotify
 * Project:JUC
 * Package: com.fioriro.c3_lock
 * Description
 *
 * @Author liulei
 * @Create 2025/3/10 14:13
 * @Version 1.0
 */
public class ObjWaitAndNotify {

    static Logger log = LoggerFactory.getLogger(ObjWaitAndNotify.class);

    final static Object obj = new Object();

    public static void main(String[] args) throws InterruptedException {

        new Thread(() -> {
            synchronized (obj){
                log.debug("执行...");
                try{
                    obj.wait();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                log.debug(Thread.currentThread().getName() +  "其他代码...");
            }
        }, "t1").start();

        new Thread(() -> {
            synchronized (obj){
                log.debug("执行...");
                try{
                    obj.wait();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                log.debug(Thread.currentThread().getName() + "其他代码...");
            }
        }, "t2").start();

        sleep(2000);
        log.debug("唤醒 obj 上其他线程");
        synchronized (obj){
            obj.notifyAll();
            //obj.notify();
        }
    }
}
