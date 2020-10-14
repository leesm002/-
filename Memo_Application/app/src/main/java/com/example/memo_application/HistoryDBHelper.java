package com.example.memo_application;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.util.ArrayList;

public class HistoryDBHelper extends SQLiteOpenHelper {
    public HistoryDBHelper(@Nullable Context context, @Nullable String name,
                           @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table history ( sequenceNumber integer primary key autoincrement, title text, contents text)";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql = "drop table history";
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }

    public long insert(MemoBean memo){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put("title", memo.getTitle());
        value.put("contents",memo.getContents());
        return db.insert("history", null, value);
    }

    public ArrayList<MemoBean> getAll(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("history", null, null, null,null, null, null,null);      //history DB 전체를 불러옴
        ArrayList<MemoBean> result = new ArrayList<>();
        while(cursor.moveToNext()){
            MemoBean memo = new MemoBean();
            memo.setSequenceNumber(cursor.getInt(cursor.getColumnIndex("sequenceNumber")));
            memo.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            memo.setContents(cursor.getString(cursor.getColumnIndex("contents")));
            result.add(memo);
        }
        return result;
    }
}
