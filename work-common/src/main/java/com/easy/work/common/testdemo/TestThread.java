package com.easy.work.common.testdemo;

import com.easy.work.common.designpatterns.singleton.Singleton;

import java.util.*;

public class TestThread extends Thread{


    public TestThread(String name){
            super(name);
        }
         static  int count = 0;
        @Override
        public void run() {

            System.out.println("启动一个子线程，线程名称为：" + Thread.currentThread().getName());
            for (int i = 0; i < 100; i++) {
                synchronized (this) {

                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                count = count + 1;
                }
            }
            //System.out.println("count:"+count);
            //直接调用方法
            //runSub();
        }

        public void runSub(){
            System.out.println("在run方法里调用，线程名称为："+Thread.currentThread().getName());
            System.out.println(this);
            System.out.println(Thread.currentThread());

            //直接调用方法
            runSubToSub();
        }

        public void runSubToSub(){
            System.out.println("在 runSub里面被调用，线程名称为："+Thread.currentThread().getName());
        }

        public static void main(String[] args) {
            System.out.println("当前主线程名称为："+Thread.currentThread().getName());

            TestThread obj = new TestThread("00");
            TestThread obj1 = new TestThread("11");
            TestThread obj2 = new TestThread("22");
            obj1.start();
            obj2.start();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(count);
            ////直接调用方法
            //mainSub();
            //
            ////启动一个线程
            //new TestThread("我是子线程001").start();
            HashMap<Integer, String> map = new HashMap<Integer, String>();
                   map.put(3, "ab");
                  map.put(2, "b");
                    map.put(4, "ab");
                   map.put(4, "ab");// 和上面相同 ， 会自己筛选
                   map.put(1, "a");

            for (Map.Entry<Integer, String> entry : map.entrySet()) {
                             //Map.entry<Integer,String> 映射项（键-值对）  有几个方法：用上面的名字entry
                            //entry.getKey() ;entry.getValue(); entry.setValue();
                            //map.entrySet()  返回此映射中包含的映射关系的 Set视图。
                            System.out.println("key= " + entry.getKey() + " and value= "
                                    + entry.getValue());
            }

            Set<Map.Entry<Integer, String>> entry = map.entrySet();
            List<Map.Entry<Integer, String>> list = new ArrayList<Map.Entry<Integer, String>>(entry);
            //for (Set(Entry<Integer, String>) entry : map.entrySet()) {
            //    //Map.entry<Integer,String> 映射项（键-值对）  有几个方法：用上面的名字entry
            //    //entry.getKey() ;entry.getValue(); entry.setValue();
            //    //map.entrySet()  返回此映射中包含的映射关系的 Set视图。
            //    System.out.println("key= " + entry.getKey() + " and value= "
            //            + entry.getValue());
            //}
        }

        public void test(TestThread obj) {
            synchronized (TestThread.class) {
                System.out.println(obj.getName());
            }
        }

        public static void mainSub(){

            TestThread obj = new TestThread("00");
            TestThread obj1 = new TestThread("11");
            TestThread obj2 = new TestThread("22");
            obj1.start();
            obj2.start();
            //new Thread(()->{
            //    try{
            //        for (int i = 0; i < 10; i++) {
            //            obj.test(obj1);
            //            Thread.sleep(5000);
            //        }
            //
            //
            //    }catch (Exception e){
            //        e.printStackTrace();
            //    }
            //}
            //).start();
            //
            //new Thread(()->{
            //    try{
            //        for (int i = 0; i < 10; i++) {
            //            obj.test(obj2);
            //            Thread.sleep(1000);
            //        }
            //
            //
            //    }catch (Exception e){
            //        e.printStackTrace();
            //    }
            //}
            //).start();

            System.out.println("直接在主方法里被调用，线程名称为："+Thread.currentThread().getName());
        }
}
