package com.study.netty.dubborpc.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.Callable;

/**
 * @Description:
 * @Author study
 **/
public class NettyClientHandler extends ChannelInboundHandlerAdapter implements Callable {

    private ChannelHandlerContext context;
    private String response;
    private String param;

    /**
     * 与服务器创建连接后就被调用，这个方法时第一个被调用
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        context = ctx;

    }

    /**
     * 收到服务器数据时调用
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        response = (String) msg;
        notify(); // 唤醒
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

    /**
     * 被代理对象的调用，发送数据给服务器 -》 wait -》 等待被唤醒（channelRead） -》 返回结果
     * @return
     * @throws Exception
     */
    @Override
    public synchronized Object call() throws Exception {
        context.writeAndFlush(param);
        // 进行wait
        wait(); // 等待channelRead方法唤醒

        return response; // 服务费返回结果
    }

    void setParam(String param){
        this.param = param;
    }

    public ChannelHandlerContext getContext (){
        return this.context;
    }
}
