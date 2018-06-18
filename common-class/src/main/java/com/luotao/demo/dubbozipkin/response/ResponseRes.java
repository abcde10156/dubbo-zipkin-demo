package com.luotao.demo.dubbozipkin.response;

/**
 * User: luotao-pc
 * Date: 2018/6/16
 * Time: 16:30
 */
public class ResponseRes extends ResponseCommon {
    private String name;

    private String md5;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }
}
