package com.luotao.demo.dubbozipkin.web;

import com.github.kristofa.brave.ClientRequestAdapter;
import com.github.kristofa.brave.IdConversion;
import com.github.kristofa.brave.KeyValueAnnotation;
import com.github.kristofa.brave.SpanId;
import com.github.kristofa.brave.internal.Nullable;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * User: luotao-pc
 * Date: 2018/6/17
 * Time: 20:01
 */
public class GrpcClientHttpRequestAdapter implements ClientRequestAdapter {
    private HttpServletRequest request;

    public GrpcClientHttpRequestAdapter(HttpServletRequest request) {
        this.request = request;
    }


    public String getSpanName() {
//            	 Service ls = (Service) invocation.getArguments()[0];
//                 String serviceName = ls == null || ls.getName() == null?"unkown":ls.getName();
        return request.getRequestURI();
    }


    public void addSpanIdToRequest(@Nullable SpanId spanId) {
        Map<String, String> at = new LinkedHashMap<>();
        if (spanId == null) {
            at.put("Sampled", "0");
        } else {

            at.put("Sampled", "1");
            at.put("TraceId", spanId.traceIdString());
            at.put("SpanId", IdConversion.convertToString(spanId.spanId));

            if (spanId.nullableParentId() != null) {
                at.put("ParentSpanId", IdConversion.convertToString(spanId.parentId));
            }
        }
    }


    public Collection<KeyValueAnnotation> requestAnnotations() {

        KeyValueAnnotation an = KeyValueAnnotation.create("params", "1234567");
        return Collections.singletonList(an);
    }

    public com.twitter.zipkin.gen.Endpoint serverAddress() {
        return null;
    }

}
