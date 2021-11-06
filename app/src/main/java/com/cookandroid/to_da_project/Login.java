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


    private BackDoubleClick backDoubleClick;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        btnJoin = (Button) findViewById(R.id.btnJoin);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        Ed_LoginID = (EditText) findViewById(R.id.Ed_LoginID);
        Ed_LoginPW = (EditText) findViewById(R.id.Ed_LoginPW);

        backDoubleClick = new BackDoubleClick(this);
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

                    loginID = Ed_LoginID.getText().toString();
                    loginPW = Ed_LoginPW.getText().toString();

                    if(!(loginID.equals("") || loginPW.equals(""))) {
                        int togle = 0; // 아이디나 비밀번호가 맞지않을 때 체크용 변수

                        sqlDB = myHelper.getWritableDatabase();
                        Cursor cursor = sqlDB.rawQuery("SELECT nicname, userid, userpw FROM " + "userTBL", null);
                        int count = cursor.getCount();

                        for (int i = 1; i <= count; i++) {
                            cursor.moveToNext(); // 다음 행으로
                            strName = cursor.getString(cursor.getColumnIndex("nicname"));
                            strID = cursor.getString(cursor.getColumnIndex("userid"));
                            strPW = cursor.getString(cursor.getColumnIndex("userpw"));

                            if (loginID.equals(strID) && loginPW.equals(strPW)) {

                                Intent intent = new Intent(getApplicationContext(), MainMenu.class);
                                intent.putExtra("Nicname", strName);
                                intent.putExtra("UserId", strID);
                                intent.putExtra("UserPw", strPW);

                                startActivity(intent);
                                togle = 1;
                            }
                        }
                        cursor.close();
                        sqlDB.close();

                        if (togle == 0) Toast.makeText(getApplicationContext(), "아이디 혹은 비밀번호를 확인하세요", Toast.LENGTH_SHORT).show();
                    }
                    else if(loginID.equals("") && loginPW.equals("")){
                        Toast.makeText(getApplicationContext(), "값을 입력해주세요", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "아이디/비밀번호를 전부 입력해주세요", Toast.LENGTH_SHORT).show();
                    }

                }catch(Exception e){
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "로그인에 실패하였습니다", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    @Override public void onBackPressed() {
        // super.onBackPressed();
        backDoubleClick.onBackPressed();
    }
}