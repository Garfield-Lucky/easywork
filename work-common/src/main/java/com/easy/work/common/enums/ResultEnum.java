package com.easy.work.common.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {

    SUCCESS(0, "success"),
    ERROR(-1, "error"),
    PARAMS_ERROR(101, "参数不正确"),
    USER_NOT_EXIST(102, "用户不存在"),
    TOKEN_NOT_EXIST(103, "token不存在"),
    ;

    private Integer code;

    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
