package com.easy.work.common.testdemo;

import org.springframework.util.Assert;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SingletonTest {

    static {
        System.out.println("111111");
    }

    private static class StaticClassA{
        private static SingletonTest singleton = new SingletonTest();
       static {
            System.out.println("222");
        }

    }

    public static SingletonTest getInstance(){
        return StaticClassA.singleton;
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

//        EnumInstance instance = EnumInstance.getInstance();
//        instance.setData(new Object());
//        int a[] = new int[] {1,2,3};
//
//        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("singleton_file"));
//        oos.writeObject(instance);

//        File file = new File("singleton_file");
//        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
//        EnumInstance newInstance = (EnumInstance) ois.readObject();
//
//
//        System.out.println(instance.getData());
//        System.out.println(newInstance.getData());
//        System.out.println(instance.getData() == newInstance.getData());

//        SingletonTest.getInstance();
//        EnumInstance.getInstance().setData("123456");
        System.out.println( EnumInstance.getInstance().getData());
        System.out.println( EnumInstance.values());



//        for(int i=0;i<10;i++){
//            ExecutorService pool = Executors.newFixedThreadPool(5);
//            pool.submit(new Runnable() {
//                @Override
//                public void run() {
//                    System.out.println( "start***");
//                    try {
//                        Thread.sleep(2000L);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//            System.out.println( pool);
//        }

        Integer[] arr = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        Arrays.stream(arr).filter(x->x>3&&x<8).forEach(System.out::println);
        String[] arr1 = {"a", "b", "c", "d"};
        String[] arr2 = {"e", "f", "c", "d"};
        String[] arr3 = {"h", "j", "c", "d"};
        // Stream.of(arr1, arr2, arr3).flatMap(x -> Arrays.stream(x)).forEach(System.out::println);
//        Stream.of(arr1, arr2, arr3).flatMap(Arrays::stream).forEach(System.out::println);
//        Object[] powers = Stream.iterate(1.0, p->p*2).peek(e->System.out.println(e)).toArray();

        Stream<String> uniqueWordStream = Stream.of("a","e", "b", "bq", "c", "abc", "d").distinct();
//        Optional<String> largest = uniqueWordStream.max(String::compareToIgnoreCase);
        Optional<String> largest2 = uniqueWordStream.max((x1,x2) -> Integer.compare(x1.length(),x2.length()));

        if (largest2.isPresent()) {
            System.out.println("largest: " + largest2.get());
        }

        List<String> names = new ArrayList();
        names.add("Google");
        names.add("Runoob");
         names.add("Taobao");
        names.add("Baidu");
         names.add("Sina");
//        names.forEach(System.out::println);

        List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd", "", "jkl");
        List<Long> longs = Arrays.asList(12L,16L, 68L, 25L, 3L, 8L, 9L);

        List<String> newList = strings.stream().filter(i -> i.length() >29).map(x -> coverd2(x +  "TT")).collect(Collectors.toList());

        for (String name : newList) {
            System.out.println("newList:"+name);
        }
        //newList.isEmpty();
        //Random random = new Random(3);
        //random.ints().limit(10).forEach(System.out::println);
        //
        //System.out.println(10 | 5);
        //names.stream().forEach(System.out::println);
        //names.stream().filter(i -> i.length()>4).map(s ->s+"AA").forEach(System.out::println);


        //0001 0010 0011 0100 0101 0110 0111 1000 1001 1010 1011 1100 1101 1110 1111 10000
//
//        Queue que = new LinkedList();
//        Boolean aBoolean = longs.stream().anyMatch(x->x.equals(25L));
//        System.out.println(aBoolean);
//
//        String str =  strings.stream().parallel().filter(x->x.length()>4).findFirst().orElse("noghing");
//
//        System.out.println(str);
//
//        Stream<Integer> stream = Stream.iterate(1, x -> x + 1).limit(10);
//
////        stream.forEach(System.out::println);
//        stream.forEach((x) -> {System.out.println(x);System.out.println("yy");});
//
//        AtomicInteger au = new AtomicInteger();
//
//        System.out.println(au);
//        System.out.println(au.getAndIncrement());
//        System.out.println(au.getAndIncrement());
//
//        System.out.println(UUID.randomUUID());
    }

    public static boolean coverd(String name) {
        System.out.println("coverd:"+name);
        return name.length() > 2;
    }
    public static String coverd2(String name) {
        System.out.println("coverd2:"+name);
        return name;
    }



}

