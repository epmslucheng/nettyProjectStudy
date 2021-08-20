package com.study.netty.dubborpc.common;

/**
 * 定义一个公共接口
 */
public interface HelloService {
    /**
     * hello
     * @param msg 消息
     * @return 返回值
     */
    String hello(String msg);
}
