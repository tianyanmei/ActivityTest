package com.example.activitytest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class NomalActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nomal);
        if(savedInstanceState !=null){
            Log.d("tag",savedInstanceState.getString("data_nomal"));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d("tag","onSaveInstanceState:NomalActivity");
        super.onSaveInstanceState(outState);
        outState.putString("data_nomal","some nomal thing should be save");
    }
}
