package com.study.netty.groupchat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Description: 客户端执行器
 * @Author study
 **/
public class GroupChatClientHandler extends SimpleChannelInboundHandler<Msg> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Msg msg) throws Exception {
        System.out.println("[" + msg.getFrom() + "]发来新消息：" + msg.getMsg());
    }
}
