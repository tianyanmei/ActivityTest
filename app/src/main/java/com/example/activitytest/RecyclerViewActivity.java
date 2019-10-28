package com.example.activitytest;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.activitytest.adapter.FruitRecyclerAdapter;
import com.example.activitytest.pojo.Fruit;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends BaseActivity {

    private List<Fruit> fruitList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        initFruit();
        RecyclerView recyclerView = findViewById(R.id.recyclerViewId);

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this);

        //OrientationHelper. VERTICAL 垂直列表，
        // OrientationHelper.HORIZONTAL 水平列表

         layoutManager.setOrientation(RecyclerView.VERTICAL);



        recyclerView.setLayoutManager(layoutManager);

        FruitRecyclerAdapter fruitRecyclerAdapter = new FruitRecyclerAdapter(fruitList);

        recyclerView.setAdapter(fruitRecyclerAdapter);

    }


    private  void initFruit(){
        for (int i=0;i<3;i++){
            fruitList.add(new Fruit("柠檬",R.drawable.lemon));
            fruitList.add(new Fruit("西瓜",R.drawable.watermelon));
            fruitList.add(new Fruit("草莓",R.drawable.strawberry));
            fruitList.add(new Fruit("李子",R.drawable.plum));
            fruitList.add(new Fruit("葡萄",R.drawable.grape));
        }
    }
}
