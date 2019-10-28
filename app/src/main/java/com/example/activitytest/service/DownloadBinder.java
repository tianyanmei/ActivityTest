package com.example.activitytest.service;


import android.os.Binder;
import android.util.Log;

public class DownloadBinder extends Binder {

    public  void  startDownload(){
        Log.i("service","DownloadBinder --启动绑定服务执行下载");
    }

    public  int getProgress(){
        Log.i("service","DownloadBinder --获取绑定服务下载进度");
        return 0;
    }




}
