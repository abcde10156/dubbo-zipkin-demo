package com.luotao.demo.dubbozipkin.web;


import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


@Activate(
        group = {"provider", "consumer"}
)
public class FilterClient implements Filter, ApplicationContextAware {

    public FilterClient() {
        System.out.println("1232132 = " + 1232132);
    }

    ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    Tracer tracer;

    @Autowired
    public void setTracer(Tracer tracer) {
        this.tracer = tracer;
    }


    public Result invoke(Invoker<?> arg0, Invocation arg1) throws RpcException {
//        System.out.println("tracer = " + tracer);
        Tracer traceKey = TraceKeyHolder.getTraceKey();
//        System.out.println("traceKey = " + traceKey);
        Span newSpan = traceKey.createSpan(arg0.getInterface().getName()+"."+arg1.getMethodName());
        try {
            newSpan.logEvent(Span.SPAN_ERROR_TAG_NAME);
            return arg0.invoke(arg1);
        } catch (RpcException e) {
            e.printStackTrace();
            return new RpcResult();
        } finally {
            newSpan.logEvent(org.springframework.cloud.sleuth.Span.CLIENT_RECV);
            traceKey.close(newSpan);
        }
    }

}