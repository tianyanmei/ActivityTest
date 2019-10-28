package com.example.activitytest.service;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.activitytest.R;

import java.io.File;

public class MyServiceMainActivity extends AppCompatActivity  implements View.OnClickListener {

    private  DownloadBinder downloadBinder;

    private ServiceConnection serviceConnection= new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            downloadBinder =(DownloadBinder) iBinder;
            downloadBinder.startDownload();
            downloadBinder.getProgress();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_service_main);
        Button service_start_id= findViewById(R.id.service_startService_id);
        service_start_id.setOnClickListener(this);
        Button service_stop_id =findViewById(R.id.service_stopService_id);
        service_stop_id.setOnClickListener(this);

        Button binder_service_id=findViewById(R.id.service_binderService_id);
        binder_service_id.setOnClickListener(this);
        Button unbinder_service_id =findViewById(R.id.service_unbinderService_id);
        unbinder_service_id.setOnClickListener(this);

        Button service_startIntentService_id =findViewById(R.id.service_startIntentService_id);
        service_startIntentService_id.setOnClickListener(this);

        Button service_startForgroundNotification_id =findViewById(R.id.service_startForgroundNotification_id);
        service_startForgroundNotification_id.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.service_startService_id:
                Log.i("service","点击了服务启动按钮");
                Intent startintent = new Intent(this,MyService.class);
                startService(startintent);
                break;
            case R.id.service_stopService_id:
                Log.i("service","点击了服务停止按钮");
                Intent stopIntent = new Intent(this,MyService.class);
                stopService(stopIntent);
                break;
            case R.id.service_binderService_id:
                Intent bindIntent =new Intent(this,MyService.class);
                bindService(bindIntent,serviceConnection,BIND_AUTO_CREATE);
                break;

            case R.id.service_unbinderService_id:
                unbindService(serviceConnection);
                break;
            case R.id.service_startIntentService_id:
                Log.i("service","启动了Intent服务"+Thread.currentThread().getId());
                Intent startIntentService= new Intent(this,MyIntentService.class);
                startService(startIntentService);

            case R.id.service_startForgroundNotification_id:
                if(Build.VERSION.SDK_INT>Build.VERSION_CODES.O){
                    Intent intent= new Intent(this,MyService.class);
                    startForegroundService(intent);
                }

               /* Intent intent =new Intent(this,MyServiceMainActivity.class);
                PendingIntent pendingIntent = PendingIntent.
                        getActivity(this,1,intent,0);
                NotificationManager notificationManager =(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                if(Build.VERSION.SDK_INT>Build.VERSION_CODES.O){
                    NotificationChannel notificationChannel = new NotificationChannel("channel_service",
                            "测试下载",NotificationManager.IMPORTANCE_DEFAULT);

                    *//*   notificationChannel.enableLights(false);//如果使用中的设备支持通知灯，则说明此通知通道是否应显示灯
             notificationChannel.setShowBadge(false);//是否显示角标
             notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_SECRET);
             ;*//*

                    notificationManager.createNotificationChannel(notificationChannel);
                }

                NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"channel_service");
                builder.setContentTitle("download...").setSmallIcon(R.drawable.mt401).setContentIntent(pi);
                builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.mt401));
                builder.setContentText("50%").setProgress(100,50,false);
                builder.build();*/
            default:
                break;

        }

    }
}
