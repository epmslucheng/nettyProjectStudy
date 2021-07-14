package com.study.netty.protocoltcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.UUID;

/**
 * @Description:
 * @Author study
 **/
public class MyServerHandler extends SimpleChannelInboundHandler<MessageProtocol> {

    private int i = 0;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        byte[] content = msg.getContent();
        System.out.println("服务器接收消息长度为：" + msg.getLen());
        System.out.println("服务端接收到数据：" + new String(content, CharsetUtil.UTF_8));
        System.out.println("服务器接收消息次数为：" + ++(this.i));
        System.out.println("----------------------------------");
//        for (int j = 0; j < 5; j++) {
        String str = UUID.randomUUID().toString() + "";
        MessageProtocol response = new MessageProtocol();
        response.setLen(str.getBytes(CharsetUtil.UTF_8).length);
        response.setContent(str.getBytes(CharsetUtil.UTF_8));
        ctx.writeAndFlush(response);
//        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("ERROR: " + cause.getMessage());
        ctx.channel().close();
    }
}
