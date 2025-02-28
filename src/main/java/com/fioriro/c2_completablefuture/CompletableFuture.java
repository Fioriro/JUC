package com.fioriro.c2_completablefuture;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * ClassName:CompletableFuture
 * Project:JUC
 * Package: com.fioriro.c2_completablefuture
 * Description
 *
 * @Author liulei
 * @Create 2025/2/28 10:21
 * @Version 1.0
 */
public class CompletableFuture {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<String> futureTask = new FutureTask<>(new MyThread());
        Thread t1 = new Thread(futureTask); //开启一个异步线程
        t1.start();

        System.out.println(futureTask.get()); //有返回hello Callable
    }
}


class MyThread implements Callable<String> {
    @Override
    public String call() throws Exception {
        System.out.println("--------come in");
        return "hello Callable";
    }
}
