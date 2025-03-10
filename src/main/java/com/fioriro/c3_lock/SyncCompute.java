package com.fioriro.c3_lock;

/**
 * ClassName:SyncComputef
 * Project:JUC
 * Package: com.fioriro.c3_lock
 * Description
 *
 * @Author liulei
 * @Create 2025/3/10 10:37
 * @Version 1.0
 */
public class SyncCompute {

    static int count = 0;
    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(() -> {
            increment();
        }, "t1");

        Thread t2 = new Thread(() -> {
            decrement();
        }, "t2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println("count = " + count);
    }

    public static void increment() {
        for (int i = 0; i < 5000; i++) {
            count++;
        }
    }

    public static void decrement() {
        for (int i = 0; i < 5000; i++) {
            count--;
        }
    }
}
