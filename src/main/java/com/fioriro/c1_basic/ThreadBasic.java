package com.fioriro.c1_basic;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * ClassName:ThreadBacis
 * Project:JUC
 * Package: com.fioriro.c1_basic
 * Description
 *
 * @Author liulei
 * @Create 2025/2/28 10:08
 * @Version 1.0
 */
public class ThreadBasic {

    private static final Logger log = LoggerFactory.getLogger(ThreadBasic.class);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
       //daemonThread();
        //m3ToStartThreadWithRunnable();
//        m2ToStartThreadWithLambda();
        m4ToStartThreadWithFutureTask();
    }

    public static void daemonThread(){
        Thread t1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " 开始运行," + (Thread.currentThread().isDaemon() ? "守护线程" : "用户线程"));
            while (true) {

            }
        }, "t1");
        t1.setDaemon(true);//通过设置属性Daemon来设置当前线程是否为守护线程
        t1.start();
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName() + " 主线程结束");
    }

    public static void m1ToStartThread() {
        // 直接new Thread，然后调用start方法
        Thread t1 = new Thread("m1"){
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " 开始运行");
            }
        };
        t1.start();
    }

    public static void m1ToStartThreadWithLambda(){
        Thread t1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " 开始运行");
        }, "m1");
        t1.start();
    }

    public static void m2ToStartThreadWithRunnable() {
        // 通过自定义类实现Runnable接口，然后传入Thread构造方法
        Thread t2 = new Thread(new MyThread(), "m2");
        t2.start();
    }

    public static void m2ToStartThreadWithLambda() {
        Runnable t2 = () -> System.out.println(Thread.currentThread().getName() + " 开始运行");
        new Thread(t2, "m2").start();
    }

    public static void m3ToStartThreadWithRunnable() {
        // 通过创建Runnable对象，然后传入Thread构造方法
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " 开始运行");
            }
        };
        Thread t3 = new Thread(runnable, "m3");
        t3.start();
    }

    public static void m4ToStartThreadWithFutureTask() throws ExecutionException, InterruptedException {
        // 通过FutureTask包装Callable对象，然后传入Thread构造方法
        Callable<Integer> callable = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println(Thread.currentThread().getName() + " 开始运行");
                return 100;
            }
        };
        FutureTask<Integer> futureTask = new FutureTask<>(callable);
        Thread t4 = new Thread(futureTask, "m4");
        t4.start();
        Integer result = futureTask.get();
        log.info("result: {}", result);
    }



    @Data
    @NoArgsConstructor
    static class MyThread implements Runnable {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " 开始运行");
        }
    }
}
