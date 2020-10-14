package com.example.testapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Mondai_Passwd_Activity extends AppCompatActivity {
    private Button Hairu;
    private String passwd_code; //입력된 값을 String 형으로 저장한 값
    private EditText passwd;   //비밀번호 입력받을 곳
    private int gotNumber = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mondai_passwd);
        passwd = (EditText) findViewById(R.id.passwd);
        Hairu = (Button) findViewById(R.id.Hairu);
        Hairu.setOnClickListener(v -> {

            if(passwd.getText().toString().trim().length() == 0){
                return;
            }else {
                passwd_code = passwd.getText().toString().trim();
                gotNumber = Integer.parseInt(passwd_code);
            }

            if(v.getId() == R.id.Hairu && gotNumber == 1234){
                Intent i = new Intent(this, Mondai_List_Add.class);
                startActivity(i);
            }
        });
    }
}
