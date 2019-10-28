package com.example.activitytest.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.activitytest.R;
import com.example.activitytest.pojo.Msg;

import java.util.List;

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.ViewHolder> {

    private List<Msg> listMsgs;

    public MsgAdapter(List<Msg> listMsgs) {
        this.listMsgs = listMsgs;
    }

    class  ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout rightLinearLayout;
        LinearLayout leftLinearLayout;
        TextView rightTextView;
        TextView leftTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.rightLinearLayout =itemView.findViewById(R.id.msg_right_layout_id);
            this.leftLinearLayout = itemView.findViewById(R.id.msg_left_layout_id);
            this.rightTextView = itemView.findViewById(R.id.msg_right_id);
            this.leftTextView = itemView.findViewById(R.id.msg_left_id);
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meg_item,parent,false);
        return  new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Msg msg = listMsgs.get(position);

        if(Msg.TYPE_RECEIVED == msg.getType()){ //接收在左侧，隐藏右侧
            holder.leftLinearLayout.setVisibility(View.VISIBLE);
            holder.rightLinearLayout.setVisibility(View.GONE);
            holder.leftTextView.setText(msg.getContent());
        }else {
            holder.leftLinearLayout.setVisibility(View.GONE);
            holder.rightLinearLayout.setVisibility(View.VISIBLE);
            holder.rightTextView.setText(msg.getContent());
        }
    }

    @Override
    public int getItemCount() {
        return listMsgs.size();
    }


}
