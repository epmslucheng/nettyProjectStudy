package com.study.netty.heartbeat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author 80320838
 **/
public class MyServer {

    public static void main(String[] args) {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup(8);

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            // 加入一个netty提供的idlestateHandle
                            /**
                             * 1、 netty提供的处理空闲状态的处理器
                             * 2、  readerIdleTime  表示多长时间没有读操作，发送一个心跳检测包检测是否链接
                             *      writerIdleTime  表示多长时间没有写操作。。。
                             *      allIdleTime     表示多长时间没有读写操作。。。
                             *
                             * 3、 当IdleStateHandler触发下，会通过管道传递给下一个handler去处理。通过触发下一个handler的userEventTrigger方法处理
                             */
                            pipeline.addLast(new IdleStateHandler(3, 5, 7, TimeUnit.SECONDS));
                            /**
                             * 加入一个检测到空闲事件后自定义的处理Handler
                             */
                            pipeline.addLast(new MyServerHandler());
                        }
                    });

            ChannelFuture cf = serverBootstrap.bind(6666).sync();
            cf.channel().closeFuture().sync();


        } catch (Exception e) {
            System.out.println("系统异常：" + e.getMessage());

        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
