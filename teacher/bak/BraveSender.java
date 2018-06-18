package com.luotao.demo.dubbozipkin.web.bean;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import zipkin2.reporter.Sender;
import zipkin2.reporter.okhttp3.OkHttpSender;


/**
 * User: luotao-pc
 * Date: 2018/6/16
 * Time: 22:33
 */
@Component
public class BraveSender implements FactoryBean {

    @Autowired
    TraceProperties traceProperties;

    @Override
    public Object getObject() throws Exception {
        OkHttpSender okHttpSender = OkHttpSender.create(traceProperties.getZipkin());
        return okHttpSender;
    }

    @Override
    public Class<?> getObjectType() {
        return Sender.class ;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
