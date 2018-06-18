package com.luotao.demo.dubbozipkin.web.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.stereotype.Component;
import zipkin2.codec.SpanBytesEncoder;
import zipkin2.reporter.AsyncReporter;
import zipkin2.reporter.ReporterMetrics;
import zipkin2.reporter.Sender;

import java.util.concurrent.TimeUnit;

/**
 * Spring XML config does not support chained builders. This converts accordingly
 */
@Component
public class AsyncReporterFactoryBean extends AbstractFactoryBean<AsyncReporter> {
    Sender sender;
    SpanBytesEncoder encoder = SpanBytesEncoder.JSON_V1;
    ReporterMetrics metrics;
    Integer messageMaxBytes;
    Integer messageTimeout;
    Integer closeTimeout;
    Integer queuedMaxSpans;
    Integer queuedMaxBytes;

    @Override
    public Class<? extends AsyncReporter> getObjectType() {
        return AsyncReporter.class;
    }

    @Override
    protected AsyncReporter createInstance() throws Exception {
        AsyncReporter.Builder builder = AsyncReporter.builder(sender);
        if (metrics != null) builder.metrics(metrics);
        if (messageMaxBytes != null) builder.messageMaxBytes(messageMaxBytes);
        if (messageTimeout != null) builder.messageTimeout(messageTimeout, TimeUnit.MILLISECONDS);
        if (closeTimeout != null) builder.closeTimeout(closeTimeout, TimeUnit.MILLISECONDS);
        if (queuedMaxSpans != null) builder.queuedMaxSpans(queuedMaxSpans);
        if (queuedMaxBytes != null) builder.queuedMaxBytes(queuedMaxBytes);
        return builder.build(encoder);
    }

    @Override
    protected void destroyInstance(AsyncReporter instance) throws Exception {
        instance.close();
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Autowired
    public void setsendSpans(Sender sender) {
        this.sender = sender;
    }

    public void setEncoder(SpanBytesEncoder encoder) {
        this.encoder = encoder;
    }

    public void setMetrics(ReporterMetrics metrics) {
        this.metrics = metrics;
    }

    public void setMessageMaxBytes(Integer messageMaxBytes) {
        this.messageMaxBytes = messageMaxBytes;
    }

    public void setMessageTimeout(Integer messageTimeout) {
        this.messageTimeout = messageTimeout;
    }

    public void setCloseTimeout(Integer closeTimeout) {
        this.closeTimeout = closeTimeout;
    }

    public void setQueuedMaxSpans(Integer queuedMaxSpans) {
        this.queuedMaxSpans = queuedMaxSpans;
    }

    public void setQueuedMaxBytes(Integer queuedMaxBytes) {
        this.queuedMaxBytes = queuedMaxBytes;
    }
}
