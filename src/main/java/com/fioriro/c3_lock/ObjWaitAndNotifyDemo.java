package com.fioriro.c3_lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.lang.Thread.sleep;

/**
 * ClassName:ObjWaitAndNotifyDemo
 * Project:JUC
 * Package: com.fioriro.c3_lock
 * Description
 *
 * @Author liulei
 * @Create 2025/3/10 14:41
 * @Version 1.0
 */
public class ObjWaitAndNotifyDemo {

    static Logger log = LoggerFactory.getLogger(ObjWaitAndNotifyDemo.class);
    static final Object room = new Object();
    static boolean hasCigarette = false;
    static boolean hasTakeout = false;

    public static void main(String[] args) throws InterruptedException {
        situation5();
    }

    public static void situation1() throws InterruptedException {

        new Thread(() -> {
            synchronized (room){
                log.debug("有烟没？[{}]", hasCigarette);
                if(!hasCigarette){
                    log.debug("没烟，先歇会");
                    try {
                        sleep(2000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                log.debug("有烟没？[{}]", hasCigarette);
                if (hasCigarette){
                    log.debug("有烟了，可以开始干活了");
                }
            }
        }, "小南").start();

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                synchronized (room){
                    log.debug("可以开始干活了");
                }
            }, "其他人").start();
        }

        sleep(1000);
        new Thread(() -> {
            hasCigarette = true;
            log.debug("烟到了噢！");
        }, "送烟的").start();
    }

    public static void situation2() throws InterruptedException{

        new Thread(() -> {
            synchronized (room){
                log.debug("有烟没？[{}]", hasCigarette);
                if(!hasCigarette){
                    log.debug("没烟，先歇会");
                    try {
                        room.wait(2000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                log.debug("有烟没？[{}]", hasCigarette);
                if (hasCigarette){
                    log.debug("有烟了，可以开始干活了");
                }
            }
        }, "小南").start();

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                synchronized (room){
                    log.debug("可以开始干活了");
                }
            }, "其他人").start();
        }

        sleep(1000);
        new Thread(() -> {
            synchronized (room) {
                hasCigarette = true;
                log.debug("烟到了噢！");
                room.notify();
            }
        }, "送烟的").start();
    }

    public static void situation3() throws InterruptedException {
        new Thread(() -> {
            synchronized (room) {
                log.debug("有烟没？[{}]", hasCigarette);
                if (!hasCigarette) {
                    log.debug("没烟，先歇会");
                    try {
                        room.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("有烟没？[{}]", hasCigarette);
                if (hasCigarette) {
                    log.debug("有烟了，可以开始干活了");
                }else {
                    log.debug("没干成活...");
                }
            }
        }, "小南").start();

        new Thread(() -> {
            synchronized (room) {
                Thread thread = Thread.currentThread();
                log.debug("外卖到了没？[{}]", hasTakeout);
                if (!hasTakeout) {
                    log.debug("没外卖，先歇会");
                    try {
                        room.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("外卖到了没？[{}]", hasTakeout);
                if (hasTakeout) {
                    log.debug("可以开始干活了");
                }else {
                    log.debug("没干成活...");
                }
            }
        }, "小女").start();

        sleep(1000);
        new Thread(() -> {
            synchronized (room) {
                hasTakeout = true;
                log.debug("外卖到了");
                room.notify();
            }
        }, "送外卖的").start();
    }

    public static void situation4() throws InterruptedException {
        new Thread(() -> {
            synchronized (room) {
                log.debug("有烟没？[{}]", hasCigarette);
                if (!hasCigarette) {
                    log.debug("没烟，先歇会");
                    try {
                        room.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("有烟没？[{}]", hasCigarette);
                if (hasCigarette) {
                    log.debug("有烟了，可以开始干活了");
                }else {
                    log.debug("没干成活...");
                }
            }
        }, "小南").start();

        new Thread(() -> {
            synchronized (room) {
                Thread thread = Thread.currentThread();
                log.debug("外卖到了没？[{}]", hasTakeout);
                if (!hasTakeout) {
                    log.debug("没外卖，先歇会");
                    try {
                        room.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("外卖到了没？[{}]", hasTakeout);
                if (hasTakeout) {
                    log.debug("可以开始干活了");
                }else {
                    log.debug("没干成活...");
                }
            }
        }, "小女").start();

        sleep(1000);
        new Thread(() -> {
            synchronized (room) {
                hasTakeout = true;
                log.debug("外卖到了");
                room.notifyAll();
            }
        }, "送外卖的").start();
    }

    public static void situation5() throws InterruptedException {
        new Thread(() -> {
            synchronized (room) {
                log.debug("有烟没？[{}]", hasCigarette);
                while (!hasCigarette) {
                    log.debug("没烟，先歇会");
                    try {
                        room.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("有烟没？[{}]", hasCigarette);
                if (hasCigarette) {
                    log.debug("有烟了，可以开始干活了");
                }else {
                    log.debug("没干成活...");
                }
            }
        }, "小南").start();

        new Thread(() -> {
            synchronized (room) {
                Thread thread = Thread.currentThread();
                log.debug("外卖到了没？[{}]", hasTakeout);
                while (!hasTakeout) {
                    log.debug("没外卖，先歇会");
                    try {
                        room.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("外卖到了没？[{}]", hasTakeout);
                if (hasTakeout) {
                    log.debug("可以开始干活了");
                }else {
                    log.debug("没干成活...");
                }
            }
        }, "小女").start();

        sleep(1000);

        new Thread(() -> {
            synchronized (room) {
                hasCigarette = true;
                log.debug("烟到了");
                room.notifyAll();
            }
        }, "送烟的").start();

        sleep(1000);

        new Thread(() -> {
            synchronized (room) {
                hasTakeout = true;
                log.debug("外卖到了");
                room.notifyAll();
            }
        }, "送外卖的").start();
    }
}
