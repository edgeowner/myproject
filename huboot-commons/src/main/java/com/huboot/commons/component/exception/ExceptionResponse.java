package com.huboot.commons.component.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponse implements Serializable{

    private Integer code;

    private String message;

    private String errorKey;

    public ExceptionResponse(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
