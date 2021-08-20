package com.study.netty.dubborpc.provider;

import com.study.netty.dubborpc.netty.NettyServer;

/**
 * @Description:  启动服务提供者
 * @Author dubo
 **/
public class ServerBootstrap {

    public static void main(String[] args) {
        NettyServer .startServer0("127.0.0.1", 7701);
    }
}
