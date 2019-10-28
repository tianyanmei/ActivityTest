package com.example.activitytest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.activitytest.adapter.MsgAdapter;
import com.example.activitytest.pojo.Msg;

import java.util.ArrayList;
import java.util.List;

public class ChatWithActivity extends AppCompatActivity {


    private List<Msg> list = new ArrayList<>();

    private RecyclerView recyclerView;
    private EditText editText;
    private Button sendButton;
    private MsgAdapter msgAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_with);

        init();

        editText = findViewById(R.id.input_id);
        sendButton = findViewById(R.id.input_sendId);

        recyclerView = findViewById(R.id.message_recycleId);

        msgAdapter = new MsgAdapter(list);

        LinearLayoutManager linearLayout = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(linearLayout);

        recyclerView.setAdapter(msgAdapter);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = editText.getText().toString().trim();
                if(!"".equals(content)){
                    Msg msg = new Msg(content,Msg.TYPE_SEND);
                    list.add(msg);
                    msgAdapter.notifyItemInserted(list.size()-1); //添加到
                    recyclerView.scrollToPosition(list.size()-1); //刷新到最新一条

                    editText.setText("");





                }


            }
        });













    }


    public  void  init(){
        list.add(new Msg("Hollo guy",Msg.TYPE_RECEIVED));

        list.add(new Msg("Hollo jay",Msg.TYPE_SEND));

        list.add(new Msg("we are taking something",Msg.TYPE_RECEIVED));



    }

}
