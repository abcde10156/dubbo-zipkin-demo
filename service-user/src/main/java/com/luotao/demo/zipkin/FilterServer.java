package com.luotao.demo.zipkin;

import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;


@Activate(
        group = {"provider", "consumer"}
)
public class FilterServer implements Filter {

//    private final ServerRequestInterceptor serverRequestInterceptor;
//    private final ServerResponseInterceptor serverResponseInterceptor;

    public FilterServer() {
//        String sendUrl = ZipkinConfig.getProperty(ZipkinConstants.SEND_ADDRESS);
//        Sender sender = OkHttpSender.create(sendUrl);
//        Reporter<Span> reporter = AsyncReporter.builder(sender).build();
//        String application = ZipkinConfig.getProperty(ZipkinConstants.BRAVE_NAME);//RpcContext.getContext().getUrl().getParameter("application");
//        Brave brave = new Brave.Builder(application).reporter(reporter).build();
//        this.serverRequestInterceptor = brave.serverRequestInterceptor();
//        this.serverResponseInterceptor = brave.serverResponseInterceptor();
    }

    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        RpcContext context = RpcContext.getContext();
        boolean isConsumerSide = context.isConsumerSide();
        System.out.println("isConsumerSide = " + isConsumerSide);
        return invoker.invoke(invocation);
    }
}