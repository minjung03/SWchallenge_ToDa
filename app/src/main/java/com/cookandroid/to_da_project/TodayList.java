package com.cookandroid.to_da_project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;


public class TodayList extends AppCompatActivity {

    Button btn_today_Back;
    Calendar_OndDayDecorator oneDayDecorator;
    MaterialCalendarView calendarView;
    TextView Text_diary;

    DiaryDBHelper diaryDBHelper;
    SQLiteDatabase sqlDB;

    String diary_date, diary_value;
    String day_select;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView(R.layout.today_list);

        btn_today_Back = (Button)findViewById(R.id.btn_today_Back);
        Text_diary = findViewById(R.id.Text_diary);

        // 날짜 출력 테스트
        TextView textView = findViewById(R.id.TextView);

        calendarView = findViewById(R.id.calendarView);
        oneDayDecorator = new Calendar_OndDayDecorator();

        diaryDBHelper = new DiaryDBHelper(this);

        btn_today_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainMenu.class);
                startActivity(intent);

                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });

        calendarView.addDecorators(
                    new Calendar_SaturdayDecorator(),
                    new Calendar_SundayDecorator()
                );

        calendarView.addDecorators(
                new Calendar_MySelectDecorator(this)
        );

        calendarView.addDecorators(
                oneDayDecorator
        );

        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                try {
                    // 사용자가 누른 날짜
                    int Year = date.getYear();
                    int Month = date.getMonth() + 1;
                    int Day = date.getDay();
                    day_select = Year + "-" + Month + "-" + Day;

                    sqlDB = diaryDBHelper.getWritableDatabase();
                    Cursor cursor = sqlDB.rawQuery("SELECT * FROM " + "diaryTBL", null);
                    int count = cursor.getCount();

                    outer : for (int i = 1; i <= count; i++) {
                        cursor.moveToNext(); // 다음 행으로
                        diary_date = cursor.getString(cursor.getColumnIndex("date"));
                        diary_value = cursor.getString(cursor.getColumnIndex("diary"));

                        if (diary_date.equals(day_select)) {
                            Text_diary.setText(diary_value);
                            break outer;
                        }else {
                            Text_diary.setText("저장된 일기가 없습니다");
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