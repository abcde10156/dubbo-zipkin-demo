package com.luotao.demo.dubbozipkin.common;

/**
 * User: luotao-pc
 * Date: 2018/6/15
 * Time: 18:56
 */
public class BusiException extends RuntimeException {

    Throwable throwable;

    int code;

    public BusiException(int code, Throwable throwable) {
        super(throwable);
        this.throwable = throwable;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return throwable.getMessage();
    }
}
