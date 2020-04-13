package com.easy.work.common.testdemo;

public class CommonMainTest {

    public static void  main(String[]  args) {
        System.out.println("hello");
        Thread currentThread = Thread.currentThread();
        System.out.println(currentThread);
        System.out.println(currentThread.getName());

        String src = new String("../ab4../3a2c4../3d");
        System.out.println(src.replaceFirst("../","*"));
        System.out.println(src.indexOf("../"));

        System.out.println(src.replaceAll("../","*"));
        Girl girl = new Girl();



        int a = 0x61c88647;;
        System.out.println(girl.localCode);

        for (int i = 0 ;i<16 ; i ++) {
            System.out.println((i*a + a) & 15);

        }

    }
}
