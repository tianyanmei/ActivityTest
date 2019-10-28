package com.example.activitytest.pojo;

public class Msg {
    public   static final int TYPE_RECEIVED=0; //接收
    public  static  final  int TYPE_SEND =1; //发送

    private  String content; //消息实体
    private int type; //类型

    public Msg() {
    }

    public Msg(String content, int type) {
        this.content = content;
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
