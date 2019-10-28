package com.example.activitytest;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.activitytest.pojo.News;


public class NewsContentFragment extends Fragment {


    private  View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.fragment_news_content, container, false);
        return view;
    }


    public void  refresh(News news){
         View visibilityLayout = view.findViewById(R.id.news_visibility_layout);

         visibilityLayout.setVisibility(View.VISIBLE);

        TextView news_title= view.findViewById(R.id.news_titleId);

        TextView news_content =view.findViewById(R.id.news_contentId);

        news_title.setText(news.getTitle());

        news_content.setText(news.getContent());

    }


}
