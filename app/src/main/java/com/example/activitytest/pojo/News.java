package com.example.activitytest.pojo;

public class News {
    private String title; //标题
    private  String content;//内容

    public News(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public News(){

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
