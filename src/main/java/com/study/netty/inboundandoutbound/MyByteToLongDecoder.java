package com.study.netty.inboundandoutbound;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @Description:
 * @Author study
 **/
public class MyByteToLongDecoder extends ByteToMessageDecoder {
    /**
     * decode会根据传入数据的大小，可能会被调用多次,直到确定没有新的元素加到out集合中，或者是byteBuf没有更多的字节可读
     * List out 不为空，则会将List的内容传递给下一个ChannelInboundHandler ,该处理器也会被调用多次
     *
     * @param ctx 上下文
     * @param in  入站对象
     * @param out 解码后数据存储，再传给下一个inboundHander处理
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("MyByteToLongDecoder被调用");
        if (in.readableBytes() >= 8) {
            out.add(in.readLong());
        }
    }
}
