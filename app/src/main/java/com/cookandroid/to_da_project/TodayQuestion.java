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

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TodayQuestion extends AppCompatActivity {

    Button btnBackMenu, btnSumit;
    TextView TextView_Nickname;
    EditText Ed_Diary;

    MyDBHelper myHelper;
    SQLiteDatabase sqlDB;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView(R.layout.today_question);

        btnBackMenu = findViewById(R.id.btnBackMenu);
        btnSumit = findViewById(R.id.btnSumit);
        TextView_Nickname = findViewById(R.id.TextView_Nickname);
        Ed_Diary = findViewById(R.id.Ed_Diary);

        myHelper = new MyDBHelper(this);
        // user_info 에 저장한 값으로 바꾸기
/*        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String Nicname = bundle.getString("Nicname");
        TextView_Nickname.setText(Nicname);*/

        // 현재 시간 가져오기
        long now = System.currentTimeMillis();
        // date 형식으로 바꾸기
        Date date = new Date(now);
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
        String getDate = simpleDate.format(date);

        btnBackMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), MainMenu.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });

        btnSumit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    String Diary_value = Ed_Diary.getText().toString();
                    sqlDB = myHelper.getWritableDatabase();
                    String sql = "INSERT INTO diaryTBL VALUES ('" + getDate + "', '" + Diary_value + "');";
                    sqlDB.execSQL(sql);
                    sqlDB.close();

                    Toast.makeText(getApplicationContext(), "저장되었습니다.",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), TodayList.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fadein, R.anim.fadeout);

                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "저장에 실패하였습니다.", Toast.LENGTH_SHORT).show();
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