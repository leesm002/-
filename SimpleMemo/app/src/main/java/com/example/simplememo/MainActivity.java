package com.example.simplememo;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MemoItemClickListener{

    private RecyclerView memoList;
    private MemoAdapter adapter;
    private ArrayList<MemoBean> data;
    private MemoDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new MemoDBHelper(this, "db",
                null, 1);
        data = dbHelper.get();

        memoList = findViewById(R.id.memoList);
        adapter = new MemoAdapter(data, this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        memoList.setLayoutManager(manager);
        memoList.setAdapter(adapter);
    }

    @Override
    public void onMemoItemClick(View v, int index) {
        Intent intent = new Intent(this, MemoActivity.class);
        intent.putExtra("id", data.get(index).getId());
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode != 1) return;

        this.data = dbHelper.get();
        adapter.notifyDataSetChanged();
    }

    public void onInsert(View v){
        Intent intent = new Intent(this, MemoActivity.class);
        startActivityForResult(intent, 1);
    }
}
