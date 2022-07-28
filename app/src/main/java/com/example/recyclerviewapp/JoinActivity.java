package com.example.recyclerviewapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class JoinActivity extends AppCompatActivity {

    int version = 1;
    DatabaseOpenHelper helper;
    SQLiteDatabase database;

    EditText idEditText, pwEditText;
    Button btnJoin, btnBack;

    String sql;
    Cursor cursor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        idEditText = findViewById(R.id.et_namejoin);
        pwEditText = findViewById(R.id.et_numberjoin);

        btnJoin = findViewById(R.id.btn_yesjoin);
        btnBack = findViewById(R.id.btn_nojoin);

        helper = new DatabaseOpenHelper(JoinActivity.this, DatabaseOpenHelper.tableName, null, version);
        database = helper.getWritableDatabase();

        btnJoin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                String id = idEditText.getText().toString();
                String pw = pwEditText.getText().toString();

                if(id.length() == 0 || pw.length() == 0) {
                    //정보 미입력
                    Toast toast = Toast.makeText(JoinActivity.this, "이름과 핸드폰번호를 입력해주세요.", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                sql = "SELECT id FROM "+ helper.tableName + " WHERE id = '" + id + "'";
                cursor = database.rawQuery(sql, null);

                if(cursor.getCount() != 0){
                    //존재하는 이름입니다.
                    Toast toast = Toast.makeText(JoinActivity.this, "존재하는 이름입니다.", Toast.LENGTH_SHORT);
                    toast.show();
                }else{
                    helper.insertUser(database,id,pw);
                    Toast toast = Toast.makeText(JoinActivity.this, "가입이 완료되었습니다.", Toast.LENGTH_SHORT);
                    toast.show();
                    Intent intent_login = new Intent(JoinActivity.this, LoginActivity.class);
                    startActivity(intent_login);
                    finish();
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //취소 버튼 클릭
                Toast toast = Toast.makeText(JoinActivity.this, "로그인 화면으로 돌아갑니다.", Toast.LENGTH_SHORT);
                toast.show();
                finish(); //현재 액티비티 종료
            }
        });
    }

}
