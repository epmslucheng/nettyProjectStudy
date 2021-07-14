package com.study.netty.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.UUID;

/**
 * @Description:
 * @Author 80320838
 **/
public class MyServerHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private int i = 0;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        byte[] buffer = new byte[msg.readableBytes()];
        msg.readBytes(buffer);
        System.out.println("服务端接收到数据：" + new String(buffer, CharsetUtil.UTF_8));
        System.out.println("服务器接收消息，次数为：" + (++this.i));
        ctx.writeAndFlush(Unpooled.copiedBuffer(UUID.randomUUID().toString() + " ", CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.channel().close();
    }
}
