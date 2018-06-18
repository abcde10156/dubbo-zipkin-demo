package com.luotao.demo.dubbozipkin.web;


import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import zipkin.Span;


/**
 * User: luotao-pc
 * Date: 2018/6/17
 * Time: 19:38
 */

public class FactoryBrave implements FactoryBean<Brave> {

    @Autowired
    ZipkinConfig zipkinConfig;

    @Override
    public Brave getObject() throws Exception {
        String application = ZipkinConfig.getProperty(ZipkinConstants.BRAVE_NAME);
        String sendUrl = ZipkinConfig.getProperty(ZipkinConstants.SEND_ADDRESS);
        Sender sender = OkHttpSender.create(sendUrl);
        Reporter<Span> reporter = AsyncReporter.builder(sender).build();
        Brave brave = new Brave.Builder(application).reporter(reporter).build();
        return brave;
    }

    @Override
    public Class<?> getObjectType() {
        return Brave.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
