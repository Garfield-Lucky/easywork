package com.easy.work.common.designpatterns.singleton;

public class Lazy {
    private static Lazy instance;
    private Lazy() {

    }
    public static synchronized Lazy getInstance() {
        if (instance == null) {
            instance = new Lazy();
        }
        return instance;
    }
}
