package com.custom.dic.exceptions;

public class PostConstructException extends ServiceInstantiationException {
    public PostConstructException(String message) {
        super(message);
    }

    public PostConstructException(String message, Throwable cause) {
        super(message, cause);
    }
}
