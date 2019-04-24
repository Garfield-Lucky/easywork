package com.easy.work.exception;

import com.easy.work.common.exception.EasyWorkException;
import com.easy.work.util.vo.ResultVO;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description: 全局异常处理
 * @param
 * @author Created by wuzhangwei on 2019/1/9
 */

@ControllerAdvice
public class MyControllerAdvice {

    /**
     * 全局异常捕捉处理
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ResultVO errorHandler(Exception ex) {
        ResultVO result = new ResultVO();
        result.setCode(100);
        result.setMsg(ex.getMessage());
        return result;
    }
    
    /**
     * 拦截捕捉自定义异常 MyException.class
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = EasyWorkException.class)
    public ResultVO myErrorHandler(EasyWorkException ex) {
        ResultVO result = new ResultVO();
        result.setCode(ex.getCode());
        result.setMsg(ex.getMessage());
        return result;
    }

}