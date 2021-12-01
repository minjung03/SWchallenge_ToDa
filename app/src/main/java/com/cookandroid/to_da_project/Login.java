
package com.cookandroid.to_da_project;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {

    EditText Ed_LoginID, Ed_LoginPW;
    Button btnJoin, btnLogin;
    CheckBox chk_autoLogin;

    MyDBHelper myHelper;
    SQLiteDatabase sqlDB;

    String loginID, loginPW;
    String strName, strID, strPW;
    String auto_ID, auto_Pass;
    private Activity activity;

    private BackDoubleClick backDoubleClick;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        btnJoin = (Button) findViewById(R.id.btnJoin);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        Ed_LoginID = (EditText) findViewById(R.id.Ed_LoginID);
        Ed_LoginPW = (EditText) findViewById(R.id.Ed_LoginPW);
        chk_autoLogin = findViewById(R.id.chk_autoLogin);

        backDoubleClick = new BackDoubleClick(this);
        myHelper = new MyDBHelper(this);

        SharedPreferences autoLogin = getSharedPreferences("auto_Login", MODE_PRIVATE);
        auto_ID = autoLogin.getString("auto_id", "");
        auto_Pass = autoLogin.getString("auto_pw", "");

        if(!(auto_ID.equals("") && auto_Pass.equals(""))){
            Toast.makeText(getApplicationContext(), "자동 로그인 되었습니다", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), MainMenu.class);
            startActivity(intent);
            overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        }

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

                                if(chk_autoLogin.isChecked()){
                                    SharedPreferences autoLogin = getSharedPreferences("auto_Login", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = autoLogin.edit();
                                    editor.putString("auto_id", loginID);
                                    editor.putString("auto_pw", loginPW);
                                    editor.commit();
                                }

                                SharedPreferences user = getSharedPreferences("user_info", MODE_PRIVATE);
                                SharedPreferences.Editor editor = user.edit();
                                editor.putString("user_name", strName);
                                editor.putString("user_id", loginID);
                                editor.putString("user_pw", loginPW);
                                editor.commit();

                                Intent intent = new Intent(getApplicationContext(), MainMenu.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.fadein, R.anim.fadeout);

                                togle = 1;
                            }
                        }
                        cursor.close();
                        sqlDB.close();

                        if (togle == 0) Toast.makeText(getApplicationContext(), "아이디 혹은 비밀번호를 확인하세요", Toast.LENGTH_SHORT).show();
                    }
                    else if(loginID.equals("") && loginPW.equals("")){
                        Toast.makeText(activity, "값을 입력해주세요", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
}