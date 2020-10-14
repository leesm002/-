package com.example.testapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.testapp.Contract.Entry;

import java.util.ArrayList;

import static com.example.testapp.Contract.Entry.ROW_ID;
import static com.example.testapp.Contract.Entry.TABLE_NAME;


public class SMDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Projectt.db";
    public static final int DATABASE_VERSION = 13;

    public SMDBHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_SM_TABLE = "CREATE TABLE "+
                Entry.TABLE_NAME + " (" +
                ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                Entry.COLUMN_CONTENTS + " TEXT, " +
                Entry.COLUMN_SCORE + " TEXT, " +
                Entry.COLUMN_RIGHT_ANSWER + " INTEGER, " +
                Contract.Entry.MODE + " INTEGER, "+
                Entry.SHOW_1 + " TEXT, " +
                Entry.SHOW_2 + " TEXT, " +
                Entry.SHOW_3 + " TEXT, " +
                Entry.SHOW_4 + " TEXT, " +
                Entry.IMAGE_SHOW_1 + " BLOB, " +
                Entry.IMAGE_SHOW_2 + " BLOB, " +
                Entry.IMAGE_SHOW_3 + " BLOB, " +
                Entry.IMAGE_SHOW_4 + " BLOB" +
                ");";

        db.execSQL(SQL_CREATE_SM_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Entry.TABLE_NAME);
        onCreate(db);
    }



    public long insert(Bean user){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(Entry.COLUMN_CONTENTS, user.getContents());
        value.put(Entry.MODE, user.getMode_text_image());
        value.put(Entry.COLUMN_RIGHT_ANSWER, user.getRight_answer());
        value.put(Entry.COLUMN_SCORE, user.getScore());
        value.put(Entry.SHOW_1, user.getSHOW_1());
        value.put(Entry.SHOW_2, user.getSHOW_2());
        value.put(Entry.SHOW_3, user.getSHOW_3());
        value.put(Entry.SHOW_4, user.getSHOW_4());
        value.put(Entry.IMAGE_SHOW_1, user.getImage_SHOW_1());
        value.put(Entry.IMAGE_SHOW_2, user.getImage_SHOW_2());
        value.put(Entry.IMAGE_SHOW_3, user.getImage_SHOW_3());
        value.put(Entry.IMAGE_SHOW_4, user.getImage_SHOW_4());

        return db.insert(Entry.TABLE_NAME,null,value);
    }


    public long update(Bean user, int position){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(Entry.COLUMN_CONTENTS, user.getContents());
        value.put(Entry.MODE, user.getMode_text_image());
        value.put(Entry.COLUMN_RIGHT_ANSWER, user.getRight_answer());
        value.put(Entry.COLUMN_SCORE, user.getScore());
        value.put(Entry.SHOW_1, user.getSHOW_1());
        value.put(Entry.SHOW_2, user.getSHOW_2());
        value.put(Entry.SHOW_3, user.getSHOW_3());
        value.put(Entry.SHOW_4, user.getSHOW_4());
        value.put(Entry.IMAGE_SHOW_1, user.getImage_SHOW_1());
        value.put(Entry.IMAGE_SHOW_2, user.getImage_SHOW_2());
        value.put(Entry.IMAGE_SHOW_3, user.getImage_SHOW_3());
        value.put(Entry.IMAGE_SHOW_4, user.getImage_SHOW_4());

        return db.update(Entry.TABLE_NAME,value, ROW_ID+"="+(position), null);
    }

    //리싸이클러 뷰 위치에 맞는 행 찾기
    public Bean select_row(int position){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(Entry.TABLE_NAME, null, ROW_ID, null,
                null, null, null);
        Bean user = new Bean();

        while(cursor.moveToNext()){
            user.set_ID(cursor.getInt(cursor.getColumnIndex(ROW_ID)));
            user.setContents(cursor.getString(cursor.getColumnIndex(Entry.COLUMN_CONTENTS)));
            user.setMode_text_image(cursor.getInt(cursor.getColumnIndex(Entry.MODE)));
            user.setRight_answer(cursor.getInt(cursor.getColumnIndex(Entry.COLUMN_RIGHT_ANSWER)));
            user.setScore(cursor.getString(cursor.getColumnIndex(Entry.COLUMN_SCORE)));
            user.setSHOW_1(cursor.getString(cursor.getColumnIndex(Entry.SHOW_1)));
            user.setSHOW_2(cursor.getString(cursor.getColumnIndex(Entry.SHOW_2)));
            user.setSHOW_3(cursor.getString(cursor.getColumnIndex(Entry.SHOW_3)));
            user.setSHOW_4(cursor.getString(cursor.getColumnIndex(Entry.SHOW_4)));
            user.setImage_SHOW_1(cursor.getBlob(cursor.getColumnIndex(Entry.IMAGE_SHOW_1)));
            user.setImage_SHOW_2(cursor.getBlob(cursor.getColumnIndex(Entry.IMAGE_SHOW_2)));
            user.setImage_SHOW_3(cursor.getBlob(cursor.getColumnIndex(Entry.IMAGE_SHOW_3)));
            user.setImage_SHOW_4(cursor.getBlob(cursor.getColumnIndex(Entry.IMAGE_SHOW_4)));


            if(cursor.getPosition() == position){
                break;
            }
        }
        close();
        return user;
    }




    //리싸이클러 뷰에 표시할 아이템 목록
    public ArrayList<Bean> getAll(){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(Entry.TABLE_NAME, null, null, null,
                null, null, null);
        ArrayList<Bean> result = new ArrayList<>();

        //Cursor Sizecursor = db.query(Entry.TABLE_NAME, null, null, null,
        //       null, null, null);

        //cursor 잘라 보기...
        /*
        if(Sizecursor.moveToNext()){
            long blobStart = 1;
            long blobLen = 1;
            long blobSize = Sizecursor.getLong(0);
            byte[] bytes = blobSize > 0 ? new byte[(int)blobSize] : null;
            while (blobSize > 0){
                blobLen = blobSize > 1000000 ? 1000000 : blobSize;
                blobSize -= blobLen;

                Cursor blobCursor = db.rawQuery("select substr("  + ROW_ID

                        + ", " + blobStart + ", " + blobLen + ") from "

                        + TABLE_NAME + " where ID='"+ ROW_ID + "'", null);
                if(blobCursor.moveToNext())
                {
                    byte[] barr = blobCursor.getBlob(0);
                    if(barr != null)
                        System.arraycopy(barr, 0, bytes, (int)blobStart - 1, barr.length);
                }
                blobCursor.close();

                blobStart += blobCursor.getCount();
            }

            if(bytes != null){

            }

        } */


            while(cursor.moveToNext()){
                Bean user = new Bean();
                user.set_ID(cursor.getInt(cursor.getColumnIndex(ROW_ID)));
                user.setContents(cursor.getString(cursor.getColumnIndex(Entry.COLUMN_CONTENTS)));

                result.add(user);
            }
            close();

            return result;


    }

}
