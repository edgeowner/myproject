package com.huboot.commons.component.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BizException extends RuntimeException {

    private String errorKey;

    public BizException(String message) {
        super(message);
    }

    public BizException(String message, String errorKey) {
        super(message);
        this.errorKey = errorKey;
    }

}
