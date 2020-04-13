package com.easy.work.common.testdemo;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public class GirlComparator implements Comparator<Girl> {

    @Override
    public int compare(Girl g1, Girl g2) {
        System.out.println("888888");
        return g1.getAge() - g2.getAge();
    }

}
