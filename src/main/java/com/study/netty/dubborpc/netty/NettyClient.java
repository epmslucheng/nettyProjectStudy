package com.study.netty.dubborpc.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.lang.reflect.Proxy;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author study
 **/
public class NettyClient {

    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(2,10,60L, TimeUnit.SECONDS,new LinkedBlockingQueue(1000));

    private static NettyClientHandler client;

    private void startClient(){
        client = new NettyClientHandler();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup(2);
        try{

            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast(new StringDecoder());
                            pipeline.addLast(new StringEncoder());
                            pipeline.addLast(client);
                        }
                    });

            ChannelFuture cf = bootstrap.connect("127.0.0.1", 7701).sync();
            cf.channel().closeFuture().sync();
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            workerGroup.shutdownGracefully();
        }
    }

    public Object getBean(final Class<?> serviceClass, final String providerName){
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class<?>[]{serviceClass}, (proxy, method, args) -> {
            if (client == null || client.getContext() == null) {
                startClient();
            }
            // providerName 协议头,args[0]就是客户端 api   hello（）， 参数
            client.setParam(providerName+args[0]);

            return executor.submit(client).get();
        });
    }

}
