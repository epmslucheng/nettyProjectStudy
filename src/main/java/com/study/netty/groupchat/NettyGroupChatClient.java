package com.study.netty.groupchat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.util.Scanner;

/**
 * @Description: 客户端
 * @Author 80320838
 **/
public class NettyGroupChatClient {
    private final String ip;
    private final int port;

    public NettyGroupChatClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public static void main(String[] args) {
        NettyGroupChatClient client = new NettyGroupChatClient("127.0.0.1", 6666);
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
                            pipeline.addLast("decoder", new ObjectDecoder(ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader())));
                            pipeline.addLast("encoder", new ObjectEncoder());
                            pipeline.addLast(new GroupChatClientHandler());

                        }
                    });

            ChannelFuture cf = bootstrap.connect(ip, port).sync();
            Channel channel = cf.channel();
            System.out.println("客户端" + channel.remoteAddress() + "准备完成");
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()) {
                Msg msg = new Msg();
                msg.setFrom(channel.remoteAddress().toString());
                String in = scanner.nextLine();
                if (in.contains("#")) {
                    /**
                     * #代表是私聊信息，#前是私聊账号， #后是发送的消息
                     */
                    String[] strArray = in.split("#");
                    msg.setTo(strArray[0]);
                    msg.setMsg(strArray[1]);

                } else {
                    /**
                     * 无#代表是群聊信息
                     */
                    msg.setMsg(in);
                }
                channel.writeAndFlush(msg);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
