package com.easy.work.common.testdemo;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

public class Mtest {
    static int count = 0;
    private Object obj = new Object();
    public Mtest(){

    }
    public void testSyn() {
        synchronized (obj) {
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        count++;
        }
    }
    public static void main(String[] args) {
        Map<String, Integer> unsortMap = new HashMap<>();

        // put {A=9, B=2, C=7, D=1} into unsortMap
        unsortMap.put("A",9);
        unsortMap.put("B",2);
        unsortMap.put("C",7);
        unsortMap.put("D",1);



        BinaryOperator<Integer> mergeFunction = (u, v) -> {
            throw new IllegalStateException(String.format("Duplicate key %s", u));
        };
        Map<String, Integer> sortedMap = unsortMap.entrySet().stream()
                .sorted(Map.Entry.<String, Integer> comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, mergeFunction, LinkedHashMap::new));

        System.out.println("unsorted map: " + unsortMap);
        System.out.println("sorted map: " + sortedMap);

        Mtest obj1 = new Mtest();
        Mtest obj2 = new Mtest();

        new Thread(()->{
            try{
                for (int i=0; i<100; i++){
                    obj1.testSyn();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        ).start();

        new Thread(()->{
            try{
                for (int i=0; i<100; i++){
                    obj2.testSyn();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        ).start();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(count);
    }
}
