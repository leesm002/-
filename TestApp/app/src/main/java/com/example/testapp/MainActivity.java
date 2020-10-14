package com.example.testapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.function.Function;

public class MainActivity extends AppCompatActivity {

    RadioButton easyMode_Button;
    RadioButton hardMode_Button;

    // 버튼 루트 지정
    private View.OnClickListener listener = v -> {

        if (v.getId() == R.id.imageButton) {
            Intent i = new Intent(this, Mondai_Passwd_Activity.class);
            startActivity(i);
        } else if (v.getId() == R.id.start_button && easyMode_Button.isChecked()) {
            Intent i = new Intent(this, Quiz.class);
            i.putExtra("start_easy","START_EASY");
            startActivity(i);
        } else if (v.getId() == R.id.start_button && hardMode_Button.isChecked()){
            Intent i = new Intent(this, Quiz.class);
            i.putExtra("start_hard","START_HARD");
            startActivity(i);
        } else if(v.getId() == R.id.start_button && !easyMode_Button.isChecked() && !hardMode_Button.isChecked())
            Toast.makeText(this,"모드를 체크 하세요.",Toast.LENGTH_SHORT).show();
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.imageButton).setOnClickListener(listener);
        findViewById(R.id.start_button).setOnClickListener(listener);

        easyMode_Button = (RadioButton)findViewById(R.id.easyMode_Button);
        hardMode_Button = (RadioButton)findViewById(R.id.hardMode_Button);
        TextView Setsumei = (TextView) findViewById(R.id.Setsumei);

        //난도 설명 리스너
        Setsumei.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        help(easyMode_Button,hardMode_Button);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        help(easyMode_Button,hardMode_Button);
                        break;
                    case MotionEvent.ACTION_UP:
                        help(easyMode_Button,hardMode_Button);
                        break;
                }
                return false;
            }
        });

    }

    //체크 된 라디오 버튼 확인 후 난도 설명 Toast
    public void help(RadioButton b1, RadioButton b2){
        if (b1.isChecked())
            Toast.makeText(MainActivity.this, "모든 문제가 객관식으로 출제됩니다.", Toast.LENGTH_SHORT).show();
        else if (b2.isChecked())
            Toast.makeText(MainActivity.this, "객관식과 주관식이 출제됩니다.", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(MainActivity.this, "모드를 선택해주세요.", Toast.LENGTH_SHORT).show();
    }

}


