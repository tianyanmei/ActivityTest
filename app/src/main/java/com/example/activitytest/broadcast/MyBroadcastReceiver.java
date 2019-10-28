package com.example.activitytest.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("BroadcastReceiver","接收到广播信息BroadcastReceiver");
        Toast.makeText(context,"接收到了广播信息",Toast.LENGTH_SHORT).show();
    }
}
