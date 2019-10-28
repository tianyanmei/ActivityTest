package com.example.activitytest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ListViewActivity extends AppCompatActivity {

    //定义数据

    private String[] data = new  String[]{
            "apple 苹 ",
            "pear 梨 ",
            "apricot 杏 ",
            "peach 桃 ",
            "grape 葡萄 ",
            "banana 香蕉 ",
            "pineapple 菠萝 ",
            "plum 李 ",
            "watermelon 西瓜 ",
            "orange 橙 ",
            "lemon 柠檬 ",
            "mango 芒 ",
            "strawberry 草莓 ",
            "medlar 枇杷,欧查 ",
            "mulberry 桑椹 ",
            "nectarine 油桃 ",
            "cherry 樱桃 "};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,android.R.layout.simple_list_item_1,data);
        //ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<>(
       //         this,android.R.layout.simple_list_item_2,data);

       ListView listView = findViewById(R.id.listViewId01);
       listView.setAdapter(arrayAdapter);

       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               Toast.makeText(ListViewActivity.this,"你点击了"+data[i],Toast.LENGTH_SHORT).show();
           }
       });
    }





}
