package com.study.netty.heartbeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @Description:
 * @Author study
 **/
public class MyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            String eventMsg = "";
            switch (event.state()) {
                case READER_IDLE:
                    eventMsg = "读空闲";
                    break;
                case WRITER_IDLE:
                    eventMsg = "写空闲";
                    break;
                case ALL_IDLE:
                    eventMsg = "读写空闲";
                    break;
            }
            System.out.println(ctx.channel().remoteAddress() + ": " + eventMsg);
            ctx.channel().close();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.channel().close();
    }
}
