package com.study.netty.inboundandoutbound;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @Description:
 * @Author 80320838
 **/
public class MyByteToLongDecode2 extends ReplayingDecoder<Void> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("MyByteToLongDecode2 被调用");
        /**
         * 在ReplayingDecode不需要判断数据是够足够，内部会自行处理
         */
        //        if(in.readableBytes() >= 8){
        out.add(in.readLong());
        //        }
    }
}
