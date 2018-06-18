package com.luotao.demo.zipkin.service;

import com.luotao.demo.dubbozipkin.request.RequestLong;
import com.luotao.demo.dubbozipkin.response.ResponseOrder;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * User: luotao-pc
 * Date: 2018/6/16
 * Time: 14:54
 */
@Service("orderService")
public class OrderServiceImpl implements OrderService {

    @Override
    public ResponseOrder findOrder(RequestLong id) {
        if (id.getId().equals(101L)) {
            try {
                Thread.currentThread().sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        String time = "_" + System.currentTimeMillis();
        ResponseOrder responseOrder = new ResponseOrder();
        responseOrder.setOrderName("name" + time);
        responseOrder.setOrderType("type" + time);
        return responseOrder;
    }


}
