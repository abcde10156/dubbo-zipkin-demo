package com.luotao.demo.dubbozipkin.web.bean;

import org.springframework.stereotype.Component;

/**
 * User: luotao-pc
 * Date: 2018/6/16
 * Time: 22:32
 */
@Component
public class TraceProperties {
    private String serviceName;
    private String zipkin;
    private float rate;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getZipkin() {
        return zipkin;
    }

    public void setZipkin(String zipkin) {
        this.zipkin = zipkin;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }
}
