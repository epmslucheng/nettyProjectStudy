package com.study.netty.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.time.LocalDateTime;

/**
 * @Description: 使用文本帧（TextWebSocketFrame）数据来测试
 * @Author 80320838
 **/
public class MyWebSocketServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        System.out.println("服务器端收到消息：" + msg.text());

        ctx.channel().writeAndFlush(new TextWebSocketFrame("服务器 时间为：" + LocalDateTime.now() + " " + msg.text()));


    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        // .asLongText() 是唯一的， .asShortText() 非唯一
        System.out.println("客户端【" + ctx.channel().remoteAddress() + ", " + ctx.channel().id().asLongText() + "】已上线");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端【" + ctx.channel().remoteAddress() + ", " + ctx.channel().id().asLongText() + "】已下线");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("发生异常:" + cause.getMessage());
        ctx.channel().close();
    }
}
