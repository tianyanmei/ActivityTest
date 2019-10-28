package com.example.activitytest.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.activitytest.R;

public class MyService extends Service {

    private  DownloadBinder downloadBinder;
    private  static final String CHANNEL_ID="channel_MyService";

    public MyService() {
        this.downloadBinder= new DownloadBinder();
    }

    @Override
    public void onCreate() { //初始化时候创建
        Log.d("service","MyService-----onCreate");
        NotificationManager notificationManager =(NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.O){ //高版本需要创建通道
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,
                    "默认通知",NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        Intent intent = new Intent(this,MyServiceMainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,1,intent,0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,CHANNEL_ID);
        builder.setContentTitle("前台通知").setContentText("前台服务通知内容").setWhen(
                System.currentTimeMillis()).setSmallIcon(R.drawable.mt401).
                setContentIntent(pendingIntent);
        notificationManager.notify(1,builder.build());

        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) { //服务执行
        Log.d("service","MyService-----onStartCommand");

        Intent intent1 =new Intent(this,MyServiceMainActivity.class);
        PendingIntent pendingIntent = PendingIntent.
                getActivity(this,0,intent1,0);
        NotificationManager notificationManager =(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT>Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel("channel_service",
                    "测试下载",NotificationManager.IMPORTANCE_DEFAULT);

                    /*   notificationChannel.enableLights(false);//如果使用中的设备支持通知灯，则说明此通知通道是否应显示灯
             notificationChannel.setShowBadge(false);//是否显示角标
             notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_SECRET);
             ;*/

            notificationManager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"channel_service");
        builder.setContentTitle("download...").setSmallIcon(R.drawable.mt401).setContentIntent(pendingIntent);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.mt401));
        builder.setContentText("50%").setProgress(100,50,false);
        startForeground(1,builder.build());
        Log.d("service","MyService-----onStartCommand--前台通知");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() { //服务销毁
        Log.d("service","MyService-----onDestroy");
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d("service","MyService-----onBind");
       return  downloadBinder;
    }
}
