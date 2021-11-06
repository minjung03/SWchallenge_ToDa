package com.cookandroid.to_da_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Setting extends AppCompatActivity {

    Button btn_setting_Background, btn_setting_NameChange, btn_setting_IdPassChange, btn_setting_back;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);

        btn_setting_Background = (Button) findViewById(R.id.btn_setting_Background);
        btn_setting_NameChange = (Button) findViewById(R.id.btn_setting_NameChange);
        btn_setting_IdPassChange = (Button)findViewById(R.id.btn_setting_IdPassChange);
        btn_setting_back = (Button)findViewById(R.id.btn_setting_back);

        btn_setting_Background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SettingBackground.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });

        btn_setting_NameChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = getIntent();
                Bundle bundle = intent1.getExtras();
                String Nicname = bundle.getString("Nicname");
                String UserId = bundle.getString("UserId");
                String UserPw = bundle.getString("UserPw");

                Intent intent2 = new Intent(getApplicationContext(), SettingNicknameChange.class);
                intent2.putExtra("Nicname",Nicname);
                intent2.putExtra("UserId",UserId);
                startActivity(intent2);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });

        btn_setting_IdPassChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = getIntent();
                Bundle bundle = intent1.getExtras();
                String Nicname = bundle.getString("Nicname");
                String UserId = bundle.getString("UserId");
                String UserPw = bundle.getString("UserPw");

                Intent intent2 = new Intent(getApplicationContext(), SettingidpassCheck.class);
                intent2.putExtra("UserId",UserId);
                intent2.putExtra("UserPw",UserPw);

                startActivity(intent2);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });

        btn_setting_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainMenu.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });


    }
}
