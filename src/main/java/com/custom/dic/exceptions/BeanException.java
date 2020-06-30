package com.custom.dic.exceptions;

public class BeanException extends InstantiationException {
    public BeanException(String message) {
        super(message);
    }

    public BeanException(String message, Throwable cause) {
        super(message, cause);
    }
}
