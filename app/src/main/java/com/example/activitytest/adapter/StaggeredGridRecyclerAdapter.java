package com.example.activitytest.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.activitytest.R;
import com.example.activitytest.pojo.Fruit;

import java.util.List;

public class StaggeredGridRecyclerAdapter extends
        RecyclerView.Adapter<StaggeredGridRecyclerAdapter.ViewHolder> {

    static  class  ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;
        View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.staggered_ImageId);
            this.textView = itemView.findViewById(R.id.staggered_textViewId);
            this.view = itemView;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, final int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.staggered_grid_layout_fruit,parent,false);
        final ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               int position = viewHolder.getAdapterPosition();
               Fruit fruit = fruits.get(position);
               Toast.makeText(view.getContext(),"点击了图片"+fruit.getName(),
                       Toast.LENGTH_SHORT).show();
            }
        });

        viewHolder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = viewHolder.getAdapterPosition();
                Fruit fruit = fruits.get(position);
                Toast.makeText(view.getContext(),"你点击了文字"+fruit.getName(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Fruit fruit = fruits.get(position);
        holder.imageView.setImageResource(fruit.getImageId());
        holder.textView.setText(fruit.getName());
    }

    @Override
    public int getItemCount() {
        return fruits.size();
    }

    private List<Fruit> fruits;

    public StaggeredGridRecyclerAdapter(List<Fruit> fruits) {
        this.fruits = fruits;
    }
}
