package com.study.netty.groupchat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 执行器
 * @Author 80320838
 **/
public class GroupChatServerHandler extends SimpleChannelInboundHandler<Msg> {

    /**
     * 群聊：定义一个channel组，管理所有的channel
     * GlobalEventExecutor是全局的事件执行器
     */
    private static final ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    /**
     * 私聊时，服务端需要维护用户通道关系
     */
    private static final Map<String, Channel> channelMap = new HashMap<>();

    /**
     * 表示链接简历，一旦连接成功，第一个被执行
     * 将当前channel加入到channelGroup
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println("[客户端:" + channel.remoteAddress() + " " + LocalDateTime.now() + " ]加入聊天室");
//        channelGroup.writeAndFlush("[客户端:" + channel.remoteAddress() + " " +LocalDateTime.now() +" ]加入聊天室");
        channelGroup.add(channel);
        channelMap.put(channel.remoteAddress().toString(), channel);
    }

    /**
     * 表示channel处于活动状态，可以用于提示 XX上线
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + "上线了！");
//        channelGroup.writeAndFlush(ctx.channel().remoteAddress() + "上线了！");
    }

    /**
     * 表可以用于提示 XX离线
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
//        channelGroup.writeAndFlush(ctx.channel().remoteAddress() + "离线了！");
        System.out.println(ctx.channel().remoteAddress() + "离线了！");
    }

    /**
     * 断开链接，提示
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
//        channelGroup.writeAndFlush("[客户端:" + channel.remoteAddress() + "]离开聊天室");
//        channelGroup.remove(channel);
        System.out.println("channelGroup大小: " + channelGroup.size());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Msg msg) throws Exception {

        if (null != msg.getTo()) {
            /**
             * 私聊直接发送
             */
            Channel toChannel = channelMap.get(msg.getTo());
            toChannel.writeAndFlush(msg);
            return;
        }
        /**
         * 未指定，当做群聊
         */
        Channel finalChannel = ctx.channel();
        Msg sendMsg = new Msg(); // 封装发送信息
        sendMsg.setFrom(finalChannel.remoteAddress().toString());
        sendMsg.setMsg(msg.getMsg());
        channelGroup.forEach(g -> {
            if (finalChannel != g) {
                sendMsg.setTo(g.remoteAddress().toString());
                g.writeAndFlush(sendMsg);
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
