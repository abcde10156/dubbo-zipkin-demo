package com.luotao.demo.dubbozipkin.res;

import com.luotao.demo.dubbozipkin.request.RequestLong;
import com.luotao.demo.dubbozipkin.response.ResponseRes;
import org.springframework.stereotype.Service;

/**
 * User: luotao-pc
 * Date: 2018/6/16
 * Time: 14:54
 */
@Service("resService")
public class ResServiceImpl implements ResService {

    @Override
    public ResponseRes findRes(RequestLong id) {
        if (id.getId().equals(101L)) {
            try {
                Thread.currentThread().sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        String time = "_" + System.currentTimeMillis();
        ResponseRes responseOrder = new ResponseRes();
        responseOrder.setName("res_name" + time);
        responseOrder.setMd5("res_md5" + time);
        return responseOrder;
    }


}
