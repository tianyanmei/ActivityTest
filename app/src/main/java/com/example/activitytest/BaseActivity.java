package com.example.activitytest;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    /**
     *  活动第一次初始化的时候调用此方法
     *  用于加载布局，绑定事件
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("BaseActivity","onCreate:"+getClass().getSimpleName());
        Log.d("tag","onCreate:"+getClass().getSimpleName());
    }

    /**
     *  活动由不可见变为可见时候调用
     */
    @Override
    protected void onStart() {
        super.onStart();
        Log.d("tag","onStart:"+getClass().getSimpleName());
    }

    /**
     * 活动此时位于返回栈顶，并且活动准备和用户交互时调用
     * 此时处于运行状态中
     */
    @Override
    protected void onResume() {
        super.onResume();
        Log.d("tag","onResume:"+getClass().getSimpleName());

    }

    /**
     * 系统在恢复或者启动另外一个活动时调用，
     * 通常用于释放资源和保存一些关键数据，
     * 不执行耗时操作
     *
     */

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("tag","onPause:"+getClass().getSimpleName());
    }

    /**
     * 活动完全不可见时候调用
     * 如果新活动是一个对话框，则onStop不执行，onPause执行
     *
     */
    @Override
    protected void onStop() {
        super.onStop();
        Log.d("tag","onStop:"+getClass().getSimpleName());
    }


    /**
     * 活动销毁前调用
     */
    @Override
    protected void onDestroy() {
        Log.d("tag","onDestroy:"+getClass().getSimpleName());
        super.onDestroy();
    }

    /**
     * 活动由停止变为运行
     */

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("tag","onRestart:"+getClass().getSimpleName());
    }
}
