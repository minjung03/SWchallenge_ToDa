package com.cookandroid.to_da_project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Join extends AppCompatActivity {

    Button  btnJoin;
    ImageView today_list_back;
    EditText Ed_JoinName, Ed_JoinID, Ed_JoinPW;

    MyDBHelper myHelper;
    SQLiteDatabase sqlDB;

    String joinName, joinID, joinPW, getDate;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join);

        today_list_back =findViewById(R.id.today_list_back);
        btnJoin = (Button) findViewById(R.id.btnJoin);
        Ed_JoinName = findViewById(R.id.Ed_JoinName);
        Ed_JoinID = findViewById(R.id.Ed_JoinID);
        Ed_JoinPW = findViewById(R.id.Ed_JoinPW);


        myHelper = new MyDBHelper(this);

        today_list_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);

                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });

        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    joinName = Ed_JoinName.getText().toString();
                    joinID = Ed_JoinID.getText().toString();
                    joinPW = Ed_JoinPW.getText().toString();

                    if(!(joinName.equals("") || joinID.equals("") || joinPW.equals(""))) {

                        sqlDB = myHelper.getWritableDatabase();

                        // 현재 시간 가져오기
                        long now = System.currentTimeMillis();
                        // date 형식으로 바꾸기
                        Date date = new Date(now);
                        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
                        getDate = simpleDate.format(date);
                        String sql = "INSERT INTO userTBL VALUES ('" + joinName + "', '" + joinID + "', '" + joinPW + "','" + getDate + "');";
                        Log.d("myapp", sql);
                        sqlDB.execSQL(sql);
                        sqlDB.close();

                        SharedPreferences list_background = getSharedPreferences("change_color", MODE_PRIVATE);
                        SharedPreferences.Editor editor2 = list_background.edit();
                        editor2.putString("color", "#FFFFFF");
                        editor2.putInt("setColor", 0);
                        editor2.commit();

                        Intent intent = new Intent(getApplicationContext(), Login.class);
                        startActivity(intent);

                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

                        Toast.makeText(getApplicationContext(), "가입되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "필수사항 값을 입력해주세요", Toast.LENGTH_SHORT).show();
                    }

                }catch(Exception e){
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "가입에 실패하였습니다.", Toast.LENGTH_SHORT).show();
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

