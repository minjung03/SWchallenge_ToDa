package com.cookandroid.to_da_project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.Calendar;

public class TodayList extends AppCompatActivity {

    Button btn_today_Back;
    Calendar_OndDayDecorator oneDayDecorator;
    MaterialCalendarView calendarView;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView(R.layout.today_list);

        btn_today_Back = (Button)findViewById(R.id.btn_today_Back);
        TextView textView = findViewById(R.id.TextView);
        calendarView = findViewById(R.id.calendarView);
        oneDayDecorator = new Calendar_OndDayDecorator();

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

       /* calendarView.setOnDateChangedListener(new MaterialCalendarView.OnDateChangeListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                textView.setText(String.format("%d년 %d월 %d일", year, month, dayOfMonth));
            }
        });*/
        /*calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

              //  textView.setText(date);
            }

        

        });
        CalendarDay date = CalendarDay.today();*/
        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                int Year = date.getYear();
                int Month = date.getMonth() + 1;
                int Day = date.getDay();

                /*
                Log.i("Year test", Year + "");
                Log.i("Month test", Month + "");
                Log.i("Day test", Day + "");
                Log.i("shot_Day test", shot_Day + "");
                */

                String shot_Day = Year + "년 " + Month + "월 " + Day + "일";
                //calendarView.clearSelection();
                textView.setText(shot_Day);
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