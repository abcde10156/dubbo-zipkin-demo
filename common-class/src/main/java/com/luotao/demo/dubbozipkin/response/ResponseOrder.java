package com.luotao.demo.dubbozipkin.response;

/**
 * User: luotao-pc
 * Date: 2018/6/16
 * Time: 16:30
 */
public class ResponseOrder extends ResponseCommon {
    private String orderName;

    private String orderType;

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }
}
