package com.example.activitytest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_layout);
        Intent intent =getIntent(); //获取Intent
        String data =intent.getStringExtra("data");
        TextView textView2_1 = findViewById(R.id.text2_1);
        textView2_1.setText(data);

        Button button = findViewById(R.id.button_2_2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent();
                intent1.putExtra("data_return","返回参数你好");
                setResult(RESULT_OK,intent1);
                finish();
            }
        });

    }

    //重写返回键按钮
    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("data_return","返回参数你好");
        setResult(RESULT_OK,intent);
        finish();
    }

}
