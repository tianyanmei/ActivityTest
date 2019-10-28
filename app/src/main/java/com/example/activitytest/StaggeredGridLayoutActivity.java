package com.example.activitytest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;

import com.example.activitytest.adapter.StaggeredGridRecyclerAdapter;
import com.example.activitytest.pojo.Fruit;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StaggeredGridLayoutActivity extends BaseActivity {

    private List<Fruit> fruitList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staggered_grid_layout);
        initFruit();

        RecyclerView staggeredRecycerId = findViewById(R.id.staggeredRecycerId);

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(
             3,StaggeredGridLayoutManager.VERTICAL
        );

        staggeredRecycerId.setLayoutManager(staggeredGridLayoutManager);

        StaggeredGridRecyclerAdapter staggeredGridRecyclerAdapter =
                new StaggeredGridRecyclerAdapter(fruitList);

        staggeredRecycerId.setAdapter(staggeredGridRecyclerAdapter);

    }

    private  void initFruit(){
        for (int i=0;i<3;i++){
            fruitList.add(new Fruit(getRamdomLengthName("柠檬"),R.drawable.lemon));
            fruitList.add(new Fruit(getRamdomLengthName("西瓜"),R.drawable.watermelon));
            fruitList.add(new Fruit(getRamdomLengthName("草莓"),R.drawable.strawberry));
            fruitList.add(new Fruit(getRamdomLengthName("李子"),R.drawable.plum));
            fruitList.add(new Fruit(getRamdomLengthName("葡萄"),R.drawable.grape));
        }
    }

    private String getRamdomLengthName(String name){
        Random random = new Random();
        int length = random.nextInt(20)+1;
        StringBuilder stringBuilder = new StringBuilder();

        for (int i =0;i<length;i++){
            stringBuilder.append(name);
        }
        return  stringBuilder.toString();

    }


}
