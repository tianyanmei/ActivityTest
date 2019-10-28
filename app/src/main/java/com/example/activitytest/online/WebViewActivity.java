package com.example.activitytest.online;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.activitytest.R;

public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        WebView online_webView_Id =findViewById(R.id.online_webView_Id);
        online_webView_Id.getSettings().setJavaScriptEnabled(true); //支持浏览器脚本
        online_webView_Id.setWebViewClient(new WebViewClient());
        online_webView_Id.loadUrl("https://www.baidu.com");

    }
}
