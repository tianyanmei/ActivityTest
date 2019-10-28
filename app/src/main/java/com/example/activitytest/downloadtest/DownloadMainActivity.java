package com.example.activitytest.downloadtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.activitytest.R;

public class DownloadMainActivity extends AppCompatActivity implements View.OnClickListener {

    private  DownloadService.DownloadBinder downloadBinder;


    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
           downloadBinder =(DownloadService.DownloadBinder) iBinder;

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_main);

        Button download_start = findViewById(R.id.downloadTest_start);//开始
        download_start.setOnClickListener(this);
        Button download_pause = findViewById(R.id.downloadTest_pause); //暂停
        download_pause.setOnClickListener(this);
        Button download_cancle =findViewById(R.id.downloadTest_cancle);//取消
        download_cancle.setOnClickListener(this);

        //直接启动绑定服务
        Intent intent = new Intent(this,DownloadService.class);
        //启动服务做判断
        if(Build.VERSION.SDK_INT>Build.VERSION_CODES.O){
            startForegroundService(intent);
        }else {
            startService(intent);
        }
        bindService(intent,serviceConnection, BIND_AUTO_CREATE);

        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE},1001);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1001:
                if(grantResults.length >0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){

                }else {
                    Toast.makeText(this,"无权使用，退出",Toast.LENGTH_LONG).show();
                    finish();
                }
                break;

            default:
                break;
        }


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.downloadTest_start :
                String url ="https://www.apachelounge.com/download/VS16/binaries/httpd-2.4.41-win64-VS16.zip";
                downloadBinder.startDownload(url);
                break;
            case R.id.downloadTest_pause:
                downloadBinder.pauseDownload();
                break;
            case R.id.downloadTest_cancle:
                downloadBinder.cancleDownload();
                break;
            default:
                break;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
    }
}
