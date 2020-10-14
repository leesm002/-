package com.example.testapp;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class Mondai_List_Add extends AppCompatActivity implements ItemClickListener {
    private static final int REQ_ADD = 122;
    private static final int REQ_LOAD = 123;    //원래 있던 리스트면 123

    private SMDBHelper helper;
    private RecyclerView recyclerView;
    private Adapter adapter;
    private ArrayList<Bean> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mondai_list_add);
        findViewById(R.id.imageButton2).setOnClickListener(listener);


        //db 불러오기 or 새로만들기
        DBStarter();

    }

    //화면 재 동기화
    @Override
    protected void onRestart() {
        super.onRestart();
        DBStarter();
    }

    //Mondai 액티비티 띄우기
    private View.OnClickListener listener = v -> {
        if (v.getId() == R.id.imageButton2) {
            Intent i = new Intent(this, Mondai.class);
            i.putExtra("add_bt","ADD");
            startActivityForResult(i, REQ_ADD);
        }

    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "Result Code: Canceled", Toast.LENGTH_SHORT).show();
            return;
        }


        //추가버튼으로 들어갔을 때
        if (requestCode == REQ_ADD) {
            String resultStr = "Result Code: ";
            if (data != null) {
                String result = data.getStringExtra("save_bt");
                if (result != null)
                    resultStr += result;
            }
            if (data != null) {
                String result = data.getStringExtra("del_bt");
                if (result != null)
                    resultStr += result;
            }

            Toast.makeText(this, resultStr, Toast.LENGTH_SHORT).show();
            adapter.notifyDataSetChanged();

        }

        //리싸이클러 뷰로 들어갔을 때
        if (requestCode == REQ_LOAD) {
            String resultStr = "Result Code: ";

            if (data != null) {
                String result = data.getStringExtra("save_bt");
                if (result != null)
                    resultStr += result;
            }
            if (data != null) {
                String result = data.getStringExtra("del_bt");
                if (result != null)
                    resultStr += result;
            }
            Toast.makeText(this, resultStr, Toast.LENGTH_SHORT).show();
            adapter.notifyDataSetChanged();
        }
    }

    //눌린 리스트 값을 clicked_list에 넣어서 Mondai 액티비티로 넘김
    @Override
    public void onItemClick(View v, final int position) {
       Log.i("1111",position+"");
        Toast.makeText(this,"Loading...",Toast.LENGTH_SHORT).show();

        Intent i = new Intent(this, Mondai.class);
        i.putExtra("item_bt",position);
        startActivityForResult(i, REQ_LOAD);

    }


    private void DBStarter(){
        helper = new SMDBHelper(this);
        data = helper.getAll();

        adapter = new Adapter(data, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


    }


}

