package com.new9.shitlecture.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import java.util.List;

public class MessageActivity extends AppCompatActivity {


    private List<Message> messageList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MessageAdapter mAdapter;
    private TextView myChannel;
    private String channelString;

    private FirebaseDatabase database;
    private DatabaseReference myRef;

    private Client client = null;


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

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("messages");


        checkChannel();
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

        writeNewUser(User.getName(), String.valueOf(editText.getText()));

        editText.setText("");
    }

    private void writeNewUser(String name, String content) {
        final Message msg = new Message(name, content);

        myRef.child(channelString).push().setValue(msg);

    }
}

/*


 */