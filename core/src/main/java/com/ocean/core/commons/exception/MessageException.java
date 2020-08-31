package com.ocean.core.commons.exception;

import lombok.Data;

/**
 * 自定义异常
 */
@Data
public class MessageException extends RuntimeException {

    private String code;
    private String message;


    public MessageException(String message) {
        super(message);
        this.message = message;
    }

    public MessageException(String message, String code) {
        super(message);
        this.code = code;
        this.message = message;
    }


}
