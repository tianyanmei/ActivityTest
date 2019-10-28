package com.example.activitytest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.activitytest.pojo.News;

public class News_contents extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_contents);

        //获取新闻信息

        String title = getIntent().getStringExtra("news_title");

        String content = getIntent().getStringExtra("news_content");

        NewsContentFragment newsContentFragment = (NewsContentFragment)getSupportFragmentManager().
                findFragmentById(R.id.news_contents_fragmentId);

        News news = new News(title,content);

        newsContentFragment.refresh(news);

    }


    public void  startAction(Context context, News news){
        Intent intent = new Intent(context,News_contents.class);
        if(news !=null){
            intent.putExtra("news_title",news.getTitle());
            intent.putExtra("news_content",news.getContent());
        }
        context.startActivity(intent);

    }


}
