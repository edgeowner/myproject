package com.huboot.business.base_model.weixin_service.dto.common.exception;

/**
 * Created by Administrator on 2018/2/5 0005.
 */
public class RemoteException extends RuntimeException {

    public RemoteException() {
    }

    public RemoteException(String message) {
        super(message);
    }

    public RemoteException(String message, Throwable cause) {
        super(message, cause);
    }

    public RemoteException(Throwable cause) {
        super(cause);
    }

    public RemoteException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
