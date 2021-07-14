package com.study.netty.codec2;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Random;

/**
 * @Description:
 * @Author 80320838
 **/
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        /**
         * 发送proto对象到服务器
         */
        int random = new Random().nextInt(3);
        MyDataInfo.myMessage msg = null;
        if (0 == random) {
            msg = MyDataInfo.myMessage.newBuilder().setDataType(MyDataInfo.myMessage.DataType.StudentType).setStudent(MyDataInfo.Student.newBuilder().setId(1).setName("student").build()).build();
        } else {
            msg = MyDataInfo.myMessage.newBuilder().setDataType(MyDataInfo.myMessage.DataType.WorkerType).setWorker(MyDataInfo.Worker.newBuilder().setAge(10).setName("worker").build()).build();
        }
        ctx.writeAndFlush(msg);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + "发来消息：" + msg);
    }
}
