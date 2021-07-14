package com.study.netty.simple;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Description:
 * @Author study
 **/
public class NettyServer {

    private final int port;

    public NettyServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) {
        NettyServer server = new NettyServer(6666);
        server.run();
    }

    public void run() {

        /***
         * 创造两个线程组
         *
         */
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);

        EventLoopGroup workerGroup = new NioEventLoopGroup(8);

        ServerBootstrap serverBootstrap = new ServerBootstrap();

        try {
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    /**
                     * 给pipeline指定想对应的channel
                     */
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        /**
                         * 初始化通道
                         * @param socketChannel
                         * @throws Exception
                         */
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            /***
                             * 向pipeline中加入解码器
                             */

                            pipeline.addLast(new NettyServerHandler());
                        }
                    });
            System.out.println("服务器启动成功");

            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
