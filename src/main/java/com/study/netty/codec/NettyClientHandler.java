package com.study.netty.codec;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Description:
 * @Author study
 **/
public class NettyClientHandler extends SimpleChannelInboundHandler<StudentPOJO.Student> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        /**
         * 发送proto对象到服务器
         */
        StudentPOJO.Student.Builder s = StudentPOJO.Student.newBuilder().setId(12).setName("test").setCode(1);
        ctx.writeAndFlush(s);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, StudentPOJO.Student msg) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + "发来消息：" + msg);
    }
}
