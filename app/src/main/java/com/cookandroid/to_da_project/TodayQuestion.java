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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TodayQuestion extends AppCompatActivity {

    Button btnBackMenu, btnSumit;
    TextView TextView_Nickname, Text_Question;
    EditText Ed_Diary;
    SharedPreferences preferences;
    ImageView today_list_back;

    DiaryDBHelper diaryDBHelper;
    MyDBHelper myDBHelper;
    SQLiteDatabase sqlDB_D, sqlDB_M;

    String getDate, diary_value, user_id, myDB_date, myDB_userid, myDate;

    int cnt2;

    Date FirstDate, SecondDate;


    // 출력 테스트

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView(R.layout.today_question);

        String[] Question = {"한달 후의 자신에게 하고 싶은 말은 무엇인가요 ?", "오늘 하루는 어땠나요 ?", "당신이 사랑하는 사람 중 한 명을 소개해주세요 !",
                "오늘 가장 맛있게 먹었던 음식은 무엇인가요 ?", "주변 사람들에게 평소 하고 싶은 말이 있나요 ?", "오늘의 반성할 점은 무엇인가요 ?",
                "오늘 가장 보고 싶었던 사람은 누구였나요 ?", "오늘 하루를 한 문장으로 표현해주세요.", "오늘 하루 중 가장 기뻤던 순간은 언제인가요 ?",
                "오늘 하루를 어떤 식으로 마무리 하고 있으신가요 ?", "오늘의 목표는 무엇이였나요 ?", "오늘 가장 많이 느꼈던 감정은 무엇인가요 ?",
                "당신의 기분에 어울리는 색깔을 적어주세요. ", "이 다이어리를 열기 전 무엇을 하고 계셨나요 ?",
                "오늘의 자신에게 해줄 칭찬을 적어주세요 !", "다이어리를 닫은 뒤, 무엇을 하실껀가요 ?", "오늘의 제일 인상 깊었던 뉴스는 무엇인가요 ?",
                "오늘, 당신을 가장 행복하게 만들었던 것은 무엇인가요 ?", "오늘의 마지막을 장식할 할 일은 무엇인가요 ?", "오늘은 어디를 다녀오셨나요 ?",
                "오늘 들었던 노래들 중 마음에 드는 게 있었나요 ?", "오늘 제일 하기 싫었던 일은 무엇인가요 ?",
                "당장 순간이동을 할 수 있다면 어디로 가고 싶나요 ?", "오늘 즐긴 여가 중 제일 재밌던 것은 무엇인가요 ?", "오늘 목표는 무엇이었나요 ?",
                "오늘 제일 보람찼던 일은 무엇인가요 ?", "어제의 당신보다 바뀐 점이 무엇인가요 ?", "오늘의 옷차림은 어떤가요 ?", "30일을 채운 소감이 어떠신가요 ?"
        };

        today_list_back = findViewById(R.id.today_list_back);
        btnSumit = findViewById(R.id.btnSumit);
        TextView_Nickname = findViewById(R.id.TextView_Nickname);
        Text_Question = findViewById(R.id.Text_Question);
        Ed_Diary = findViewById(R.id.Ed_Diary);

        diaryDBHelper = new DiaryDBHelper(this);
        myDBHelper = new MyDBHelper(this);

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
        //

        sqlDB_M = myDBHelper.getWritableDatabase();
        Cursor cursor = sqlDB_M.rawQuery("SELECT userid, date FROM " + "userTBL", null);
        int count = cursor.getCount();

        for (int i = 1; i <= count; i++) {
            cursor.moveToNext(); // 다음 행으로
            myDB_userid = cursor.getString(cursor.getColumnIndex("userid"));
            myDB_date = cursor.getString(cursor.getColumnIndex("date"));
            if(myDB_userid.equals(user_id)) {
                myDate = myDB_date;
                break;
            }
        }

        cursor.close();
        sqlDB_M.close();

        try{ // String Type을 Date Type으로 캐스팅하면서 생기는 예외로 인해 여기서 예외처리 해주지 않으면 컴파일러에서 에러가 발생해서 컴파일을 할 수 없다.
            // date1, date2 두 날짜를 parse()를 통해 Date형으로 변환.
            FirstDate = simpleDate.parse(myDate);
            SecondDate = simpleDate.parse(getDate);

            // Date로 변환된 두 날짜를 계산한 뒤 그 리턴값으로 long type 변수를 초기화 하고 있다.
            // 연산결과 -950400000. long type 으로 return 된다.
            long calDate = FirstDate.getTime() - SecondDate.getTime();

            // Date.getTime() 은 해당날짜를 기준으로1970년 00:00:00 부터 몇 초가 흘렀는지를 반환해준다.
            // 이제 24*60*60*1000(각 시간값에 따른 차이점) 을 나눠주면 일수가 나온다.
            long cnt = calDate / ( 24*60*60*1000);

            cnt = Math.abs(cnt);

            cnt2 = (int)cnt;
            Text_Question.setText(Question[cnt2%30]);
        }
        catch(ParseException e)
        {
            // 예외 처리
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "저장에 실패하였습니다.", Toast.LENGTH_SHORT).show();
        }

        //test
        //cnt = 1;
        //int cnt2 = (int)cnt;
        //Text_Question.setText(Question[cnt2]);

        //Text_Question.setText(Question[0]);

        today_list_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                    if(!diary_value.equals("")){
                        String q = Question[cnt2 % 30].toString();

                        sqlDB_D = diaryDBHelper.getWritableDatabase();

                        String sql = "INSERT INTO diaryTBL VALUES ('" + user_id + "', '" + getDate + "', '" + diary_value + "', '" + q + "');";
                        sqlDB_D.execSQL(sql);
                        sqlDB_D.close();
                        Toast.makeText(getApplicationContext(), "저장되었습니다.", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(getApplicationContext(), TodayList.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "내용을 입력해주세요", Toast.LENGTH_SHORT).show();
                    }


                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "내용을 입력해주세요", Toast.LENGTH_SHORT).show();
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