package com.easy.work.common.exception;

import com.easy.work.common.enums.ResultEnum;

public class EasyWorkException extends RuntimeException {

    private Integer code;

    public EasyWorkException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }

    public EasyWorkException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public EasyWorkException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
