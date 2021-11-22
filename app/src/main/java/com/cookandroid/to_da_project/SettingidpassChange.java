package com.cookandroid.to_da_project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SettingidpassChange extends AppCompatActivity {

    EditText Ed_new_id, Ed_new_pass;
    Button btn_idpass_change;
    ImageView today_list_back;

    MyDBHelper myHelper;
    SQLiteDatabase sqlDB;

    String new_id, new_pw;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_idpass_change);

        today_list_back = findViewById(R.id.today_list_back);
        btn_idpass_change = findViewById(R.id.btn_idpass_change);
        Ed_new_id = findViewById(R.id.Ed_new_id);
        Ed_new_pass = findViewById(R.id.Ed_new_pass);

        myHelper = new MyDBHelper(this);

        today_list_back.setOnClickListener(new View.OnClickListener() {
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
                    new_id = Ed_new_id.getText().toString();
                    new_pw = Ed_new_pass.getText().toString();

                    SharedPreferences test = getSharedPreferences("user_info", MODE_PRIVATE);
                    String user_id = test.getString("user_id", "null");

                    sqlDB = myHelper.getWritableDatabase();
                    String sql = "UPDATE userTBL SET userid = '" + new_id + "', userpw = '" + new_pw + "' WHERE userid = '"+user_id+"';";
                    sqlDB.execSQL(sql);
                    sqlDB.close();

                    SharedPreferences.Editor editor = test.edit();
                    editor.remove("user_id");
                    editor.remove("user_pw");

                    editor.putString("user_id", new_id);
                    editor.putString("user_pw", new_pw);
                    editor.commit();

                    Toast.makeText(getApplicationContext(), "변경이 완료되었습니다", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), Setting.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fadein, R.anim.fadeout);

                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "변경에 실패하였습니다.", Toast.LENGTH_SHORT).show();
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
