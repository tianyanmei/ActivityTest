package com.example.activitytest.downloadtest;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.activitytest.R;

import java.io.File;

public class DownloadService extends Service {

    public static  final  String DOWN_CHANNEL="channel_down";
    private  String downUrl;

    private  DownloadTask downloadTask;

    private  DownloadBinder downloadBinder;


    private  DownloadListener downloadListener= new DownloadListener() {
        @Override
        public void onProgress(int progress) {
            getNotificationManger().notify(1,
                    getNotification("download...",progress));

        }

        @Override
        public void onSuccess() {
            downloadTask =null;
            stopForeground(true); //下载成功直接关闭notification
            getNotificationManger().notify(1,
                    getNotification("download success",-1));
            Toast.makeText(DownloadService.this,"download success",
                    Toast.LENGTH_LONG).show();
        }

        @Override
        public void onFailed() {
            downloadTask =null;
            stopForeground(true); //下载成功直接关闭notification
            getNotificationManger().notify(1,
                    getNotification("download failed",-1));
            Toast.makeText(DownloadService.this,"download failed",
                    Toast.LENGTH_LONG).show();
        }

        @Override
        public void onPaused() {
            downloadTask =null;
            Toast.makeText(DownloadService.this,"download paused",
                    Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCancle() {
            downloadTask =null;
            stopForeground(true); //下载成功直接关闭notification
            Toast.makeText(DownloadService.this,"download cancle",
                    Toast.LENGTH_LONG).show();

        }
    };


    public DownloadService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
       downloadBinder=new DownloadBinder();
       return  downloadBinder;
    }

    //获取notificationManger实例

    public NotificationManager getNotificationManger(){
        //根据判断是否需要加上通道
        NotificationManager notificationManager =(NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT>Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new
                    NotificationChannel(DOWN_CHANNEL,"下载进度",
                    NotificationManager.IMPORTANCE_DEFAULT);

            notificationManager.createNotificationChannel(notificationChannel);
        }


        return notificationManager;
    }

    public Notification getNotification(String title, int progress){


        if(Build.VERSION.SDK_INT>Build.VERSION_CODES.O){
            NotificationManager notificationManager =(NotificationManager)
                    getSystemService(NOTIFICATION_SERVICE);
            NotificationChannel notificationChannel = new
                    NotificationChannel(DOWN_CHANNEL,"下载进度",
                    NotificationManager.IMPORTANCE_DEFAULT);

            notificationManager.createNotificationChannel(notificationChannel);
        }

        Intent intent = new Intent(this,DownloadMainActivity.class);
        PendingIntent pi =PendingIntent.getActivity(this,0,intent,0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"channel_down");
        builder.setContentTitle(title).setSmallIcon(R.drawable.mt401).setContentIntent(pi);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.mt401));
        if(progress>0){
            //progress大于0显示下载进度条
            builder.setContentText(progress+"%");
            builder.setProgress(100,progress,false);
        }
        return  builder.build();

    };


    class DownloadBinder extends Binder {


        public  void  startDownload(String downloadUrl){
            if(downloadTask ==null){
                downUrl=downloadUrl;
                downloadTask = new DownloadTask(downloadListener);
                downloadTask.execute(downloadUrl);
                startForeground(1,getNotification("download...",0));
                Toast.makeText(DownloadService.this,"cancle",
                        Toast.LENGTH_LONG).show();
            }

        }

        public  void  pauseDownload(){
            if(downloadTask !=null){
                downloadTask.pauseDownload();
            }
        }


        public  void  cancleDownload(){
            if(downloadTask !=null){
                downloadTask.cancleDownload();
            }else {
                String fileName =downUrl.substring(downUrl.lastIndexOf("/"));
                String dirtoryPath= Environment.getExternalStoragePublicDirectory(
                       Environment.DIRECTORY_DOWNLOADS).getPath();
                File file = new File(dirtoryPath+fileName);
                if(file.exists()){
                    file.delete();
                }
                getNotificationManger().cancel(1);
                stopForeground(true);
                Toast.makeText(DownloadService.this,"cancle",
                        Toast.LENGTH_LONG).show();
            }


        }


    }

}
