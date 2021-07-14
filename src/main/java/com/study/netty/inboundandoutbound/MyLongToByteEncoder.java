package com.study.netty.inboundandoutbound;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @Description:
 * @Author 80320838
 **/
public class MyLongToByteEncoder extends MessageToByteEncoder<Long> {

    /**
     * MessageToByteEncoder  的write 方法中会做判断acceptOutboundMessage，如果不是Long，则不会encode.
     *
     * @param ctx
     * @param msg
     * @param out
     * @throws Exception
     */
    @Override
    protected void encode(ChannelHandlerContext ctx, Long msg, ByteBuf out) throws Exception {
        System.out.println("MyLongToByteEncoder.encode 被调用");
        System.out.println("发送数据msg = " + msg);
        out.writeLong(msg);
    }
}
