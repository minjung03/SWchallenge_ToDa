package com.cookandroid.to_da_project;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class TodayQuestion extends AppCompatActivity {

    Button btnBackMenu, btnSumit;
    TextView TextView_Nickname;

    User user;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView(R.layout.today_question);

        btnBackMenu = findViewById(R.id.btnBackMenu);
        btnSumit = findViewById(R.id.btnSumit);
        TextView_Nickname = findViewById(R.id.TextView_Nickname);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String Nicname = bundle.getString("Nicname");

        TextView_Nickname.setText(Nicname);

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

                Intent intent = new Intent(getApplicationContext(), TodayList.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);

            }
        });


    }
}