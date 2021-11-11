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

public class SettingidpassCheck extends AppCompatActivity {

    Button btn_CheckCancel;
    Button btn_CheckSumit;

    TextView Ed_Ex_id, Ed_Ex_pass;

    String ed_id, ed_pw;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_idpass_check);

        btn_CheckCancel = (Button)findViewById(R.id.btn_CheckCancel);
        btn_CheckSumit  = (Button)findViewById(R.id.btn_CheckSumit);
        Ed_Ex_id  = (EditText)findViewById(R.id.Ed_Ex_id);
        Ed_Ex_pass  = (EditText)findViewById(R.id.Ed_Ex_pass);

        btn_CheckCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Setting.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });

        btn_CheckSumit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    ed_id = Ed_Ex_id.getText().toString();
                    ed_pw = Ed_Ex_pass.getText().toString();

                    Intent intent1 = getIntent();
                    Bundle bundle = intent1.getExtras();
                    String UserId = bundle.getString("UserId");
                    String UserPw = bundle.getString("UserPw");

                    if (!(ed_id.equals("") || ed_pw.equals(""))) {
                        int togle = 0; // 아이디나 비밀번호가 맞지않을 때 체크용 변수

                            if (ed_id.equals(UserId) && ed_pw.equals(UserPw)) {

                                Intent intent = new Intent(getApplicationContext(), SettingidpassChange.class);
                                intent.putExtra("UserId", UserId);
                                intent.putExtra("UserPw", UserPw);
                                startActivity(intent);
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
                    Toast.makeText(getApplicationContext(), "변경에 실패하였습니다", Toast.LENGTH_SHORT).show();
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
