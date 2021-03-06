package com.luotao.demo.dubbozipkin.web.bean;

import brave.Clock;
import brave.Tracing;
import brave.propagation.CurrentTraceContext;
import brave.propagation.Propagation;
import brave.sampler.Sampler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.stereotype.Component;
import zipkin2.Endpoint;
import zipkin2.Span;
import zipkin2.reporter.Reporter;

/**
 * Spring XML config does not support chained builders. This converts accordingly
 */
@Component
public class TracingFactoryBean extends AbstractFactoryBean<Tracing> {

    @Autowired
    TraceProperties traceProperties;

    String localServiceName;
    Endpoint localEndpoint;
    Reporter<Span> spanReporter;
    Clock clock;
    Sampler sampler;
    CurrentTraceContext currentTraceContext;
    Propagation.Factory propagationFactory;
    Boolean traceId128Bit;
    Boolean supportsJoin;

    @Override
    protected Tracing createInstance() throws Exception {
        Tracing.Builder builder = Tracing.newBuilder();
        if (localServiceName != null) builder.localServiceName(localServiceName);
        if (localEndpoint != null) builder.localEndpoint(localEndpoint);
        if (spanReporter != null) builder.spanReporter(spanReporter);
        if (clock != null) builder.clock(clock);
        if (sampler != null) builder.sampler(sampler);
        if (currentTraceContext != null) builder.currentTraceContext(currentTraceContext);
        if (propagationFactory != null) builder.propagationFactory(propagationFactory);
        if (traceId128Bit != null) builder.traceId128Bit(traceId128Bit);
        if (supportsJoin != null) builder.supportsJoin(supportsJoin);


        builder.sampler(Sampler.create(traceProperties.getRate()));
        return builder.build();
    }

    @Override
    protected void destroyInstance(Tracing instance) throws Exception {
        instance.close();
    }

    @Override
    public Class<? extends Tracing> getObjectType() {
        return Tracing.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public void setLocalServiceName(String localServiceName) {
        this.localServiceName = localServiceName;
    }

    public void setLocalEndpoint(Endpoint localEndpoint) {
        this.localEndpoint = localEndpoint;
    }

    public void setSpanReporter(Reporter<Span> spanReporter) {
        this.spanReporter = spanReporter;
    }

    public void setClock(Clock clock) {
        this.clock = clock;
    }

    public void setSampler(Sampler sampler) {
        this.sampler = sampler;
    }

    public void setCurrentTraceContext(CurrentTraceContext currentTraceContext) {
        this.currentTraceContext = currentTraceContext;
    }

    public Propagation.Factory getPropagationFactory() {
        return propagationFactory;
    }

    public void setPropagationFactory(Propagation.Factory propagationFactory) {
        this.propagationFactory = propagationFactory;
    }

    public void setTraceId128Bit(boolean traceId128Bit) {
        this.traceId128Bit = traceId128Bit;
    }

    public Boolean getSupportsJoin() {
        return supportsJoin;
    }

    public void setSupportsJoin(Boolean supportsJoin) {
        this.supportsJoin = supportsJoin;
    }
}
