package com.example.activitytest;


import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.activitytest.adapter.FruitAdapt;
import com.example.activitytest.pojo.Fruit;

import java.util.ArrayList;
import java.util.List;

public class FruitListViewActivity extends BaseActivity {

    private List<Fruit> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruit_list_view);
        initFruit();

        FruitAdapt fruitAdapt = new FruitAdapt(this,R.layout.fruit_item,
                list);
       /* ArrayAdapter<Fruit> arrayAdapter = new ArrayAdapter<Fruit>(
                this,R.layout.fruit_item,list);*/
        final ListView listView = findViewById(R.id.fruit_ListView_Id);
        listView.setAdapter(fruitAdapt);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Fruit fruit = list.get(i);
                String name = "你点击了："+fruit.getName();
                Toast.makeText(FruitListViewActivity.this,name,Toast.LENGTH_SHORT).show();



            }
        });



    }



    private  void initFruit(){
        for (int i=0;i<3;i++){
            list.add(new Fruit("柠檬",R.drawable.lemon));
            list.add(new Fruit("西瓜",R.drawable.watermelon));
            list.add(new Fruit("草莓",R.drawable.strawberry));
            list.add(new Fruit("李子",R.drawable.plum));
            list.add(new Fruit("葡萄",R.drawable.grape));
        }
    }
}
