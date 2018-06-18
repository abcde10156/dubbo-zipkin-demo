package com.luotao.demo.zipkin.service;

import com.luotao.demo.dubbozipkin.request.RequestLong;
import com.luotao.demo.dubbozipkin.response.ResponseOrder;

/**
 * User: luotao-pc
 * Date: 2018/6/16
 * Time: 14:53
 */
public interface OrderService {
    ResponseOrder findOrder(RequestLong id);
}
