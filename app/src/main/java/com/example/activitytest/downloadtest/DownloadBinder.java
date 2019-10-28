package com.example.activitytest.downloadtest;

import android.os.Binder;

public class DownloadBinder extends Binder {


    public  void  startDownload(DownloadTask downloadTask,
             DownloadListener downloadListener,String downloadUrl){
        if(downloadTask ==null){
            downloadTask = new DownloadTask(downloadListener);
            downloadTask.execute(downloadUrl);



        }


    }


}
