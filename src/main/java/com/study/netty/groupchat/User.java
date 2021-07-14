package com.study.netty.groupchat;

/**
 * @Description: 用户
 * @Author 80320838
 **/
public class User {

    private int id;
    private String userName;

    public User(int id, String userName) {
        this.id = id;
        this.userName = userName;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "用户名: " + this.userName + ", ID: " + this.id;
    }
}
