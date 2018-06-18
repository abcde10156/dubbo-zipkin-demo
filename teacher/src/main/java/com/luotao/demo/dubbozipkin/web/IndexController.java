package com.luotao.demo.dubbozipkin.web;


import com.luotao.demo.dubbozipkin.request.RequestLong;
import com.luotao.demo.dubbozipkin.res.ResService;
import com.luotao.demo.dubbozipkin.response.ResponseOrder;
import com.luotao.demo.dubbozipkin.response.ResponseRes;
import com.luotao.demo.zipkin.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * User: luotao-pc
 * Date: 2018/6/16
 * Time: 15:36
 */
@Controller
public class IndexController implements ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Autowired
    OrderService orderService;

    @Autowired
    ResService resService;


    @RequestMapping("index")
    @ResponseBody
    public Object index(@RequestParam(name = "id") Long id) {

//        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
//        for (String beanDefinitionName : beanDefinitionNames) {
//            System.out.println("beanDefinitionName = " + beanDefinitionName);
//        }
//
//        System.out.println("orderService = " + orderService);

        ResponseOrder order = orderService.findOrder(new RequestLong(id));
        logger.info("order = " + order);
        ResponseRes order1 = resService.findRes(new RequestLong(id));
        logger.info("order1 = " + order1);
        return order;
    }
}
