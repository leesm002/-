package com.example.testapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

public class Quiz extends AppCompatActivity {

    RadioButton quiz_radio_button_1,quiz_radio_button_2,quiz_radio_button_3,quiz_radio_button_4;
    ImageView quiz_imageView_1,quiz_imageView_2,quiz_imageView_3,quiz_imageView_4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        FindViewId();

        Intent activity_intent = new Intent(this.getIntent());
        String WhatWasButton = "";
        // EASY모드면 EASY, HARD모드면 HARD
        if(activity_intent.getStringExtra("start_easy") != null)
            WhatWasButton = "EASY";
        else
            WhatWasButton = "HARD";

        // 이지모드 구현
        if(WhatWasButton == "EASY"){
            Toast.makeText(this,"Start EasyMode",Toast.LENGTH_SHORT).show();


        }
        // 하드모드 구현
        else {
            Toast.makeText(this, "Start HardMode",Toast.LENGTH_SHORT).show();


        }
    }

    //이미지모드면 이미지뷰 보이게하고 라디오버튼 텍스트 지우기, 아니면 이미지뷰 안보이게함
    private void WhatIsThisMode(Bean bean){

        if(bean.getMode_text_image() == 1){
            quiz_radio_button_1.setText("");
            quiz_radio_button_2.setText("");
            quiz_radio_button_3.setText("");
            quiz_radio_button_4.setText("");

            quiz_imageView_1.setVisibility(View.VISIBLE);
            quiz_imageView_2.setVisibility(View.VISIBLE);
            quiz_imageView_3.setVisibility(View.VISIBLE);
            quiz_imageView_4.setVisibility(View.VISIBLE);
        }else{
            quiz_imageView_1.setVisibility(View.GONE);
            quiz_imageView_2.setVisibility(View.GONE);
            quiz_imageView_3.setVisibility(View.GONE);
            quiz_imageView_4.setVisibility(View.GONE);
        }
    }

    //

    //findViewById 찾아오기
    private void FindViewId(){
        quiz_radio_button_1 = (RadioButton)findViewById(R.id.quiz_radio_button_1);
        quiz_radio_button_2 = (RadioButton)findViewById(R.id.quiz_radio_button_2);
        quiz_radio_button_3 = (RadioButton)findViewById(R.id.quiz_radio_button_3);
        quiz_radio_button_4 = (RadioButton)findViewById(R.id.quiz_radio_button_4);
        quiz_imageView_1 = (ImageView)findViewById(R.id.quiz_imageView_1);
        quiz_imageView_2 = (ImageView)findViewById(R.id.quiz_imageView_2);
        quiz_imageView_3 = (ImageView)findViewById(R.id.quiz_imageView_3);
        quiz_imageView_4 = (ImageView)findViewById(R.id.quiz_imageView_4);
    }

}
