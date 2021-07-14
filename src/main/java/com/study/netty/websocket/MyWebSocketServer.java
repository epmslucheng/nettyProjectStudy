package com.study.netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @Description:
 * @Author 80320838
 **/
public class MyWebSocketServer {

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
                            /**
                             * http协议，使用http的编解码器
                             */
                            pipeline.addLast(new HttpServerCodec());
                            /**
                             * 以块方式写入
                             */
                            pipeline.addLast(new ChunkedWriteHandler());
                            /**
                             * 1、http传输过程中是分段的，HttpObjectAggregator可以将多个段数据聚合
                             * 2、这就是为什么当浏览器发送大量数据时，会发出多次http请求的原因
                             */
                            pipeline.addLast(new HttpObjectAggregator(8192));
                            /**
                             * 1、对于websocket，它的数据是以帧（frame）的形式传递的
                             * 2、可以看到webSocketFrame下面有6个子类
                             * 3、浏览器发送请求时 ws://localhost:6666/xxx   (xxx表示请求的uri)，即 请求资源
                             * 4、WebSocketServerProtocolHandler 将一个http协议升级为一个ws协议，即 保持长链接
                             * 5、通过状态码 101 来切换协议
                             */
                            pipeline.addLast(new WebSocketServerProtocolHandler("/hello"));
                            /**
                             * 自定义handler，处理业务逻辑
                             */
                            pipeline.addLast(new MyWebSocketServerHandler());
                        }
                    });

            ChannelFuture cf = serverBootstrap.bind(7000).sync();
            cf.channel().closeFuture().sync();
        } catch (Exception e) {
            System.out.println("系统异常：" + e.getMessage());

        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
