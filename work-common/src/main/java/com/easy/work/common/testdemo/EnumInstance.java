package com.easy.work.common.testdemo;

public enum EnumInstance {
    INSTANCE("aa","bb"),
    INSTANCE2("cc","dd");

    private Object data;
    private String msg;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
    private EnumInstance(Object data,String msg){
        this.data = data;
        this.msg = msg;
    }
    public static EnumInstance getInstance(){
        return INSTANCE;
    }

}