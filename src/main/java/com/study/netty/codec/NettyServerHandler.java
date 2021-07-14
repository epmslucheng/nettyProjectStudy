package com.study.netty.codec;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Description:
 * @Author study
 **/
public class NettyServerHandler extends SimpleChannelInboundHandler<StudentPOJO.Student> {
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端【" + ctx.channel().remoteAddress() + "】上线了！");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, StudentPOJO.Student msg) throws Exception {
        System.out.println("收到客户端【" + ctx.channel().remoteAddress() + "】消息：{id=" + msg.getId() + ", name=" + msg.getName() + ", code=" + msg.getCode() + "}");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush("收到");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端【" + ctx.channel().remoteAddress() + "】下线了！");
    }
}
