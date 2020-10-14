package com.example.simplememo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MemoActivity extends AppCompatActivity {

    private MemoDBHelper dbHelper;
    private MemoBean memo;

    private EditText editTextTitle;
    private EditText editTextBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);

        dbHelper = new MemoDBHelper(this, "db", null, 1);

        editTextBody = findViewById(R.id.editTextBody);
        editTextTitle = findViewById(R.id.editTextTitle);

        int id = getIntent().getIntExtra("id", -1);
        if(id == -1){
            memo = new MemoBean();
        } else {
            memo = dbHelper.get(id);
            editTextTitle.setText(memo.getTitle());
            editTextBody.setText(memo.getBody());
        }
    }

    public void onSave(View v){
        memo.setBody(editTextBody.getText().toString());
        memo.setTitle(editTextTitle.getText().toString());
        if ( dbHelper.insert(memo) > 0 ) {
            setResult(RESULT_OK);
            finish();
        }
    }

    public void onDelete(View v){
        dbHelper.delete(memo.getId());
        setResult(RESULT_OK);
        finish();
    }
}
