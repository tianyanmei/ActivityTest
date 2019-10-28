package com.example.activitytest.untils.common;

public interface HttpCallbackListener {
    void finish(String response);
    void error(Exception e);
}
