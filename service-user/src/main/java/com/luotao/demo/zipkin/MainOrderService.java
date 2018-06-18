package com.luotao.demo.zipkin;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * User: luotao-pc
 * Date: 2018/6/16
 * Time: 14:55
 */
public class MainOrderService {
    public static void main(String[] args) {
        try {
            //测试常规服务
            ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
            context.start();


        } catch (Exception e) {
            e.printStackTrace();
        }
        synchronized (MainOrderService.class) {
            while (true) {
                try {
                    MainOrderService.class.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
//        System.out.println("consumer start");
//        OrderService demoService = context.getBean(OrderService.class);
//        System.out.println("consumer");
//        System.out.println(demoService.findOrder(1L));
    }
}
