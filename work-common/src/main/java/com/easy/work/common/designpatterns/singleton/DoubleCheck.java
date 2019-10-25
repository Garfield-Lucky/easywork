package com.easy.work.common.designpatterns.singleton;

public class DoubleCheck {
    private volatile static DoubleCheck instance;
    private DoubleCheck(){}

    public static DoubleCheck getInstance() {
        if(instance == null) {
            synchronized (DoubleCheck.class) {
                if(instance == null) {
                    instance = new DoubleCheck();

                }
            }
        }
        return instance;
    }
}
