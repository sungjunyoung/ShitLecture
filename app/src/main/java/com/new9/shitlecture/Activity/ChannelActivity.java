package com.new9.shitlecture.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.new9.shitlecture.Adapter.ChannelAdapter;
import com.new9.shitlecture.Java.Channel;
import com.new9.shitlecture.Java.Client;
import com.new9.shitlecture.Java.User;
import com.new9.shitlecture.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ChannelActivity extends AppCompatActivity implements Serializable {

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

        channelList = User.getChannelList();

        Intent intent = getIntent();
        client = (Client) intent.getSerializableExtra("CLIENT");
        idTextView = (TextView) findViewById(R.id.id);
        nameTextView = (TextView) findViewById(R.id.name);
        idTextView.setText(User.getId());
        nameTextView.setText(User.getName());
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        channelAdapter = new ChannelAdapter(channelList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(channelAdapter);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(ChannelActivity.this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        TextView channelName = (TextView) view.findViewById(R.id.channelName);
                        User.setSelectedChannel(channelName.getText().toString());
                        Intent intent = new Intent(getApplicationContext(), MessageActivity.class);
                        intent.putExtra("CLIENT",client);
                        startActivity(intent);
                    }
                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                })
        );
    }


   public void goInChannel(View view){
       Intent intent = new Intent(getApplicationContext(), MessageActivity.class);
       intent.putExtra("CLIENT",client);
       startActivity(intent);
   }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        User.getChannelList().clear();
    }

}

class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
        public void onLongItemClick(View view, int position);
    }

    GestureDetector mGestureDetector;

    public RecyclerItemClickListener(Context context, final RecyclerView recyclerView, OnItemClickListener listener) {
        mListener = listener;
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

        });
    }

    @Override public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
        View childView = view.findChildViewUnder(e.getX(), e.getY());
        if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
            mListener.onItemClick(childView, view.getChildAdapterPosition(childView));
            return true;
        }
        return false;
    }

    @Override public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) { }

    @Override
    public void onRequestDisallowInterceptTouchEvent (boolean disallowIntercept){}
}
