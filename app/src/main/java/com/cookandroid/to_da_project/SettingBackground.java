package com.cookandroid.to_da_project;

import android.app.Activity;
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
        btnWhite = findViewById(R.id.btnWhite);
        btn_Setting_back = (Button)findViewById(R.id.btn_setting_back);

        SharedPreferences preferences = getSharedPreferences("change_color", MODE_PRIVATE);
        int color = preferences.getInt("setColor", 0);

        switch(color){
            case 1: btnPink.setBackgroundResource(R.drawable.button1_select); break;
            case 2: btnBlue.setBackgroundResource(R.drawable.button2_select); break;
            case 3: btnYellow.setBackgroundResource(R.drawable.button3_select); break;
            case 4: btnGreen.setBackgroundResource(R.drawable.button4_select); break;
            default: btnWhite.setBackgroundResource(R.drawable.button5_select); break;
        }

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
                editor.putInt("setColor", 1);
                editor.commit();

                btnPink.setBackgroundResource(R.drawable.button1_select);
                btnBlue.setBackgroundResource(R.drawable.button2_background_color);
                btnYellow.setBackgroundResource(R.drawable.button3_background_color);
                btnGreen.setBackgroundResource(R.drawable.button4_background_color);
                btnWhite.setBackgroundResource(R.drawable.button5_background_color);

            }
        });

        btnBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("color", "#E0F0FB");
                editor.putInt("setColor", 2);
                editor.commit();
                btnPink.setBackgroundResource(R.drawable.button1_background_color);
                btnBlue.setBackgroundResource(R.drawable.button2_select);
                btnYellow.setBackgroundResource(R.drawable.button3_background_color);
                btnGreen.setBackgroundResource(R.drawable.button4_background_color);
                btnWhite.setBackgroundResource(R.drawable.button5_background_color);

            }
        });

        btnYellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("color", "#FEF9CD");
                editor.putInt("setColor", 3);
                editor.commit();

                btnPink.setBackgroundResource(R.drawable.button1_background_color);
                btnBlue.setBackgroundResource(R.drawable.button2_background_color);
                btnYellow.setBackgroundResource(R.drawable.button3_select);
                btnGreen.setBackgroundResource(R.drawable.button4_background_color);
                btnWhite.setBackgroundResource(R.drawable.button5_background_color);

            }
        });

        btnGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("color", "#D2E8CF");
                editor.putInt("setColor", 4);
                editor.commit();
                btnPink.setBackgroundResource(R.drawable.button1_background_color);
                btnBlue.setBackgroundResource(R.drawable.button2_background_color);
                btnYellow.setBackgroundResource(R.drawable.button3_background_color);
                btnGreen.setBackgroundResource(R.drawable.button4_select);
                btnWhite.setBackgroundResource(R.drawable.button5_background_color);

            }
        });

        btnWhite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("color", "#FFFFFF");
                editor.putInt("setColor", 5);
                editor.commit();
                btnPink.setBackgroundResource(R.drawable.button1_background_color);
                btnBlue.setBackgroundResource(R.drawable.button2_background_color);
                btnYellow.setBackgroundResource(R.drawable.button3_background_color);
                btnGreen.setBackgroundResource(R.drawable.button4_background_color);
                btnWhite.setBackgroundResource(R.drawable.button5_select);
            }
        });


    }
}
