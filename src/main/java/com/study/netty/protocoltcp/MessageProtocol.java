package com.study.netty.protocoltcp;

/**
 * @Description: 自定义协议
 * @Author study
 **/
public class MessageProtocol {

    private int len; // 长度

    private byte[] content;

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
