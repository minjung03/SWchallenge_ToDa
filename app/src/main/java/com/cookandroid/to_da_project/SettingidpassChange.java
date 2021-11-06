package com.cookandroid.to_da_project;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SettingidpassChange extends AppCompatActivity {

    EditText Ed_new_id, Ed_new_pass;
    Button btn_idpass_cancel, btn_idpass_change;

    MyDBHelper myHelper;
    SQLiteDatabase sqlDB;

    String new_id, new_pw;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_idpass_change);

        btn_idpass_cancel = findViewById(R.id.btn_idpass_cancel);
        btn_idpass_change = findViewById(R.id.btn_idpass_change);
        Ed_new_id = findViewById(R.id.Ed_new_id);
        Ed_new_pass = findViewById(R.id.Ed_new_pass);

        myHelper = new MyDBHelper(this);

        btn_idpass_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Setting.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });

        btn_idpass_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    Intent intent1 = getIntent();
                    Bundle bundle = intent1.getExtras();
                    String UserId = bundle.getString("UserId");

                    new_id = Ed_new_id.getText().toString();
                    new_pw = Ed_new_pass.getText().toString();

                    sqlDB = myHelper.getWritableDatabase();
                    String sql = "UPDATE userTBL SET userid = '" + new_id + "' , userpw = '" + new_pw + "' WHERE userid = '" + UserId + "'";
                    sqlDB.execSQL(sql);

                    Toast.makeText(getApplicationContext(), "변경 되었습니다", Toast.LENGTH_SHORT).show();

                }catch (Exception e){

                }
            }
        });
    }
}
