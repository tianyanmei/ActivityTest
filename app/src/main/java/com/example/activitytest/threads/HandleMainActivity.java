package com.example.activitytest.threads;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.activitytest.R;

public class HandleMainActivity extends AppCompatActivity {

    private TextView textView;

    public static final int UPDATE_INT=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handle_main);
        textView = findViewById(R.id.handleMessage_textViewId);

        Button button = findViewById(R.id.handleMessage_buttonId);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("HandleMainActivity","开启线程");
                        Message message= new Message();
                        message.what=UPDATE_INT;
                        handler.sendMessage(message);
                    }
                }).start();
            }
        });
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            Log.i("HandleMainActivity","----"+msg.what);
           switch (msg.what){
               case UPDATE_INT:
                   //处理子线程中的UI操作
                   textView.setText("hello 你好!");
                   break;

           default:
               break;
           }
        }
    };





}
