package com.easy.work.common.designpatterns.singleton;

public class StaticInnerClass {
    private static class InnerHolder {
        private static final StaticInnerClass INSTABCE = new StaticInnerClass();
    }
    private StaticInnerClass(){}

    public static StaticInnerClass getInstance() {
        return InnerHolder.INSTABCE;
    }
}
