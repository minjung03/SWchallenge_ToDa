package com.cookandroid.to_da_project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SettingBackground extends AppCompatActivity {

    Button btnPink, btnBlue, btnYellow, btnGreen, btnWhite;
    Button btn_Setting_back;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_background_color);

        btnPink = (Button)findViewById(R.id.btnPink);
        btnBlue = (Button)findViewById(R.id.btnBlue);
        btnYellow = (Button)findViewById(R.id.btnYellow);
        btnGreen = (Button)findViewById(R.id.btnGreen);

        SharedPreferences preferences = getSharedPreferences("change_color", MODE_PRIVATE);
        btn_Setting_back = (Button)findViewById(R.id.btn_setting_back);

        btn_Setting_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Setting.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });

        btnPink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("color", "#FCE9EB");
                editor.commit();
                Toast.makeText(getApplicationContext(), "분홍색을 선택하셨습니다", Toast.LENGTH_SHORT).show();

            }
        });

        btnBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("color", "#E0F0FB");
                editor.commit();
                Toast.makeText(getApplicationContext(), "파란색을 선택하셨습니다", Toast.LENGTH_SHORT).show();
            }
        });

        btnYellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("color", "#FEF9CD");
                editor.commit();
                Toast.makeText(getApplicationContext(), "노란색을 선택하셨습니다", Toast.LENGTH_SHORT).show();
            }
        });

        btnGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("color", "#D2E8CF");
                editor.commit();
                Toast.makeText(getApplicationContext(), "초록색을 선택하셨습니다", Toast.LENGTH_SHORT).show();
            }
        });

        btnWhite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("color", "#FFFFFF");
                editor.commit();
                Toast.makeText(getApplicationContext(), "하얀색을 선택하셨습니다", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
