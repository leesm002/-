package com.example.memo_application;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MemoActivity extends AppCompatActivity{

    private EditText editText_contents;
    private EditText editText_title;
    private HistoryDBHelper dbHelper;
    private MemoBean memoBean;



    //버튼 리스너
    private View.OnClickListener listener = v -> {
        if(v.getId() == R.id.delete_button){                        //삭제 버튼 눌렀을때
            Intent i = new Intent(this, MainActivity.class);
            i.putExtra("message","delete");
            finish();
        } else if (v.getId() == R.id.save_button){                  //저장 버튼 눌렀을때
            Intent i = new Intent(this, MainActivity.class);
            i.putExtra("message","save");

            //만약 제목이 공백이면
            if(editText_title.getText().toString().length() == 0){
                Toast.makeText(getApplicationContext(),"제목이 공백입니다.", Toast.LENGTH_SHORT).show();    //알림을 띄움
            } else {
                memoBean.getSequenceNumber();
                memoBean.setTitle(editText_title.getText().toString());
                memoBean.setContents(editText_contents.getText().toString());
                dbHelper.insert(memoBean);

                finish();
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);

        dbHelper = new HistoryDBHelper(this, "file_name", null, 1);

        editText_title = findViewById(R.id.editText_title);
        editText_contents = findViewById(R.id.editText_contents);

        String message = getIntent().getStringExtra("message");
        if(message == "new_memo") {
            //저장 버튼눌렀을 때 db에 NULL 값 체크된 제목, 내용 저장
            findViewById(R.id.save_button).setOnClickListener(listener);
            //삭제 버튼눌렀을 때 그냥 나가기
        } else if(message == "old_memo"){
            //저장 버튼눌렀을 때 db에 NULL 값 체크된 제목, 내용 저장

            //삭제 버튼눌렀을 때 db 에서 해당 id 행을 삭제
        }


    }
}
