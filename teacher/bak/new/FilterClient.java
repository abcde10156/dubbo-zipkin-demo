package com.luotao.demo.dubbozipkin.web;


import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;
import com.github.kristofa.brave.*;
import com.github.kristofa.brave.internal.Util;
import com.twitter.zipkin.gen.Span;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;


@Activate(
        group = {"provider", "consumer"}
)
public class FilterClient implements Filter {

    private ClientRequestInterceptor clientRequestInterceptor;
    private ClientResponseInterceptor clientResponseInterceptor;
    private ClientSpanThreadBinder clientSpanThreadBinder;

    public FilterClient() {
//        String application = ZipkinConfig.getProperty(ZipkinConstants.BRAVE_NAME);
//        String sendUrl = ZipkinConfig.getProperty(ZipkinConstants.SEND_ADDRESS);
//        Sender sender = OkHttpSender.create(sendUrl);
//        Reporter<zipkin.Span> reporter = AsyncReporter.builder(sender).build();
//
//        Brave brave = new Brave.Builder(application).reporter(reporter).build();
//        SpanId hello = brave.clientTracer().startNewSpan("hello");
//        this.clientRequestInterceptor = Util.checkNotNull(brave.clientRequestInterceptor(), null);
//        this.clientResponseInterceptor = Util.checkNotNull(brave.clientResponseInterceptor(), null);
//        this.clientSpanThreadBinder = brave.localSpanThreadBinder();
    }

    Brave brave;

    @Autowired
    public void setBrave(Brave brave) {
        this.brave = brave;
        this.clientRequestInterceptor = Util.checkNotNull(brave.clientRequestInterceptor(), null);
        this.clientResponseInterceptor = Util.checkNotNull(brave.clientResponseInterceptor(), null);
        this.clientSpanThreadBinder = brave.clientSpanThreadBinder();
    }

    public Result invoke(Invoker<?> arg0, Invocation arg1) throws RpcException {
//        clientRequestInterceptor.handle(new GrpcClientRequestAdapter(arg1));
        Map<String, String> att = arg1.getAttachments();
        final Span currentClientSpan = clientSpanThreadBinder.getCurrentClientSpan();
        System.out.println("currentClientSpan = " + currentClientSpan);

        SpanId spanId = brave.clientTracer().startNewSpan(arg1.getMethodName());
        spanId.create(currentClientSpan.getTrace_id(),currentClientSpan.getId(),currentClientSpan.getParent_id());
        Result result;

        try {
            brave.clientSpanThreadBinder().setCurrentSpan(currentClientSpan);
            result = arg0.invoke(arg1);
            clientSpanThreadBinder.setCurrentSpan(currentClientSpan);
            clientResponseInterceptor.handle(new GrpcClientResponseAdapter(result));
        } finally {
            clientSpanThreadBinder.setCurrentSpan(currentClientSpan);
        }
        return result;
    }

}