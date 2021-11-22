package com.cookandroid.to_da_project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SettingNicknameChange extends AppCompatActivity {

    ImageView today_list_back;
    Button btn_nickname_change;
    EditText ED_nickname_Change;

    MyDBHelper myHelper;
    SQLiteDatabase sqlDB;

    String new_nicname;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_nickname_change);

        today_list_back = findViewById(R.id.today_list_back);
        btn_nickname_change = (Button)findViewById(R.id.btn_nickname_change);
        ED_nickname_Change = (EditText) findViewById(R.id.ED_nickname_Change);

        myHelper = new MyDBHelper(this);

        today_list_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Setting.class);
                startActivity(intent);

                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });

        btn_nickname_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    SharedPreferences test = getSharedPreferences("user_info", MODE_PRIVATE);
                    String user_id = test.getString("user_id", "null");
                    new_nicname = ED_nickname_Change.getText().toString();

                    sqlDB = myHelper.getWritableDatabase();
                    String sql = "UPDATE userTBL SET nicname = '"+new_nicname+"' WHERE userid = '"+user_id+"'";
                    sqlDB.execSQL(sql);
                    sqlDB.close();

                    SharedPreferences.Editor editor = test.edit();
                    editor.remove("user_name");

                    editor.putString("user_name", new_nicname);
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
