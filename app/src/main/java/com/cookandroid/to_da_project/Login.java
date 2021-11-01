package com.cookandroid.to_da_project;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {

    EditText Ed_LoginID, Ed_LoginPW;
    Button btnJoin, btnLogin;

    MyDBHelper myHelper;
    SQLiteDatabase sqlDB;

    String loginID, loginPW;
    String strName, strID, strPW;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        btnJoin = (Button) findViewById(R.id.btnJoin);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        Ed_LoginID = (EditText) findViewById(R.id.Ed_LoginID);
        Ed_LoginPW = (EditText) findViewById(R.id.Ed_LoginPW);

        myHelper = new MyDBHelper(this);

        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Join.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    sqlDB = myHelper.getWritableDatabase();
                    Cursor cursor = sqlDB.rawQuery("SELECT nicname, userid, userpw FROM " + "userTBL", null);
                    int count = cursor.getCount();

                    for(int i=0;i<count;i++) {
                        cursor.moveToNext();
                         strName = cursor.getString(0) + "\n";
                         strID = cursor.getString(1) + "\n";
                         strPW = cursor.getString(2) + "\n";
                    }
                    loginID = Ed_LoginID.getText().toString();
                    loginPW = Ed_LoginPW.getText().toString();

                    if(loginID.equals(strID) && loginPW.equals(strPW)){
                        Intent intent = new Intent(getApplicationContext(), MainMenu.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "아이디 혹은 비밀번호를 확인하세요", Toast.LENGTH_SHORT).show();
                    }
                    cursor.close();
                    sqlDB.close();

                }catch(Exception e){
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "로그인에 실패하였습니다", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}