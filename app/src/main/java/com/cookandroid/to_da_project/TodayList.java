package com.cookandroid.to_da_project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.SimpleDateFormat;
import java.util.Date;


public class TodayList extends AppCompatActivity {

    Button btn_today_Back;
    Calendar_OndDayDecorator oneDayDecorator;
    MaterialCalendarView calendarView;
    TextView Text_diary, TextView_Question, Text_Date;
    ImageView img_editIcon;
    ImageView today_list_back;

    DiaryDBHelper diaryDBHelper;
    SQLiteDatabase sqlDB;

    String diary_date, diary_value, diary_id, diary_question, diary_userid;
    String day_select, getDate;

    int Date_equals_cnt;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView(R.layout.today_list);

        Text_diary = findViewById(R.id.Text_diary);
        TextView_Question = findViewById(R.id.TextView_Question);
        img_editIcon = findViewById(R.id.img_editIcon);
        today_list_back = findViewById(R.id.today_list_back);

        img_editIcon.setVisibility(View.INVISIBLE);

        long now = System.currentTimeMillis();
        // date 형식으로 바꾸기
        Date date = new Date(now);
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
        getDate = simpleDate.format(date);

        Text_Date = findViewById(R.id.Text_Date);
        Text_Date.setText(getDate);

        today_list_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainMenu.class);
                startActivity(intent);

                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });

        calendarView = findViewById(R.id.calendarView);
        oneDayDecorator = new Calendar_OndDayDecorator();

        diaryDBHelper = new DiaryDBHelper(this);

        SharedPreferences test = getSharedPreferences("user_info", MODE_PRIVATE);
        String user_id = test.getString("user_id", "null");

        // 주말 색 바꾸기
        calendarView.addDecorators(
                new Calendar_SaturdayDecorator(),
                new Calendar_SundayDecorator()
        );

        // 날짜를 누르면 색 변하게 하는
        calendarView.addDecorators(
                new Calendar_MySelectDecorator(this)
        );

        // 오늘 날짜 표시
        calendarView.addDecorators(
                oneDayDecorator
        );

        sqlDB = diaryDBHelper.getWritableDatabase();
        Cursor cursor = sqlDB.rawQuery("SELECT * FROM " + "diaryTBL", null);
        int count = cursor.getCount();

        outer : for (int i = 1; i <= count; i++) {
            cursor.moveToNext(); // 다음 행으로
            diary_id = cursor.getString(cursor.getColumnIndex("userid"));
            diary_date = cursor.getString(cursor.getColumnIndex("date"));
            diary_value = cursor.getString(cursor.getColumnIndex("diary"));
            diary_question = cursor.getString(cursor.getColumnIndex("question"));

            if (diary_date.equals(getDate) && diary_id.equals(user_id)) {
                TextView_Question.setText(diary_question);
                Text_diary.setText(diary_value);
                break outer;
            }else {
                Text_diary.setText("저장된 일기가 없습니다");
                TextView_Question.setText("저장된 일기가 없습니다");
            }
        }
        img_editIcon.setVisibility(View.VISIBLE);
        cursor.close();
        sqlDB.close();

        // 일기 수정
        img_editIcon.setOnClickListener(new View.OnClickListener() {
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

        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                try {
                    // 사용자가 누른 날짜
                    int Year = date.getYear();
                    int Month = date.getMonth() + 1;
                    int Day = date.getDay();
                    if(Day < 10)day_select = Year + "-" + Month + "-0" + Day;
                    else day_select = Year + "-" + Month + "-" + Day;

                    Text_Date.setText(day_select);

                    if(getDate.equals(day_select)){
                        img_editIcon.setVisibility(View.VISIBLE);
                    }
                    else img_editIcon.setVisibility(View.INVISIBLE);



                    sqlDB = diaryDBHelper.getWritableDatabase();
                    Cursor cursor = sqlDB.rawQuery("SELECT * FROM " + "diaryTBL", null);
                    int count = cursor.getCount();

                    /*outer : for (int i = 1; i <= count; i++) {
                        cursor.moveToNext(); // 다음 행으로
                        diary_id = cursor.getString(cursor.getColumnIndex("userid"));
                        diary_date = cursor.getString(cursor.getColumnIndex("date"));
                        diary_value = cursor.getString(cursor.getColumnIndex("diary"));

                        if (diary_date.equals(day_select) && diary_id.equals(user_id)) {
                            Text_diary.setText(diary_value);
                            break outer;
                        }else {
                            Text_diary.setText("저장된 일기가 없습니다");
                        }
                    }*/
                    outer : for (int i = 1; i <= count; i++) {
                        cursor.moveToNext(); // 다음 행으로
                        Toast.makeText(getApplicationContext(), cursor.getString(cursor.getColumnIndex("date")), Toast.LENGTH_SHORT).show();
                        diary_id = cursor.getString(cursor.getColumnIndex("userid"));
                        diary_date = cursor.getString(cursor.getColumnIndex("date"));
                        diary_value = cursor.getString(cursor.getColumnIndex("diary"));
                        diary_question = cursor.getString(cursor.getColumnIndex("question"));

                        if (diary_date.equals(day_select) && diary_id.equals(user_id)) {
                            Text_diary.setText(diary_value);
                            TextView_Question.setText(diary_question);
                            break outer;
                        }else {
                            Text_diary.setText("저장된 일기가 없습니다");
                            TextView_Question.setText("저장된 일기가 없습니다.");
                        }
                    }

                    cursor.close();
                    sqlDB.close();

                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "일기 출력이 실패하였습니다", Toast.LENGTH_SHORT).show();
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