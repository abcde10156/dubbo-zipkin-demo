package com.luotao.demo.dubbozipkin.web;

import com.github.kristofa.brave.Brave;
import com.github.kristofa.brave.SpanId;
import com.twitter.zipkin.gen.Span;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: luotao-pc
 * Date: 2018/6/17
 * Time: 19:36
 */
public class TraceInterceptor implements HandlerInterceptor {

    @Autowired
    Brave brave;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        brave.clientRequestInterceptor().handle(new GrpcClientHttpRequestAdapter(httpServletRequest));
//        SpanId spanId = brave.clientTracer().startNewSpan(httpServletRequest.getRequestURI());
//        brave.clientTracer().setClientReceived();
        final Span currentClientSpan = brave.clientSpanThreadBinder().getCurrentClientSpan();

        System.out.println("TraceInterceptor currentClientSpan = " + currentClientSpan);
//        brave.clientSpanThreadBinder().setCurrentSpan();


        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
