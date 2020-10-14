package com.example.testapp;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;
import android.widget.ToggleButton;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static android.view.View.GONE;
import static com.example.testapp.Contract.Entry.ROW_ID;
import static com.example.testapp.Contract.Entry.TABLE_NAME;

public class Mondai extends AppCompatActivity {

    private ToggleButton toggle_text_n_image;
    private EditText show_1,show_2,show_3,show_4;
    private ImageView image_show1,image_show2,image_show3,image_show4;
    private EditText mondai_editText,set_score_editText;
    private boolean IsThisComplete,IsImagesInHere,first_image_checker,second_image_checker,third_image_checker,fourth_image_checker = false;
    private RadioButton    first_radio_button,second_radio_button,third_radio_button,fourth_radio_button;
    private SMDBHelper helper;
    private SQLiteDatabase mDatabase;
    private int int_position,primary_key_num;
    final int FIRST_IMAGE = 200;  //반환값
    final int SECOND_IMAGE = 201;
    final int THIRD_IMAGE = 202;
    final int FOURTH_IMAGE = 203;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mondai);
        findviewId();
        Intent activity_intent = new Intent(this.getIntent());

        boolean WhatWasButton = activity_intent.getStringExtra("add_bt") == null ? false : true;
        //추가 버튼으로 들어 온 게 아니면 "false" , 맞으면 "true"

        //추가 버튼으로 들어왔을 때
        if(WhatWasButton){


            //DB 새로만들기
            helper = new SMDBHelper(this);
            mDatabase = helper.getWritableDatabase();

            //Intent 설정
            setResult(Activity.RESULT_CANCELED);

            //SAVE 버튼 온클릭리스너

            findViewById(R.id.Save_Button).setOnClickListener(v -> {

                if(WhatWasButton)
                    addItem();
                else
                    updateItem();
                //DB에 텍스트 다넣거나 이미지 내용 다넣었을 때 SAVED
                if (IsThisComplete || IsImagesInHere) {
                    Intent saved_i = new Intent();
                    saved_i.putExtra("save_bt", "SAVED");
                    setResult(Activity.RESULT_OK, saved_i);
                    finish();
                }
            });

            //DELETE 버튼 온클릭리스너
            findViewById(R.id.Delete_Button).setOnClickListener(v -> {
                Intent delete_i = new Intent();
                delete_i.putExtra("del_bt","DELETED");
                setResult(Activity.RESULT_OK, delete_i);
                finish();
            });

            //맨 처음은 이미지뷰 숨기기
            image_show1.setVisibility(GONE);
            image_show2.setVisibility(GONE);
            image_show3.setVisibility(GONE);
            image_show4.setVisibility(GONE);
        }
        //리사이클러뷰 에서 들어왔을 때
        else{

            int_position = activity_intent.getIntExtra("item_bt",-1);


            helper = new SMDBHelper(this);
            Bean loaded_data = helper.select_row(int_position);
            primary_key_num = loaded_data.get_ID();
            Toast.makeText(this,primary_key_num+"",Toast.LENGTH_SHORT).show();
            mDatabase = helper.getWritableDatabase();

            //불러온 값을 액티비티 내 컴포넌트들에 대입
            load_data(loaded_data);

            //Intent 설정
            setResult(Activity.RESULT_CANCELED);

            //SAVE 버튼 온클릭리스너

            findViewById(R.id.Save_Button).setOnClickListener(v -> {

                if(WhatWasButton)
                    addItem();
                else
                    updateItem();
                //DB에 텍스트 다넣거나 이미지 내용 다넣었을 때 SAVED
                if (IsThisComplete || IsImagesInHere) {
                    Intent saved_i = new Intent();
                    saved_i.putExtra("save_bt", "SAVED");
                    setResult(Activity.RESULT_OK, saved_i);
                    finish();
                }
            });

            //DELETE 버튼 온클릭리스너
            findViewById(R.id.Delete_Button).setOnClickListener(v -> {
                mDatabase.delete(TABLE_NAME,ROW_ID+"="+(primary_key_num),null);
                Intent delete_i = new Intent();
                delete_i.putExtra("del_bt","DELETED");
                setResult(Activity.RESULT_OK, delete_i);
                finish();
            });

        }


        //이미지 불러오기
        image_show1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, FIRST_IMAGE);
            }
        });
        image_show2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent. setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, SECOND_IMAGE);
            }
        });
        image_show3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent. setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, THIRD_IMAGE);
            }
        });
        image_show4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent. setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, FOURTH_IMAGE);
            }
        });


        //토글 버튼 이미지모드 / 텍스트모드 변경
        toggle_text_n_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if 이미지모드 체크되면 else 텍스트모드 체크되면
                if (toggle_text_n_image.isChecked()){
                    //보이고 안보이고
                    show_1.setVisibility(GONE);
                    show_2.setVisibility(GONE);
                    show_3.setVisibility(GONE);
                    show_4.setVisibility(GONE);
                    image_show1.setVisibility(v.VISIBLE);
                    image_show2.setVisibility(v.VISIBLE);
                    image_show3.setVisibility(v.VISIBLE);
                    image_show4.setVisibility(v.VISIBLE);



                }else {
                    show_1.setVisibility(v.VISIBLE);
                    show_2.setVisibility(v.VISIBLE);
                    show_3.setVisibility(v.VISIBLE);
                    show_4.setVisibility(v.VISIBLE);
                    image_show1.setVisibility(GONE);
                    image_show2.setVisibility(GONE);
                    image_show3.setVisibility(GONE);
                    image_show4.setVisibility(GONE);
                }
            }
        });



    }

    //갤러리에서 성공적으로 이미지파일 불러왔을 때
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == FIRST_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri url = data.getData();
            try {

                Bitmap bm = MediaStore.Images.Media.getBitmap(getContentResolver(), url);
                image_show1.setImageBitmap(bm);

            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            first_image_checker = true;

            if(first_image_checker == true && second_image_checker == true && third_image_checker == true && fourth_image_checker == true)
                IsImagesInHere = true;

        }else if(requestCode == SECOND_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null){
            Uri url = data.getData();
            try {

                Bitmap bm = MediaStore.Images.Media.getBitmap(getContentResolver(), url);
                image_show2.setImageBitmap(bm);

            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            second_image_checker = true;

            if(first_image_checker == true && second_image_checker == true && third_image_checker == true && fourth_image_checker == true)
                IsImagesInHere = true;

        }else if(requestCode == THIRD_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null){
            Uri url = data.getData();
            try {

                Bitmap bm = MediaStore.Images.Media.getBitmap(getContentResolver(), url);
                image_show3.setImageBitmap(bm);

            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            third_image_checker = true;

            if(first_image_checker == true && second_image_checker == true && third_image_checker == true && fourth_image_checker == true)
                IsImagesInHere = true;

        }else if(requestCode == FOURTH_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null){
            Uri url = data.getData();
            try {

                Bitmap bm = MediaStore.Images.Media.getBitmap(getContentResolver(), url);
                image_show4.setImageBitmap(bm);

            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            fourth_image_checker = true;

            if(first_image_checker == true && second_image_checker == true && third_image_checker == true && fourth_image_checker == true)
                IsImagesInHere = true;
        }

    }




    ////////////////////////////////////////////////////////////////////////////////////////////////






    //db에 값 넣기

    //추가버튼으로 들어왔을 때
    private void addItem(){
        int right_answer = 0;   //정답 번호
        String mondai_contents = ""; //문제 내용
        int checked_checker = toggle_text_n_image.isChecked()? 1:0;    //1 = 이미지 , 0 = 텍스트
        String mondai_score = "5";  //기본값 5점
        String string_show1 ="";
        String string_show2 ="";
        String string_show3 ="";
        String string_show4 ="";
        byte[] byte_image_show1 = new byte[10000];
        byte[] byte_image_show2 = new byte[10000];
        byte[] byte_image_show3 = new byte[10000];
        byte[] byte_image_show4 = new byte[10000];

        //문제내용 값 없을 때
        if (mondai_editText.getText().toString().trim().length() == 0) {
            Toast.makeText(this, "문제 내용을 입력 해 주세요.", Toast.LENGTH_SHORT).show();
            return;
        }else
            mondai_contents = mondai_editText.getText().toString();

        //배점내용 값이 있으면 그 값을 넣고 , 없으면 기본값 5점 그대로 유지됨
        if(set_score_editText.getText().toString().trim().length() != 0){
            mondai_score = set_score_editText.getText().toString();
        }


        // 정답 버튼 체크 안했을 때
        if(first_radio_button.isChecked())
            right_answer = 1;
        else if(second_radio_button.isChecked())
            right_answer = 2;
        else if(third_radio_button.isChecked())
            right_answer = 3;
        else if(fourth_radio_button.isChecked())
            right_answer = 4;
        else{
            Toast.makeText(this, "정답 버튼을 체크 해 주세요.", Toast.LENGTH_SHORT).show();
            return;
        }


        //텍스트 모드면 값 있는지 확인하고 넣기
        if(checked_checker == 0){
            if( show_1.getText().toString().trim().length() != 0 &&
                    show_2.getText().toString().trim().length() != 0 &&
                    show_3.getText().toString().trim().length() != 0 &&
                    show_4.getText().toString().trim().length() != 0 ){
                string_show1 = show_1.getText().toString();
                string_show2 = show_2.getText().toString();
                string_show3 = show_3.getText().toString();
                string_show4 = show_4.getText().toString();

            } else {
                Toast.makeText(this, "정답 내용을 채워 주세요.", Toast.LENGTH_SHORT).show();
                return;
            }


        }

        Bean user = new Bean();
        user.setContents(mondai_contents);
        user.setMode_text_image(checked_checker);
        user.setRight_answer(right_answer);
        user.setScore(mondai_score);
        user.setSHOW_1(string_show1);
        user.setSHOW_2(string_show2);
        user.setSHOW_3(string_show3);
        user.setSHOW_4(string_show4);

        //이미지 모드면 값 있는지 확인하고 String 형 으로 넣기
        if(checked_checker == 1){
            //모든 이미지가 바뀌었으면
            if(IsImagesInHere == true){

                Drawable d1 = image_show1.getDrawable();
                Drawable d2 = image_show2.getDrawable();
                Drawable d3 = image_show3.getDrawable();
                Drawable d4 = image_show4.getDrawable();

                //bitmap 을 byte[] 로
                byte_image_show1 = getByteArrayFromDrawable(d1);
                byte_image_show2 = getByteArrayFromDrawable(d2);
                byte_image_show3 = getByteArrayFromDrawable(d3);
                byte_image_show4 = getByteArrayFromDrawable(d4);

                Log.i("1111",user.get_ID()+"아이디값");

                SQLiteStatement p = mDatabase.compileStatement("INSERT INTO List (id, image_show1, image_show2, image_show3, image_show4) VALUES (?, ?, ?, ?, ?);");

                p.bindLong(1, (user.get_ID()));
                p.bindBlob(2, byte_image_show1);
                p.bindBlob(3, byte_image_show2);
                p.bindBlob(4, byte_image_show3);
                p.bindBlob(5, byte_image_show4);
                p.execute();
            }
        }

//        user.setImage_SHOW_1(byte_image_show1);
//        user.setImage_SHOW_2(byte_image_show2);
//        user.setImage_SHOW_3(byte_image_show3);
//        user.setImage_SHOW_4(byte_image_show4);


        helper.insert(user);

        Log.i("1111","contents :" +mondai_contents+ "");
        Log.i("1111","checker :" +checked_checker+ "");
        Log.i("1111","right_answer :" +right_answer+ "");
        Log.i("1111","score :" +mondai_score+ "");
        Log.i("1111","show1 :" +string_show1+ "");
        Log.i("1111","show2 :" +string_show2+ "");
        Log.i("1111","show3 :" +string_show3+ "");
        Log.i("1111","show4 :" +string_show4+ "");
        Log.i("1111","s1:" +byte_image_show1+ "");
        Log.i("1111","s2:" +byte_image_show2+ "");
        Log.i("1111","s3:" +byte_image_show3+ "");
        Log.i("1111","s4:" +byte_image_show4+ "");

        IsThisComplete = true;
    }

    //리사이클러뷰로 들어왔을 때
    private void updateItem(){
        int right_answer = 0;   //정답 번호
        String mondai_contents = ""; //문제 내용
        int checked_checker = toggle_text_n_image.isChecked()? 1:0;    //1 = 이미지 , 0 = 텍스트
        String mondai_score = "5";  //기본값 5점
        String string_show1 ="";
        String string_show2 ="";
        String string_show3 ="";
        String string_show4 ="";
        byte[] byte_image_show1 = new byte[10000];
        byte[] byte_image_show2 = new byte[10000];
        byte[] byte_image_show3 = new byte[10000];
        byte[] byte_image_show4 = new byte[10000];

        //문제내용 값 없을 때
        if (mondai_editText.getText().toString().trim().length() == 0) {
            Toast.makeText(this, "문제 내용을 입력 해 주세요.", Toast.LENGTH_SHORT).show();
            return;
        }else
            mondai_contents = mondai_editText.getText().toString();

        //배점내용 값이 있으면 그 값을 넣고 , 없으면 기본값 5점 그대로 유지됨
        if(set_score_editText.getText().toString().trim().length() != 0){
            mondai_score = set_score_editText.getText().toString();
        }


        // 정답 버튼 체크 안했을 때
        if(first_radio_button.isChecked())
            right_answer = 1;
        else if(second_radio_button.isChecked())
            right_answer = 2;
        else if(third_radio_button.isChecked())
            right_answer = 3;
        else if(fourth_radio_button.isChecked())
            right_answer = 4;
        else{
            Toast.makeText(this, "정답 버튼을 체크 해 주세요.", Toast.LENGTH_SHORT).show();
            return;
        }


        //텍스트 모드면 값 있는지 확인하고 넣기
        if(checked_checker == 0){
            if( show_1.getText().toString().trim().length() != 0 &&
                show_2.getText().toString().trim().length() != 0 &&
                show_3.getText().toString().trim().length() != 0 &&
                show_4.getText().toString().trim().length() != 0 ){
                string_show1 = show_1.getText().toString();
                string_show2 = show_2.getText().toString();
                string_show3 = show_3.getText().toString();
                string_show4 = show_4.getText().toString();

            } else {
                Toast.makeText(this, "정답 내용을 채워 주세요.", Toast.LENGTH_SHORT).show();
                return;
            }


        }

        //이미지 모드면 값 있는지 확인하고 String 형 으로 넣기
        if(checked_checker == 1){
            //모든 이미지가 바뀌었으면
            if(IsImagesInHere == true){

                Drawable d1 = image_show1.getDrawable();
                Drawable d2 = image_show2.getDrawable();
                Drawable d3 = image_show3.getDrawable();
                Drawable d4 = image_show4.getDrawable();
                // drawable 을 bitmap 으로
                Bitmap b1 = ((BitmapDrawable)d1).getBitmap();
                Bitmap b2 = ((BitmapDrawable)d2).getBitmap();
                Bitmap b3 = ((BitmapDrawable)d3).getBitmap();
                Bitmap b4 = ((BitmapDrawable)d4).getBitmap();


                byte_image_show1 = bitmapToByteArray(b1);
                byte_image_show2 = bitmapToByteArray(b2);
                byte_image_show3 = bitmapToByteArray(b3);
                byte_image_show4 = bitmapToByteArray(b4);

            }
        }

        Bean user = new Bean();
        user.setContents(mondai_contents);
        user.setMode_text_image(checked_checker);
        user.setRight_answer(right_answer);
        user.setScore(mondai_score);
        user.setSHOW_1(string_show1);
        user.setSHOW_2(string_show2);
        user.setSHOW_3(string_show3);
        user.setSHOW_4(string_show4);
        user.setImage_SHOW_1(byte_image_show1);
        user.setImage_SHOW_1(byte_image_show2);
        user.setImage_SHOW_1(byte_image_show3);
        user.setImage_SHOW_1(byte_image_show4);


        helper.update(user, primary_key_num);

        Log.i("1111","contents :" +mondai_contents+ "");
        Log.i("1111","checker :" +checked_checker+ "");
        Log.i("1111","right_answer :" +right_answer+ "");
        Log.i("1111","score :" +mondai_score+ "");
        Log.i("1111","show1 :" +string_show1+ "");
        Log.i("1111","show2 :" +string_show2+ "");
        Log.i("1111","show3 :" +string_show3+ "");
        Log.i("1111","show4 :" +string_show4+ "");
        Log.i("1111","show4 :" +byte_image_show1+ "");
        Log.i("1111","show4 :" +byte_image_show2+ "");
        Log.i("1111","show4 :" +byte_image_show3+ "");
        Log.i("1111","show4 :" +byte_image_show4+ "");

        IsThisComplete = true;
    }

    //findeviewbyid 실행문
    public void findviewId(){
        toggle_text_n_image = (ToggleButton) findViewById(R.id.toggle_text_n_image);
        show_1 = (EditText)findViewById(R.id.show_1);
        show_2 = (EditText)findViewById(R.id.show_2);
        show_3 = (EditText)findViewById(R.id.show_3);
        show_4 = (EditText)findViewById(R.id.show_4);
        image_show1 = (ImageView)findViewById(R.id.image_show1);
        image_show2 = (ImageView)findViewById(R.id.image_show2);
        image_show3 = (ImageView)findViewById(R.id.image_show3);
        image_show4 = (ImageView)findViewById(R.id.image_show4);
        mondai_editText = (EditText)findViewById(R.id.mondai_editText);
        set_score_editText = (EditText)findViewById(R.id.set_score_editText);
        first_radio_button = (RadioButton)findViewById(R.id.first_radio_button);
        second_radio_button = (RadioButton)findViewById(R.id.second_radio_button);
        third_radio_button = (RadioButton)findViewById(R.id.third_radio_button);
        fourth_radio_button = (RadioButton)findViewById(R.id.fourth_radio_button);
    }

    //DB에서 기존 내용 가져오는 함수
    public void load_data(Bean bean_data){
        // 토글버튼 세팅
        if(bean_data.getMode_text_image() == 1)
            toggle_text_n_image.setChecked(true);
        //내용 세팅
        mondai_editText.setText(bean_data.getContents());
        //점수 세팅
        set_score_editText.setText(bean_data.getScore());
        //정답 버튼세팅
        switch (bean_data.getRight_answer()){
            case 1 : first_radio_button.setChecked(true);
                break;
            case 2 : second_radio_button.setChecked(true);
                break;
            case 3 : third_radio_button.setChecked(true);
                break;
            case 4 : fourth_radio_button.setChecked(true);
                break;
        }

        //보기 내용세팅
        if(toggle_text_n_image.isChecked()){
            //비트맵으로 이미지 모드 구현
            byte[] decodedByteArray1 = Base64.decode(bean_data.getSHOW_1(), Base64.NO_WRAP);
            Bitmap decodedBitmap1 = BitmapFactory.decodeByteArray(decodedByteArray1, 0, decodedByteArray1.length);
            image_show1.setImageBitmap(decodedBitmap1);

            byte[] decodedByteArray2 = Base64.decode(bean_data.getSHOW_2(), Base64.NO_WRAP);
            Bitmap decodedBitmap2 = BitmapFactory.decodeByteArray(decodedByteArray2, 0, decodedByteArray2.length);
            image_show1.setImageBitmap(decodedBitmap2);

            byte[] decodedByteArray3 = Base64.decode(bean_data.getSHOW_3(), Base64.NO_WRAP);
            Bitmap decodedBitmap3 = BitmapFactory.decodeByteArray(decodedByteArray3, 0, decodedByteArray3.length);
            image_show1.setImageBitmap(decodedBitmap3);

            byte[] decodedByteArray4 = Base64.decode(bean_data.getSHOW_4(), Base64.NO_WRAP);
            Bitmap decodedBitmap4 = BitmapFactory.decodeByteArray(decodedByteArray4, 0, decodedByteArray4.length);
            image_show1.setImageBitmap(decodedBitmap4);

            image_show1.setVisibility(View.VISIBLE);
            image_show2.setVisibility(View.VISIBLE);
            image_show3.setVisibility(View.VISIBLE);
            image_show4.setVisibility(View.VISIBLE);
            show_1.setVisibility(GONE);
            show_2.setVisibility(GONE);
            show_3.setVisibility(GONE);
            show_4.setVisibility(GONE);
        }else {
            //텍스트 모드 구현
            show_1.setText(bean_data.getSHOW_1());
            show_2.setText(bean_data.getSHOW_2());
            show_3.setText(bean_data.getSHOW_3());
            show_4.setText(bean_data.getSHOW_4());

            image_show1.setVisibility(GONE);
            image_show2.setVisibility(GONE);
            image_show3.setVisibility(GONE);
            image_show4.setVisibility(GONE);
        }




    }

    // Drawable 형을 byte[] 형으로 변환해주는 함수
    public byte[] getByteArrayFromDrawable(Drawable d) {
        Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] data = stream.toByteArray();

        return data;
    }

    //이미지 bitmap 으로 가져오기
    public Bitmap getAppIcon(byte[] b) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
        return bitmap;
    }

    //Bitmap 형을 String 형으로 변환해주는 함수
    public String getBase64String(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);

        byte[] imageBytes = byteArrayOutputStream.toByteArray();

        return Base64.encodeToString(imageBytes, Base64.NO_WRAP);
    }

    //String 형을 Bitmap 형으로 변환해주는 함수
    public Bitmap getBitmap(String str){
        byte[] decodedByteArray = Base64.decode(str, Base64.NO_WRAP);
        Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
        return decodedBitmap;
    }

    //Bitmap 형을 Byte[] 형으로 변환해주는 함수
    public byte[] bitmapToByteArray(Bitmap bitmap) {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

        byte[] byteArray = stream.toByteArray();

        return byteArray;

    }

    //Byte[] 형을 Bitmap 형으로 변환해주는 함수
    public Bitmap byteArrayToBitmap(byte[] byteArray, int num) {

        Bitmap bitmap = null;

        bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        return bitmap;

    }

    // drawable 타입을 bitmap으로 변경
    // Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();

    // bitmap 타입을 drawable로 변경
    // Drawable drawable = new BitmapDrawable(bitmap);


}