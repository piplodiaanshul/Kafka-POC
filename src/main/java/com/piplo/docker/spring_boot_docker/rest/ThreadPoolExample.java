//package com.piplo.docker.spring_boot_docker.rest;
//
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.ScheduledExecutorService;
//import java.util.concurrent.TimeUnit;
//
//public class ThreadPoolExample {
//
////    public static void main(String[] args) {
////        // Fixed thread pool example
////        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(45);
////        for (int i = 0; i < 5; i++) {
////            fixedThreadPool.submit(() -> {
////                System.out.println("Fixed thread pool: " + Thread.currentThread().getName());
////            });
////        }
////        fixedThreadPool.shutdown();
////
////        // Cached thread pool example
////        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
////        for (int i = 0; i < 5; i++) {
////            cachedThreadPool.submit(() -> {
////                System.out.println("Cached thread pool: " + Thread.currentThread().getName());
////            });
////        }
////        cachedThreadPool.shutdown();
////
////        // Single thread executor example
////        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
////        for (int i = 0; i < 5; i++) {
////            singleThreadExecutor.submit(() -> {
////                System.out.println("Single thread executor: " + Thread.currentThread().getName());
////            });
////        }
////        singleThreadExecutor.shutdown();
////
////        // Scheduled thread pool example
////        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(2);
////        scheduledThreadPool.schedule(() -> {
////            System.out.println("Scheduled thread pool: " + Thread.currentThread().getName());
////        }, 3, TimeUnit.SECONDS);
////        scheduledThreadPool.shutdown();
////    }
//    public static class InterruptExample {
//        public static void main(String[] args) {
//            Thread thread = new Thread(() -> {
//                while (true) {
//                    if (Thread.currentThread().isInterrupted()) {
//                        System.out.println("Thread was interrupted.");
//                        break;
//                    }
//                    // Simulate some work
//                    System.out.println("Thread is running...");
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        System.out.println("Thread was interrupted during sleep.");
//                        Thread.currentThread().interrupt(); // Restore the interrupt status
//                        break;
//                    }
//                }
//            });
//
//            thread.start();
//            thread.start();
//            // Interrupt the thread after 3 seconds
//            try {
//                Thread.sleep(3000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//            thread.interrupt(); // Interrupt the thread
//        }
//    }
//
//}
