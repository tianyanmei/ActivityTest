package com.example.activitytest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.activitytest.adapter.HorizontalFruitAdapter;
import com.example.activitytest.pojo.Fruit;

import java.util.ArrayList;
import java.util.List;

public class HorizontalRecyclerViewActivity extends BaseActivity {

    private List<Fruit> fruitList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontal_recycler_view);
        initFruit();

        RecyclerView horizontal_recyclerId= findViewById(R.id.horizontal_recyclerId);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        layoutManager.setOrientation(RecyclerView.HORIZONTAL);

        horizontal_recyclerId.setLayoutManager(layoutManager);

        HorizontalFruitAdapter horizontalFruitAdapter =
                new HorizontalFruitAdapter(fruitList);

        horizontal_recyclerId.setAdapter(horizontalFruitAdapter);
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
