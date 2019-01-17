package com.huboot.commons.component.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UnAuthorizationException extends RuntimeException {

    private static final long serialVersionUID = -1827198664611457387L;
    private String errorKey;

    public UnAuthorizationException(String message) {
        super(message);
    }

    public UnAuthorizationException(String message, String errorKey) {
        super(message);
        this.errorKey = errorKey;
    }
}
