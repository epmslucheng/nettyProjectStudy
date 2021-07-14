package com.study.netty.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @Description:
 * @Author 80320838
 **/
public class MyClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private int i = 0;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        /**
         * 客户端发送多次数据
         */
        for (int i = 0; i < 10; i++) {
            ctx.writeAndFlush(Unpooled.copiedBuffer("hello server " + i, CharsetUtil.UTF_8));
        }

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        byte[] buffer = new byte[msg.readableBytes()];
        msg.readBytes(buffer);
        System.out.println("客户端接收信息 " + new String(buffer, CharsetUtil.UTF_8));
        System.out.println("客户端接收消息，次数为：" + (++this.i));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.channel().close();
    }
}
