package com.luotao.demo.dubbozipkin.request;

import java.io.Serializable;

/**
 * User: luotao-pc
 * Date: 2018/6/15
 * Time: 22:12
 */
public class RequestCommon implements Serializable{
    private int frontType;

    public int getFrontType() {
        return frontType;
    }

    public void setFrontType(int frontType) {
        this.frontType = frontType;
    }
}
