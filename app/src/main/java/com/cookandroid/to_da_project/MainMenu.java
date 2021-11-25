
package com.cookandroid.to_da_project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainMenu extends AppCompatActivity {

    ImageView imgView_setting;
    LinearLayout Linear_diary, Linear_calendar ,Linear_todoList;

    DiaryDBHelper diaryDBHelper;
    SQLiteDatabase sqlDB;
    SharedPreferences preferences;
    String getDate, diary_date, diary_userid;
    int Date_equals_cnt = 0;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu_view);

        imgView_setting = (ImageView)findViewById(R.id.imgView_setting);
        Linear_diary = (LinearLayout)findViewById(R.id.Linear_diary);
        Linear_calendar = (LinearLayout)findViewById(R.id.Linear_calendar);
        Linear_todoList = (LinearLayout)findViewById(R.id.Linear_todoList);

        diaryDBHelper = new DiaryDBHelper(this);
        preferences = getSharedPreferences("user_info", MODE_PRIVATE);
        String user_id = preferences.getString("user_id", "null");

        imgView_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getApplicationContext(), Setting.class);
                startActivity(intent2);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });

        Linear_diary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                long now = System.currentTimeMillis();
                // date 형식으로 바꾸기
                Date date = new Date(now);
                SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
                getDate = simpleDate.format(date);

                sqlDB = diaryDBHelper.getWritableDatabase();
                Cursor cursor = sqlDB.rawQuery("SELECT userid, date FROM " + "diaryTBL", null);
                int count = cursor.getCount();

                outer : for (int i = 1; i <= count; i++) {
                    cursor.moveToNext(); // 다음 행으로

                    diary_userid = cursor.getString(cursor.getColumnIndex("userid"));
                    diary_date = cursor.getString(cursor.getColumnIndex("date"));

                    if (diary_date.equals(getDate) && diary_userid.equals(user_id)) {
                        Date_equals_cnt = 1;
                        break outer;
                    }else {
                        Date_equals_cnt = 0;
                    }
                }
                cursor.close();
                sqlDB.close();

                Intent intent;
                if(Date_equals_cnt==1) {
                    intent = new Intent(getApplicationContext(), TodayQuestionCrystal.class);
                }
                else {
                    intent = new Intent(getApplicationContext(), TodayQuestion.class);
                }
                startActivity(intent);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);

            }
        });

        Linear_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TodayList.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });

        Linear_todoList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TodoList.class);
                //  Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                startActivity(intent);

                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
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
