package com.huboot.commons.jpa;

/**
 * Created by Administrator on 2018/2/1 0001.
 */
public class MethodCanNotUseException extends RuntimeException {

    public MethodCanNotUseException() {
    }

    public MethodCanNotUseException(String message) {
        super(message);
    }

    public MethodCanNotUseException(String message, Throwable cause) {
        super(message, cause);
    }

    public MethodCanNotUseException(Throwable cause) {
        super(cause);
    }

    public MethodCanNotUseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
