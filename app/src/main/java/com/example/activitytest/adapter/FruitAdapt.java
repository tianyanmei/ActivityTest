package com.example.activitytest.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.activitytest.R;
import com.example.activitytest.pojo.Fruit;

import java.util.List;

public class FruitAdapt extends ArrayAdapter<Fruit> {

    private int resourceId;


    public FruitAdapt(Context context, int resource,  List<Fruit> objects) {
        super(context, resource, objects);
        Log.d("adapter","Method:FruitAdapt"+resource);
        resourceId=resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
         Log.d("adapter","Method:getView");
         Fruit fruit =getItem(position); //获取当前项的实例
         View view;
         ViewHolder viewHolder =new ViewHolder();
        if(convertView==null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,null);
            viewHolder.imageView  = view.findViewById(R.id.fruit_imagId);
            viewHolder.textView = view.findViewById(R.id.fruit_nameId);
            view.setTag(viewHolder);
        }else {
            view=convertView;
            viewHolder =(ViewHolder)view.getTag();

        }

        viewHolder.imageView.setImageResource(fruit.getImageId());
        viewHolder.textView.setText(fruit.getName());
        return  view;
    }


    class  ViewHolder{
        ImageView imageView;
        TextView textView;
    }
}
