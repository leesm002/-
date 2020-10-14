package com.example.simplememo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.util.ArrayList;

public class MemoDBHelper extends SQLiteOpenHelper {
    private ArrayList<MemoBean> memos;

    public MemoDBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        memos = new ArrayList<>();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table memos (id integer primary key auto increment";
        sql += ", title text, body text, time integer)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "drop table memos";
        db.execSQL(sql);
        onCreate(db);
    }

    public long insert(MemoBean memo){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", memo.getTitle());
        values.put("body", memo.getBody());
        values.put("time", System.currentTimeMillis());
        return db.insert("memos", null, values);
    }

    public int update(MemoBean memo){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", memo.getTitle());
        values.put("body", memo.getBody());
        values.put("time", System.currentTimeMillis());
        String id = String.valueOf(memo.getId());
        return db.update("memos", values,
                "id=?", new String[] {id});
    }

    public MemoBean get(int id){
        SQLiteDatabase db = getReadableDatabase();
        String idStr = String.valueOf(id);
        Cursor cursor = db.query("memos", null,
                "id=?", new String[]{idStr},
                null, null, null);
        if(cursor.moveToNext()){
            MemoBean memo = new MemoBean();
            memo.setId(cursor.getInt(cursor.getColumnIndex("id")));
            memo.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            memo.setBody(cursor.getString(cursor.getColumnIndex("body")));
            memo.setTime(cursor.getLong(cursor.getColumnIndex("time")));
            return memo;
        } else {
            return null;
        }
    }

    public ArrayList<MemoBean> get(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("memos", null,
                null, null,
                null, null, null);

        memos.clear();
        while(cursor.moveToNext()){
            MemoBean memo = new MemoBean();
            memo.setId(cursor.getInt(cursor.getColumnIndex("id")));
            memo.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            memo.setBody(cursor.getString(cursor.getColumnIndex("body")));
            memo.setTime(cursor.getLong(cursor.getColumnIndex("time")));
            memos.add(memo);
        }
        return memos;
    }

    public int delete(int id){
        SQLiteDatabase db = getWritableDatabase();
        String idStr = String.valueOf(id);
        return db.delete("memos",
                "id=?", new String[] {idStr});
    }
}
