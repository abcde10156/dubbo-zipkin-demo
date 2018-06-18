package com.luotao.demo.dubbozipkin.web;

import org.springframework.cloud.sleuth.Tracer;

/**
 * User: luotao-pc
 * Date: 2018/6/18
 * Time: 13:38
 */
public class TraceKeyHolder {
    private static ThreadLocal<Tracer> threadLocal = new ThreadLocal<>();

    public TraceKeyHolder() {
    }

    public static Tracer getTraceKey() {
        return  threadLocal.get();
    }

    public static void setTraceKey(Tracer traceKey) {
        threadLocal.set(traceKey);
    }

    public static void clear() {
        threadLocal.remove();
    }
}
