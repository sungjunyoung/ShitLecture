package com.new9.shitlecture.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.new9.shitlecture.Adapter.MessageAdapter;
import com.new9.shitlecture.Java.Client;
import com.new9.shitlecture.Java.Message;
import com.new9.shitlecture.R;

import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends AppCompatActivity {


    private List<Message> messageList=new ArrayList<>();
    private RecyclerView recyclerView;
    private MessageAdapter mAdapter;
    private TextView myChannel;

    private Client client = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        Intent intent = getIntent();
        client = (Client) intent.getSerializableExtra("CLIENT");



        recyclerView=(RecyclerView)findViewById(R.id.recycler_view);

        mAdapter=new MessageAdapter(messageList);
        RecyclerView.LayoutManager mLayoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        myChannel=(TextView)findViewById(R.id.thisChannelName);
    }
}
/*


 */