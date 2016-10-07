package com.new9.shitlecture.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.new9.shitlecture.Adapter.MessageAdapter;
import com.new9.shitlecture.Java.Client;
import com.new9.shitlecture.Java.Message;
import com.new9.shitlecture.Java.User;
import com.new9.shitlecture.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class RandomName {
    private String sUserName;

    public String getSUserName()
    {
        return this.sUserName;
    }

    public void setUserName()
    {
        StringBuffer bName = new StringBuffer();
        int iNameSize = 3;

        for(int i = 0; i <iNameSize; i++){
            char ch = (char)((Math.random() * 11172) + 0xAC00);

            bName.append(ch);
            this.sUserName = bName.toString();
        }
    }
}



public class MessageActivity extends AppCompatActivity {


    private List<Message> messageList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MessageAdapter mAdapter;
    private TextView myChannel;
    private String channelString;
    private TextView numOfPerson;

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private DatabaseReference nickRef;

    private Client client = null;

    private String nickname = "";
    private RandomName randName ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        Intent intent = getIntent();
        client = (Client) intent.getSerializableExtra("CLIENT");
        myChannel = (TextView) findViewById(R.id.thisChannelName);
        myChannel.setText(User.getSelectedChannel());
        channelString = (String) myChannel.getText();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new MessageAdapter(messageList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(MessageActivity.this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        TextView tv = (TextView) view.findViewById(R.id.content);
                        if(tv.getText().toString().contains("#")){

                            User.setSelectedChannel("익명이랑");

                            Intent intent = new Intent(getApplicationContext(), MessageActivity.class);

                            intent.putExtra("CLIENT",client);

                            startActivity(intent);
                        }

                    }
                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                })
        );

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("messages");
        nickRef = database.getReference("nick");

        randName = new RandomName();

        checkChannel();
        randName.setUserName();
        nickname = randName.getSUserName();

        prepareMessageData();
    }

    private void checkChannel() {

        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                boolean check = false;
                for (DataSnapshot m : snapshot.getChildren()) {
                    if (m.getKey().equals(channelString)) {
                        check = true;
                        break;
                    } else {
                        check = false;

                    }
                }

                if (!check) {
                    myRef.push().child(channelString);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void prepareMessageData() {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                messageList.clear();


                for (DataSnapshot m : dataSnapshot.child(channelString).getChildren()) {
                    Message message = m.getValue(Message.class);
                    messageList.add(message);
                }

                mAdapter.notifyDataSetChanged();
                recyclerView.scrollToPosition(mAdapter.getItemCount() - 1);
                Log.d("", "Success");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("", "Failed to read value.", error.toException());
            }
        });
    }


    public void clickSubmit(View view) {
        EditText editText = (EditText) findViewById(R.id.messageSend);


        String msgText = String.valueOf(editText.getText());

        writeNewUser(nickname, msgText);
        editText.setText("");
    }

    private void writeNewUser(String name, String content) {
        final Message msg = new Message(name, content);

        myRef.child(channelString).push().setValue(msg);

    }
}



class RecyclerMessageClickListener implements RecyclerView.OnItemTouchListener {
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
        public void onLongItemClick(View view, int position);
    }

    GestureDetector mGestureDetector;

    public RecyclerMessageClickListener(Context context, final RecyclerView recyclerView, OnItemClickListener listener) {
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


