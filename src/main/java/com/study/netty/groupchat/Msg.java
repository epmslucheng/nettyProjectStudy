package com.study.netty.groupchat;

import java.io.Serializable;

/**
 * @Description: 定义消息体
 * @Author study
 **/
public class Msg implements Serializable {

    private String from;

    private String to;

    private String msg;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "from:" + this.from + " -> to: " + this.to + " [ " + this.msg + " ]";
    }
}
