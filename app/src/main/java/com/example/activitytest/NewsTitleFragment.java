package com.example.activitytest;



import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.activitytest.pojo.News;

import java.util.ArrayList;
import java.util.List;

public class NewsTitleFragment extends Fragment {

    private  boolean istwoPane;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
          View view = inflater.inflate(R.layout.new_title_frag,container,false);

          RecyclerView recyclerView = view.findViewById(R.id.news_recyclerViewId);

          LinearLayoutManager linearLayoutManager = new
                LinearLayoutManager(getActivity());

          recyclerView.setLayoutManager(linearLayoutManager);

          recyclerView.setAdapter(new
                  NewAdapt(getNews()));

        return view;
    }



    public  List<News> getNews(){
        List<News> list = new ArrayList<News>();
        for (int i=0;i<50;i++) {
            News news = new News();
            news.setTitle("标题："+i);
            news.setContent("如果你也在写一个新闻类的APP，可以参考\n" +
                    "可以使用其中的图标、图片作为你的素材\n" +
                    "如果你们老师也要求写一个新闻APP，这用处就你自己发掘了："+i);

            list.add(news);

        }



        return  list;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getActivity().findViewById(R.id.news_contents_layout)!=null){ //标识双页
           istwoPane=true;
        }else { //单页
            istwoPane=false;
        }
    }


    class NewAdapt extends RecyclerView.Adapter<NewAdapt.ViewHolder>{

        private List<News>  mlist;

        public NewAdapt(List<News> mlist) {
            this.mlist = mlist;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, final int viewType) {
            View view = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.news_item,parent,false);

            final ViewHolder viewHolder = new ViewHolder(view);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String logs= viewType+"=viewTyoe:getAdapterPosition="+viewHolder.getAdapterPosition();
                    Log.i("news_tag",logs);
                    News news = mlist.get(viewHolder.getAdapterPosition());

                    if(istwoPane){
                        NewsContentFragment newsContentFragment =
                                (NewsContentFragment)getFragmentManager().
                                        findFragmentById(R.id.news_contents_fragment);
                        newsContentFragment.refresh(news);
                    }else {

                        News_contents news_contents = new News_contents();
                        news_contents.startAction(getActivity(),news);


                    }



                }
            });



            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            News news = mlist.get(position);
            Log.i("news_tag",news.getTitle());
            holder.titleView.setText(news.getTitle());
        }

        @Override
        public int getItemCount() {
            return mlist.size();
        }

         class ViewHolder extends RecyclerView.ViewHolder{
            TextView titleView;
            public   ViewHolder(View view){
                super(view);
                titleView = view.findViewById(R.id.news_item_title);
            }

        }



    }







}

