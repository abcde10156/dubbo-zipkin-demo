package com.luotao.demo.dubbozipkin.web;

import com.alibaba.dubbo.rpc.RpcContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * User: luotao-pc
 * Date: 2018/6/18
 * Time: 14:47
 */
public class TraceInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    Tracer tracer;

    @Autowired
    public void setTracer(Tracer tracer) {
        this.tracer = tracer;
    }

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        String request_id = httpServletRequest.getHeader("requestid");

        RpcContext.getContext().setAttachment("requestid", request_id);
        MDC.put("id", request_id);
        Map<String, String> copyOfContextMap = MDC.getCopyOfContextMap();
        System.out.println("copyOfContextMap = " + copyOfContextMap);
//        TraceKeyHolder.setTraceKey(request_id);

        TraceKeyHolder.setTraceKey(tracer);
        logger.info("web tracer = " + tracer);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
//        String s = Span.idToHex(tracer.getCurrentSpan().getTraceId());
//        logger.info("traceId = " + s);
//        httpServletResponse.setHeader("trace_id", s);
//        httpServletResponse.addHeader("trace_id",s);
//        httpServletResponse.addDateHeader("trace_id",System.currentTimeMillis()+100000000);
    }
}
