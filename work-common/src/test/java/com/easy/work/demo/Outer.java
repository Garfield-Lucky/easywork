package com.easy.work.demo;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Outer {
    public AnnoInner getAnnoInner(int x) {
        int y = 100;
        return new AnnoInner() {
            int z = 100;


            @Override
            public int add() {
                System.out.println("123456");
                return x + y + z;
            }
        };
    }
 
    public AnnoInner AnnoInnergetAnnoInner1(List<Integer> list1) {
        List<Integer> list2 = new ArrayList<>(Arrays.asList(1, 2, 3));
        int x=10;
        return ()->{
            list2.add(123);
            int count = 0;
            Iterator<Integer> it = list1.iterator();
            while (it.hasNext()){
                count+=it.next();
            }
            Iterator<Integer> it1 = list2.iterator();
            while (it1.hasNext()){
                count+=it1.next();
            }
            System.out.println("3333");
            return count;
        };
    }
 
    @Test
    public void test(){
        AnnoInner res = new Outer().AnnoInnergetAnnoInner1(Arrays.asList(1,2,3));
        System.out.println("5555");

        System.out.println(res.add());

    }
 
}
 
interface AnnoInner {
    int add();
}