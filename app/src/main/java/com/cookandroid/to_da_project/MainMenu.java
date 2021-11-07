
package com.cookandroid.to_da_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainMenu extends AppCompatActivity {

    ImageView imgView_setting;
    LinearLayout Linear_diary, Linear_calendar ,Linear_todoList;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu_view);

        imgView_setting = (ImageView)findViewById(R.id.imgView_setting);
        Linear_diary = (LinearLayout)findViewById(R.id.Linear_diary);
        Linear_calendar = (LinearLayout)findViewById(R.id.Linear_calendar);
        Linear_todoList = (LinearLayout)findViewById(R.id.Linear_todoList);

        imgView_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = getIntent();
                Bundle bundle = intent1.getExtras();
                String Nicname = bundle.getString("Nicname");
                String UserId = bundle.getString("UserId");
                String UserPw = bundle.getString("UserPw");

                Intent intent2 = new Intent(getApplicationContext(), Setting.class);
                intent2.putExtra("Nicname",Nicname);
                intent2.putExtra("UserId",UserId);
                intent2.putExtra("UserPw",UserPw);
                startActivity(intent2);

                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });

        Linear_diary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = getIntent();
                Bundle bundle = intent1.getExtras();
                String Nicname = bundle.getString("Nicname");
                String UserId = bundle.getString("UserId");
                String UserPw = bundle.getString("UserPw");

                Intent intent2 = new Intent(getApplicationContext(), TodayQuestion.class);
                intent2.putExtra("Nicname",Nicname);
                startActivity(intent2);

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
                // Intent intent = new Intent(getApplicationContext(), TodoList.class);
                 Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                startActivity(intent);

                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });
    }
}
