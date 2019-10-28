package com.example.activitytest.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.activitytest.BaseActivity;
import com.example.activitytest.R;

public class LocalBroadcastReceiver extends BaseActivity {
    private IntentFilter intentFilter;
    private LocalBroadcastManager localBroadcastManager;
    private LocalReceiver localReceiver;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_broadcast_receiver);

        localBroadcastManager = LocalBroadcastManager.getInstance(this);

        IntentFilter intentFilter = new IntentFilter();

        intentFilter.addAction("com.example.activitytest.broadcast.LocalBroadcastReceiver");
        localReceiver = new LocalReceiver();
        localBroadcastManager.registerReceiver(localReceiver,intentFilter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        localBroadcastManager.unregisterReceiver(localReceiver);
    }

    public  void  localSendBroadCast(View view){

        Intent intent = new Intent("com.example.activitytest.broadcast.LocalBroadcastReceiver");

        localBroadcastManager.sendBroadcast(intent);


    }

    class LocalReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context,"本地接口收了广播",Toast.LENGTH_SHORT).show();
        }
    }


}
