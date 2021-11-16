package com.cookandroid.to_da_project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SettingUserSecession extends AppCompatActivity {

    Button btn_secession_NO, btn_secession_YES;
    EditText Ed_Ex_id, Ed_Ex_pass;

    MyDBHelper myHelper;
    DiaryDBHelper diaryDBHelper;

    SQLiteDatabase sqlDB, sqlDB2;

    String ed_id, ed_pw;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_user_secession);

        btn_secession_NO = (Button)findViewById(R.id.btn_secession_NO);
        btn_secession_YES = (Button)findViewById(R.id.btn_secession_YES);
        Ed_Ex_id = findViewById(R.id.Ed_Ex_id);
        Ed_Ex_pass = findViewById(R.id.Ed_Ex_pass);

        myHelper = new MyDBHelper(this);
        diaryDBHelper = new DiaryDBHelper(this);

        btn_secession_NO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Setting.class);
                startActivity(intent);

                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });

        btn_secession_YES.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ed_id = Ed_Ex_id.getText().toString();
                    ed_pw = Ed_Ex_pass.getText().toString();

                    SharedPreferences test = getSharedPreferences("user_info", MODE_PRIVATE);
                    String user_id = test.getString("user_id", "null");
                    String user_pw = test.getString("user_pw", "null");




                    if (!(ed_id.equals("") || ed_pw.equals(""))) {
                        int togle = 0; // 아이디나 비밀번호가 맞지않을 때 체크용 변수

                        if (ed_id.equals(user_id) && ed_pw.equals(user_pw)) {

                            // 탈퇴
                            sqlDB = myHelper.getWritableDatabase();
                            String MY_sql = "DELETE FROM userTBL WHERE userid= '" + user_id+"'";

                            sqlDB2 = diaryDBHelper.getWritableDatabase();
                            String D_sql = "DELETE FROM diaryTBL WHERE userid= '" + user_id+"'";

                            Toast.makeText(getApplicationContext(), "탈퇴되었습니다1", Toast.LENGTH_SHORT).show();

                            sqlDB.execSQL(MY_sql);
                            sqlDB.close();

                            sqlDB2.execSQL(D_sql);
                            sqlDB2.close();

                            Toast.makeText(getApplicationContext(), "탈퇴되었습니다", Toast.LENGTH_SHORT).show();
                            togle = 1;
                        }
                        if (togle == 0)
                            Toast.makeText(getApplicationContext(), "아이디 혹은 비밀번호를 확인하세요", Toast.LENGTH_SHORT).show();
                    } else if (ed_id.equals("") && ed_pw.equals("")) {
                        Toast.makeText(getApplicationContext(), "값을 입력해주세요", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "아이디/비밀번호를 전부 입력해주세요", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "확인이 실패하였습니다", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
