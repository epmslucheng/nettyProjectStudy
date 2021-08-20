package com.study.netty.dubborpc.consumer;

import com.study.netty.dubborpc.common.HelloService;
import com.study.netty.dubborpc.netty.NettyClient;

/**
 * @Description:
 * @Author study
 **/
public class ClientBootstrap {
    public static final String providerName = "dubborpc:hello:";

    public static void main(String[] args) {
        NettyClient nettyClient = new NettyClient();
        HelloService helloService = (HelloService) nettyClient.getBean(HelloService.class, providerName);
        String response = helloService.hello("你好 dubbo~");
        System.out.println("调用结果 res= " + response);

    }
}
