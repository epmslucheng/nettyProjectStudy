package com.study.netty.codec2;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;

import java.util.Scanner;

/**
 * @Description:
 * @Author 80320838
 **/
public class NettyClient {
    private final String ip;
    private final int port;

    public NettyClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public static void main(String[] args) {
        NettyClient client = new NettyClient("127.0.0.1", 6666);
        client.run();
    }

    public void run() {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast("encoder", new ProtobufEncoder());
                            pipeline.addLast("decoder", new ProtobufDecoder(MyDataInfo.myMessage.getDefaultInstance()));
                            pipeline.addLast(new NettyClientHandler());

                        }
                    });

            ChannelFuture cf = bootstrap.connect(ip, port).sync();
            Channel channel = cf.channel();
            System.out.println("客户端" + channel.remoteAddress() + "准备完成");
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()) {
                channel.writeAndFlush(scanner.nextLine());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
