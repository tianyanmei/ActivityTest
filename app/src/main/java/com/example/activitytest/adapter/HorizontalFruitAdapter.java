package com.example.activitytest.adapter;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.activitytest.R;
import com.example.activitytest.pojo.Fruit;

import java.util.List;

public class HorizontalFruitAdapter extends RecyclerView.Adapter<HorizontalFruitAdapter.ViewHolder> {

    private List<Fruit> fruits;


    public HorizontalFruitAdapter(List<Fruit> fruits) {
        this.fruits = fruits;
    }

    static  class  ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.imageView2);
            this.textView = itemView.findViewById(R.id.textView3);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_fruit_item,
                parent,false);
        ViewHolder viewHolder = new
                ViewHolder(view);
        
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Fruit fruit =fruits.get(position);
        holder.imageView.setImageResource(fruit.getImageId());
        holder.textView.setText(fruit.getName());

    }

    @Override
    public int getItemCount() {
        return fruits.size();
    }
}
