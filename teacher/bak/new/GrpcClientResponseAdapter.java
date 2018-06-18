package com.luotao.demo.dubbozipkin.web;

import com.alibaba.dubbo.rpc.Result;
import com.github.kristofa.brave.ClientResponseAdapter;
import com.github.kristofa.brave.KeyValueAnnotation;

import java.util.Collection;
import java.util.Collections;

/**
 * User: luotao-pc
 * Date: 2018/6/17
 * Time: 20:02
 */
public class GrpcClientResponseAdapter implements ClientResponseAdapter {

    private final Result result;

    public GrpcClientResponseAdapter(Result result) {
        this.result = result;
    }


    public Collection<KeyValueAnnotation> responseAnnotations() {
        return Collections.<KeyValueAnnotation>emptyList();
                /*
                return !result.hasException()
                    ? Collections.<KeyValueAnnotation>emptyList()
                    : Collections.singletonList(KeyValueAnnotation.create("error", result.getException().getMessage()));
                    */
    }
}
