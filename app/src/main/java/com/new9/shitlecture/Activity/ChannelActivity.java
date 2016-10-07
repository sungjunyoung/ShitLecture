package com.new9.shitlecture.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.new9.shitlecture.Adapter.ChannelAdapter;
import com.new9.shitlecture.Java.Channel;
import com.new9.shitlecture.Java.Client;
import com.new9.shitlecture.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ChannelActivity extends AppCompatActivity implements Serializable{

    private List<Channel> channelList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ChannelAdapter channelAdapter;
    private TextView idTextView;
    private TextView nameTextView;
    private Client client = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel);

        //Channel Test입니다 Firebase 연동하고 지워주세요

        channelList.add(new Channel("운영체제","조진성",1));
        channelList.add(new Channel("설계프로젝트D","이승형",1));
        channelList.add(new Channel("기초공학설계","이성원",1));
        channelList.add(new Channel("데이터베이스","정병수",1));
        channelList.add(new Channel("알고리즘","누구더라",1));



        //////////////////////////////////////////////////

        Intent intent = getIntent();
        client = (Client) intent.getSerializableExtra("CLIENT");
        idTextView = (TextView) findViewById(R.id.id);
        nameTextView = (TextView) findViewById(R.id.name);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        channelAdapter = new ChannelAdapter(channelList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(channelAdapter);
    }
   public void goInChannel(View view){
       Intent intent = new Intent(getApplicationContext(), MessageActivity.class);
       intent.putExtra("CLIENT",client);
       startActivity(intent);
   }

}
/*
        Intent intent = new Intent(getApplicationContext(), MessageActivity.class);
        intent.putExtra("CLIENT",client);
        startActivity(intent);
 */