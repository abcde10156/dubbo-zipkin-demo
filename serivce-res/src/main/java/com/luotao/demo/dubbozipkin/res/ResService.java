package com.luotao.demo.dubbozipkin.res;

import com.luotao.demo.dubbozipkin.request.RequestLong;
import com.luotao.demo.dubbozipkin.response.ResponseOrder;
import com.luotao.demo.dubbozipkin.response.ResponseRes;

/**
 * User: luotao-pc
 * Date: 2018/6/16
 * Time: 14:53
 */
public interface ResService {
    ResponseRes findRes(RequestLong id);
}
