//package com.piplo.docker.spring_boot_docker.rest;
//
//import java.util.Map;
//import java.util.concurrent.CompletableFuture;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.ExecutionException;
//import java.util.function.Supplier;
//
//public class ThreadExampleV2 {
//
//    public static class DeadlockExample {
//        private static final Object lock1 = new Object();
//        private static final Object lock2 = new Object();
//
//        public static void main(String[] args) throws ExecutionException, InterruptedException {
//            ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
//            map.put("Anshul", "piplodia");
//            map.put("Himanshi", "Dubey");
//            System.out.println();
//            for (Map.Entry<String,String> anEntry: map.entrySet()) {
//                map.put("Raghav", "Dubey");
//                map.put("Ram", "Sita");
//                map.put("Geeta", "Cheetah");
//                System.out.println(anEntry.getKey());
//            }
//            System.out.println();
//            System.out.println(map);
//            System.out.println();
//            System.out.println();
//            map.forEach((a,b)-> {
//                map.put("Neeta", "Trita");
//                System.out.println(a);
//            });
//            System.out.println("anshul".hashCode() == "luhsna".hashCode());
//            Supplier<String> t1 = () -> {
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//                return "Anshul";};
//            Supplier<String> t2 = ()-> {
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                } return "Aditi";};
//
//            Supplier<String> t3 = ()-> {
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                } return "Himanshi";};
//
//            Supplier<String> t4 = ()-> {
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                } return "Raghav";};
//            CompletableFuture.supplyAsync(t1)
//                    .thenCombine(CompletableFuture.supplyAsync(t2), (r1, r2) -> "Team 1: " + r1 + ", " + r2)
//                    .thenCompose(team1 -> CompletableFuture.supplyAsync(t3)
//                                                .thenCombine(CompletableFuture.supplyAsync(t4),
//                                                                (r3, r4) -> {
//                                                                    String team2 = "Team 2: " + r3+ ", " + r4;
//                                                                        return String.format("All Teams: [%s || %s]", team1, team2);
//                                                }))
//                    .thenAccept(System.out::println);
//
//   //Thread.sleep(5* 2000);
//            //            Thread thread1 = new Thread(() -> {
////                synchronized (lock1) {
////                    System.out.println("Thread 1: Holding lock 1...");
////                    try { Thread.sleep(100); } catch (InterruptedException e) {}
////                    System.out.println("Thread 1: Waiting for lock 2...");
////                    synchronized (lock2) {
////                        System.out.println("Thread 1: Acquired lock 2!");
////                    }
////                }
////            });
////
////            Thread thread2 = new Thread(() -> {
////                synchronized (lock1) {
////                    System.out.println("Thread 2: Holding lock 1...");
////                    try { Thread.sleep(100); } catch (InterruptedException e) {}
////                    System.out.println("Thread 2: Waiting for lock 1...");
////                    synchronized (lock2) {
////                        System.out.println("Thread 2: Acquired lock 2!");
////                    }
////                }
////            });
////
////            thread1.start();
////            thread2.start();
//        }
//
//
//        public static void main() {
//            final String spouse = "himanshi";
//
//            CompletableFuture.supplyAsync(()-> "ANSHUL")
//                    .thenAcceptAsync((x) -> {x= x.toLowerCase() + spouse;
//                    return;})
//                    .thenCombine(CompletableFuture.runAsync(()->{
//                        System.out.println("Combine:-Async");}), null);
//        }
//    }
//
//}
