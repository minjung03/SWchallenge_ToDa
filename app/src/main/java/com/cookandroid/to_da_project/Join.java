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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Join extends AppCompatActivity {

    Button btnBack, btnJoin;
    EditText Ed_JoinName, Ed_JoinID, Ed_JoinPW;

    MyDBHelper myHelper;
    SQLiteDatabase sqlDB;

    String joinName, joinID, joinPW;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join);

        btnBack = (Button) findViewById(R.id.btnBack);
        btnJoin = (Button) findViewById(R.id.btnJoin);
        Ed_JoinName = findViewById(R.id.Ed_JoinName);
        Ed_JoinID = findViewById(R.id.Ed_JoinID);
        Ed_JoinPW = findViewById(R.id.Ed_JoinPW);


        myHelper = new MyDBHelper(this);

        btnBack.setOnClickListener(new View.OnClickListener() {
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
                        String sql = "INSERT INTO userTBL VALUES ('" + joinName + "', '" + joinID + "', '" + joinPW + "');";
                        // Log.d("myapp", sql);
                        sqlDB.execSQL(sql);
                        sqlDB.close();

                        SharedPreferences test = getSharedPreferences("user_info", MODE_PRIVATE);
                        SharedPreferences.Editor editor = test.edit();
                        editor.putString("user_name", joinName);
                        editor.putString("user_id", joinID);
                        editor.putString("user_pw", joinPW);
                        editor.commit();

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
