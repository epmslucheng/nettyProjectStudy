package com.study.netty.dubborpc.netty;

import com.study.netty.dubborpc.common.HelloService;
import com.study.netty.dubborpc.provider.HelloServiceImpl;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Description:
 * @Author study
 **/
public class NettyServerhandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        /**
         * 获取客户端消息，并发送返回
         */
        System.out.println("收到客户端消息：" + msg);
        /**
         * 1、客户端发送的消息，定义一个协议
         * 2、消息必须以：dubborpc:hello:开头
         */
        if(msg.startsWith("dubborpc:hello:")){
            HelloService helloService = new HelloServiceImpl();
            String response = helloService.hello(msg.substring("dubborpc:hello:".length()));
            ctx.writeAndFlush(response);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
