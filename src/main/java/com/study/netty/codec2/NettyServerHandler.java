package com.study.netty.codec2;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Description:
 * @Author 80320838
 **/
public class NettyServerHandler extends SimpleChannelInboundHandler<MyDataInfo.myMessage> {
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端【" + ctx.channel().remoteAddress() + "】上线了！");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.myMessage msg) throws Exception {
        if (MyDataInfo.myMessage.DataType.StudentType == msg.getDataType()) {
            MyDataInfo.Student student = msg.getStudent();
            System.out.println("收到客户端【" + ctx.channel().remoteAddress() + "】消息：{Student: id=" + student.getId() + ", name=" + student.getName() + "}");
        } else if (MyDataInfo.myMessage.DataType.WorkerType == msg.getDataType()) {
            MyDataInfo.Worker worker = msg.getWorker();
            System.out.println("收到客户端【" + ctx.channel().remoteAddress() + "】消息：{Worker: id=" + worker.getName() + ", name=" + worker.getAge() + "}");
        } else {
            System.out.println("传输数据类型有误！");
        }

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush("收到");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端【" + ctx.channel().remoteAddress() + "】下线了！");
    }
}
