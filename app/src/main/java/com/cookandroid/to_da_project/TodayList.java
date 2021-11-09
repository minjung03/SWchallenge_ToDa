package com.cookandroid.to_da_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.Calendar;

public class TodayList extends AppCompatActivity {

    Button btn_today_Back;

    MaterialCalendarView calendarView;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView(R.layout.today_list);

        btn_today_Back = (Button)findViewById(R.id.btn_today_Back);
        final TextView textView = findViewById(R.id.TextView);
        calendarView = findViewById(R.id.calendarView);
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
    }
}