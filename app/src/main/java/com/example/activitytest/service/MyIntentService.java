package com.example.activitytest.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;


public class MyIntentService extends IntentService {

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i("service","MyIntentService -----onHandleIntent 处理具体的逻辑事务:"+Thread.currentThread().getId());
        try {
            Thread.sleep(5000);
        }catch (InterruptedException e){
            Log.i("service","MyIntentService --onHandleIntent 异常:"+Thread.currentThread().getId());
        }finally {
            Log.i("service","MyIntentService --onHandleIntent 结束:"+Thread.currentThread().getId());
        }


    }

    @Override
    public void onDestroy() {
        Log.i("service","MyIntentService ---onDestroy 结束服务");
        super.onDestroy();
    }
}
