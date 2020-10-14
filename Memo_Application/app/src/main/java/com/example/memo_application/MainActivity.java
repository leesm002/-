package com.example.memo_application;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private HistoryDBHelper dbHelper;


    //새 메모 버튼 리스너
    private View.OnClickListener listener = v -> {
        if(v.getId() == R.id.new_memo_button){
            Intent i = new Intent(this, MemoActivity.class);
            i.putExtra("message","new_memo");
            startActivity(i);
        } else if (v.getId() == R.id.recyclerView){
            Intent i = new Intent(this, MemoActivity.class);
            i.putExtra("message","old_memo");      //DB 생성 후에 시퀀스넘버 값으로 바꿀것
            startActivity(i);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new HistoryDBHelper(this, "file_name", null, 1); //db를 구축
        showMemos();
        findViewById(R.id.new_memo_button).setOnClickListener(listener);
    }

    private void showMemos(){
        ArrayList<MemoBean> users = dbHelper.getAll();
        for(MemoBean memo: users)
            Log.i("MAIN",memo.getTitle());
    }

}
