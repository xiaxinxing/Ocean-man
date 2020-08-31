package com.ocean.core.commons.handler;


import com.ocean.common.utils.JSONMessage;
import com.ocean.core.commons.exception.MessageException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author xxx
 * @Date 2020-08-20
 * 全局异常处理器
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Object errorHandler(Exception exception) {
        if (exception instanceof NullPointerException || exception instanceof IllegalArgumentException) {
            return JSONMessage.error(new MessageException("请求参数缺失"));
        }
        if (exception instanceof MessageException) {
            return JSONMessage.error(exception);
        }
        return JSONMessage.error(new MessageException("请求失败"));
    }


}
