package com.easy.work.common.testdemo;

import ch.qos.logback.core.net.SyslogOutputStream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Father {
    public void greet() {
        System.out.println("Hello, i am function in father!");
    }
}
 
class Child extends Father {
    @Override
    public void greet() {
        Runnable runnable = this::mymethon;
        new Thread(runnable).start();
    }

    public void mymethon() {
        String str = "abcdef";
        int flag = str.compareToIgnoreCase("abg");
        System.out.println(flag);
        System.out.println("my mathod ****");
        List<String> strLst = new ArrayList<String>() {
            {
                add("adfkjsdkfjdskjfkds");
                add("asdfasdfafgfgf");
                add("public static void main");
                add("asdfasdfafgfgf12");
                add("asdfasdfafgfgferr");
                add("yyr");
            }
        };
        Collections.sort(strLst, String::compareToIgnoreCase);
        System.out.println(strLst);
    }

    public static void main(String[] args){
        new Child().greet();
    }
}

