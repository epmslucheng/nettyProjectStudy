package com.study.netty.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @Description:
 * @Author 80320838
 **/
public class MyMessageDecoder extends ReplayingDecoder<Void> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("MyMessageDecoder decode 被调用");
        /**
         * 将得到的字节码转化为MessageProtocol数据包
         */
        int len = in.readInt();
        byte[] buffer = new byte[len];
        in.readBytes(buffer);
        /**
         * 封装成message对象，放到out的数据集合中，传递给下一个handler处理
         */
        MessageProtocol messageProtocol = new MessageProtocol();
        messageProtocol.setLen(len);
        messageProtocol.setContent(buffer);
        out.add(messageProtocol);
    }
}
