package com.luotao.demo.dubbozipkin.request;

/**
 * User: luotao-pc
 * Date: 2018/6/16
 * Time: 16:29
 */
public class RequestLong extends RequestCommon{
    private Long id;

    public RequestLong(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
