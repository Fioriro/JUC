package com.fioriro.c1_basic;

import java.util.*;
import java.util.concurrent.*;


/**
 * ClassName:ParallelVsSerialExecution
 * Project:JUC
 * Package: com.fioriro.c1_basic
 * Description
 *
 * @Author liulei
 * @Create 2025/2/28 11:02
 * @Version 1.0
 */
public class ParallelVsSerialExecution {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        int numTasks = 10;  // 任务数量
        List<Long> taskDurations = new ArrayList<>();

        // 生成递增的任务执行时间（从10ms递增到100ms）
        for (int i = 0; i < numTasks; i++) {
            taskDurations.add(10L * (i + 1));  // 任务耗时从10ms到100ms
        }

        // 串行执行
        long serialTime = measureSerialExecution(taskDurations);
        System.out.println("Serial execution time: " + serialTime + " ms");

        // 并行执行
        long parallelTime = measureParallelExecution(taskDurations);
        System.out.println("Parallel execution time: " + parallelTime + " ms");
    }

    // 串行执行方法
    public static long measureSerialExecution(List<Long> taskDurations) throws InterruptedException {
        long startTime = System.currentTimeMillis();

        for (Long duration : taskDurations) {
            simulateTask(duration);  // 模拟任务延迟
        }

        return System.currentTimeMillis() - startTime;
    }

    // 并行执行方法
    public static long measureParallelExecution(List<Long> taskDurations) throws InterruptedException, ExecutionException {
        long startTime = System.currentTimeMillis();

        // 使用线程池执行任务
        ExecutorService executorService = Executors.newFixedThreadPool(taskDurations.size());
        List<Future<Void>> futures = new ArrayList<>();

        for (Long duration : taskDurations) {
            futures.add(executorService.submit(() -> {
                simulateTask(duration);
                return null;  // 返回值无关紧要，只是占位
            }));
        }

        // 等待所有任务完成
        for (Future<Void> future : futures) {
            future.get();
        }

        executorService.shutdown();
        return System.currentTimeMillis() - startTime;
    }

    // 模拟任务的执行（延迟指定的时间）
    public static void simulateTask(long duration) {
        try {
            Thread.sleep(duration);  // 模拟每个任务的执行延迟
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

