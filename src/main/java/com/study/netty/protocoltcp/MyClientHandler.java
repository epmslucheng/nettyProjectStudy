package com.study.netty.protocoltcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @Description:
 * @Author 80320838
 **/
public class MyClientHandler extends SimpleChannelInboundHandler<MessageProtocol> {
    private int i = 0;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        /**
         * 客户端发送多次数据
         */
        for (int i = 0; i < 5; i++) {
            String msg = "hello server " + i;
            MessageProtocol messageProtocol = new MessageProtocol();
            messageProtocol.setLen(msg.getBytes(CharsetUtil.UTF_8).length);
            messageProtocol.setContent(msg.getBytes(CharsetUtil.UTF_8));
            ctx.writeAndFlush(messageProtocol);
        }

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        System.out.println("客户端接收信息长度为： " + msg.getLen());
        System.out.println("客户端接收信息为： " + new String(msg.getContent(), CharsetUtil.UTF_8));
        System.out.println("客户端接收消息，次数为：" + (++this.i));
        System.out.println("----------------------------------");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("ERROR: " + cause.getMessage());
        ctx.channel().close();
    }
}
