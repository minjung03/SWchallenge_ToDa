package com.cookandroid.to_da_project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SettingUserSecession extends AppCompatActivity {

    Button btn_secession_YES;
    EditText Ed_Ex_id, Ed_Ex_pass;

    ImageView today_list_back;
    MyDBHelper myHelper;
    DiaryDBHelper diaryDBHelper;
    ListDBHelper listDBHelper;

    SQLiteDatabase userDB, diaryDB, listDB;

    String ed_id, ed_pw;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_user_secession);

        today_list_back = findViewById(R.id.today_list_back);
        btn_secession_YES = (Button)findViewById(R.id.btn_secession_YES);
        Ed_Ex_id = findViewById(R.id.Ed_Ex_id);
        Ed_Ex_pass = findViewById(R.id.Ed_Ex_pass);

        myHelper = new MyDBHelper(this);
        diaryDBHelper = new DiaryDBHelper(this);
        listDBHelper = new ListDBHelper(this);

        today_list_back.setOnClickListener(new View.OnClickListener() {
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
                        int togle = 0; // ???????????? ??????????????? ???????????? ??? ????????? ??????

                        if (ed_id.equals(user_id) && ed_pw.equals(user_pw)) {


                            SharedPreferences autoLogin = getSharedPreferences("auto_Login", MODE_PRIVATE);
                            SharedPreferences.Editor editor = autoLogin.edit();
                            editor.putString("auto_id", "");
                            editor.putString("auto_pw", "");
                            editor.commit();

                            // ????????? ?????? ??????
                            userDB = myHelper.getWritableDatabase();
                            String user_sql = "DELETE FROM userTBL WHERE userid= '" + user_id+"'";

                            diaryDB = diaryDBHelper.getWritableDatabase();
                            String D_sql = "DELETE FROM diaryTBL WHERE userid= '" + user_id+"'";

                            listDB = listDBHelper.getWritableDatabase();
                            String list_sql = "DELETE FROM listTBL WHERE userid= '" + user_id+"'";

                            userDB.execSQL(user_sql);
                            userDB.close();

                            diaryDB.execSQL(D_sql);
                            diaryDB.close();

                            listDB.execSQL(list_sql);
                            listDB.close();
                            togle = 1;

/*
                            SharedPreferences preferences = getSharedPreferences("change_color", MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("color", "#FFFFFF");
                            editor.putInt("setColor", 0);
                            editor.commit();
*/

                            Toast.makeText(getApplicationContext(), "?????????????????????", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.fadein, R.anim.fadeout);

                        }
                        if (togle == 0)
                            Toast.makeText(getApplicationContext(), "????????? ?????? ??????????????? ???????????????", Toast.LENGTH_SHORT).show();
                    } else if (ed_id.equals("") && ed_pw.equals("")) {
                        Toast.makeText(getApplicationContext(), "?????? ??????????????????", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "?????????/??????????????? ?????? ??????????????????", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "????????? ?????????????????????", Toast.LENGTH_SHORT).show();
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