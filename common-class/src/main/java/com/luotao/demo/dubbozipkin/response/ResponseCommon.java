package com.luotao.demo.dubbozipkin.response;

import java.io.Serializable;

/**
 * User: luotao-pc
 * Date: 2018/6/15
 * Time: 17:20
 */
public class ResponseCommon<T> implements Serializable {

    private String requestId;

    T data;

    private int code = 0;

    private String message = "";


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public static <T> ResponseCommon success(int code, String message, T data) {
        ResponseCommon<T> responseCommon = new ResponseCommon<>();
        responseCommon.setCode(code);
        responseCommon.setMessage(message);
        responseCommon.setData(data);
        return responseCommon;
    }

    public static <T> ResponseCommon fail(int code, String message, Throwable data) {
        ResponseCommon<T> responseCommon = new ResponseCommon<>();
        responseCommon.setCode(code);
        responseCommon.setMessage(message);
        return responseCommon;
    }


}
