package com.cookandroid.to_da_project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TodayQuestionCrystal extends AppCompatActivity {

    Button btnBackMenu, btnSumit;
    TextView TextView_Nickname, Text_Question;
    EditText Ed_Diary;
    SharedPreferences preferences;

    DiaryDBHelper diaryDBHelper;
    SQLiteDatabase sqlDB_D;

    String getDate, diary_value, user_id, DDB_userid, DDB_date, DDB_value, DDB_question;

    int cnt2;



    // 출력 테스트

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView(R.layout.today_question_crystal);

        btnBackMenu = findViewById(R.id.btnBackMenu);
        btnSumit = findViewById(R.id.btnSumit);
        TextView_Nickname = findViewById(R.id.TextView_Nickname);
        Text_Question = findViewById(R.id.Text_Question);
        Ed_Diary = findViewById(R.id.Ed_Diary);

        diaryDBHelper = new DiaryDBHelper(this);

        preferences = getSharedPreferences("user_info", MODE_PRIVATE);
        String user_name = preferences.getString("user_name", "null");
        TextView_Nickname.setText(user_name);

        user_id = preferences.getString("user_id", "null");




        // 현재 시간 가져오기
        long now = System.currentTimeMillis();
        // date 형식으로 바꾸기
        Date date = new Date(now);
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
        getDate = simpleDate.format(date);

        sqlDB_D = diaryDBHelper.getWritableDatabase();
        Cursor cursor = sqlDB_D.rawQuery("SELECT userid, date, diary, question FROM " + "diaryTBL", null);
        int count = cursor.getCount();

        for (int i = 1; i <= count; i++) {
            cursor.moveToNext(); // 다음 행으로
            DDB_userid = cursor.getString(cursor.getColumnIndex("userid"));
            DDB_date = cursor.getString(cursor.getColumnIndex("date"));
            DDB_value = cursor.getString(cursor.getColumnIndex("diary"));
            DDB_question = cursor.getString(cursor.getColumnIndex("question"));
            if(DDB_userid.equals(user_id)&&DDB_date.equals(getDate)) {
                Ed_Diary.setText(DDB_value);
                Text_Question.setText(DDB_question);
                break;
            }
        }

        cursor.close();
        sqlDB_D.close();

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
                try{
                    diary_value = Ed_Diary.getText().toString();

                    sqlDB_D = diaryDBHelper.getWritableDatabase();
                    // String sql = "INSERT INTO diaryTBL VALUES ('" + getDate + "', '" + diary_value + "');";
                    String sql = "UPDATE diaryTBL SET diary = '" + diary_value + "' WHERE date = '"+ getDate +"' And userid='"+user_id+"';";
                    Toast.makeText(getApplicationContext(), "저장되었습니다.",Toast.LENGTH_LONG).show();

                    sqlDB_D.execSQL(sql);
                    sqlDB_D.close();


                    Intent intent = new Intent(getApplicationContext(), TodayList.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fadein, R.anim.fadeout);

                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "저장에 실패하였습니다.", Toast.LENGTH_SHORT).show();
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
