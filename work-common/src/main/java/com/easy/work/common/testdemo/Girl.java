package com.easy.work.common.testdemo;

import org.springframework.boot.jdbc.DataSourceUnwrapper;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Girl {

    private String name;
    private int age;

    public final int localCode = nextCode();

    private static AtomicInteger nextHashCode = new AtomicInteger();

    private static final int HASH_INCREMENT = 0x61c88647;

    public static int nextCode(){

        return nextHashCode.getAndAdd(HASH_INCREMENT);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Girl() {

    }
    public Girl(String name, int age) {
        super();
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Girl [name=" + name + ", age=" + age + "]";
    }

    public static void main(String[] args) {

//        Set<Girl> set = new TreeSet<>();
//        Girl girl;
//        for (int i = 0; i< 20; i++) {
//            girl = new Girl("girl "+i, i);
//            set.add(girl);
//        }
//        set.stream().forEach(System.out::println);

        ConsumerInterface<String> consumer = (str) -> System.out.println(str);
          consumer.accept("123456");
//          System.out.println( consumer.accept("1234567"));
//        List<Girl> list = new ArrayList<>(100);
//        Girl girl;
//        for (int i=0; i<75; i++) {
//            girl = new Girl("girl " + i, i);
//            list.add(girl);
//        }
//        Collections.shuffle(list);
//        Collections.sort(list,new GirlComparator());
//        list.stream().forEach(System.out::println);
        AtomicInteger au = new AtomicInteger();

        System.out.println(au);
        System.out.println(au.getAndIncrement());
        System.out.println(au.getAndIncrement());

        String filePath = "qweqqq";
        System.out.println(filePath.indexOf("/"));

    }

}
