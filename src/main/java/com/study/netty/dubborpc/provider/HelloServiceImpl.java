package com.study.netty.dubborpc.provider;

import com.study.netty.dubborpc.common.HelloService;

/**
 * @Description:
 * @Author study
 **/
public class HelloServiceImpl  implements HelloService {
    /**
     * 服务提供方实现
     * 当有消费者调用该方法时，返回一个结果
     * @param msg 消息
     * @return
     */
    @Override
    public String hello(String msg) {
        System.out.println("收客户端消息：" + msg);

        if(msg != null && !"".equals(msg)){
            return "你好客户端，我已经收到你的消息【" + msg + "】";
        }else{
           return  "你好客户端，我已经收到你的消息 ";
        }
    }
}
