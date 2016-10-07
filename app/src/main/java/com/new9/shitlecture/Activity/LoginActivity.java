package com.new9.shitlecture.Activity;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.new9.shitlecture.Java.Channel;
import com.new9.shitlecture.Java.Client;
import com.new9.shitlecture.R;

import java.io.Serializable;

public class LoginActivity extends AppCompatActivity implements Serializable{
    private EditText idEditText, pwEditText;
    private Client client = new Client();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        idEditText = (EditText)findViewById(R.id.idEditText);
        pwEditText = (EditText)findViewById(R.id.pwEditText);

        PasswordTransformationMethod passwdtm = new PasswordTransformationMethod();
        pwEditText.setTransformationMethod(passwdtm);
    }
    public boolean loginValidation(String id, String pw) { //json으로 서버에 post
        if(client.postLoginData(id,pw)) return true;
        else return false;
    }

    public void loginClicked(View view) {
        String id = idEditText.getText().toString();
        String pw = pwEditText.getText().toString();

        if (loginValidation(id, pw)) {
            System.out.print("**HELLO");

            Intent intent = new Intent(getApplicationContext(), ChannelActivity.class);
            intent.putExtra("CLIENT",client);
            startActivity(intent);

        } else {
            Toast.makeText(LoginActivity.this, "아이디 혹은 패스워드를 확인해주세요.", Toast.LENGTH_LONG).show();
        }

    }
}
