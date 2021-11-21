
package com.cookandroid.to_da_project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class TodoList extends AppCompatActivity {

    LinearLayout Todolist_Layout;
    SharedPreferences preferences;
    Button btn_ListBack;
    ImageView img_listAdd;
    EditText Ed_list;
    TextView tx_getID;

    ListView listView;

    String user_id;
    int percent;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView(R.layout.todolist_list);

        btn_ListBack = (Button) findViewById(R.id.btn_ListBack);
        Todolist_Layout = findViewById(R.id.Todolist_list);
        img_listAdd = findViewById(R.id.img_listAdd);
        Ed_list = findViewById(R.id.Ed_list);
        listView = findViewById(R.id.todoListView);
        tx_getID = findViewById(R.id.tx_getID);

        SharedPreferences test = getSharedPreferences("user_info", MODE_PRIVATE);
        user_id = test.getString("user_id", "null");
        tx_getID.setText(user_id);

        setListBackground();
        /*String n = preferences.getString("color", "#FFFFFF");
        Todolist_Layout.setBackgroundColor(Color.parseColor(n));*/

        displayList();

        img_listAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String list_value =  Ed_list.getText().toString();
                if(!list_value.equals("")){
                    insertList(list_value);
                    setListBackground();
                }
                else {
                    Toast.makeText(getApplicationContext(), "내용을 입력해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_ListBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainMenu.class);
                startActivity(intent);

                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });
    }

    void setListBackground(){

        String Backcolor;
        preferences = getSharedPreferences("change_color", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        int color = preferences.getInt("setColor", 0);

        ListDBHelper helper = new ListDBHelper(this);
        SQLiteDatabase database = helper.getReadableDatabase();

        Cursor cursor_listchk = database.rawQuery("SELECT list_chk FROM listTBL WHERE list_chk='true';", null);
        Cursor cursor_list = database.rawQuery("SELECT * FROM listTBL", null);
        if(cursor_list.getCount()== 0) percent = 0;
        else percent = cursor_listchk.getCount() * 100 / cursor_list.getCount() ;

        if(cursor_list.getCount() == 0 || cursor_listchk.getCount() == 0){
            editor.putString("color", "#FFFFFF");
            editor.commit();
        }
        else if(cursor_listchk.getCount() == 1){
            switch (color) {
                case 1:
                    editor.putString("color", "#FDF1F3"); editor.commit();
                    break;
                case 2:
                    editor.putString("color", "#ECF6FC"); editor.commit();
                    break;
                case 3:
                    editor.putString("color", "#FEFBE1"); editor.commit();
                    break;
                case 4:
                    editor.putString("color", "#E4F1E2"); editor.commit();
                    break;
                case 5:
                    editor.putString("color", "#FFFFFF"); editor.commit();
                    break;
            }
        }
        else if(cursor_listchk.getCount() >= 1){
            switch (color) {
                case 1: {

                    if (percent < 34) {
                        editor.putString("color", "#FDF1F3");
                    }
                    else if (percent < 67) {
                        editor.putString("color", "#FCEDEF");
                    }
                    else if (percent <= 100) {
                        editor.putString("color", "#FCE9EB");
                    }
                    editor.commit();
                    break;
                }
                case 2: {
                    if (percent < 34) {
                        editor.putString("color", "#ECF6FC");
                    }
                    else if (percent < 67) {
                        editor.putString("color", "#E6F3FB");
                    }
                    else if (percent <= 100) {
                        editor.putString("color", "#E0F0FB");
                    }
                    editor.commit();
                    break;
                }
                case 3: {
                    if (percent < 34) {
                        editor.putString("color", "#FEFBE1");
                    }
                    else if (percent < 67) {
                        editor.putString("color", "#FEFAD7");
                    }
                    else if (percent <= 100) {
                        editor.putString("color", "#FEF9CD");
                    }
                    editor.commit();
                    break;
                }
                case 4: {

                    if (percent < 34) {
                        editor.putString("color", "#E4F1E2");
                    }
                    else if (percent < 67) {
                        editor.putString("color", "#DBECD8");
                    }
                    else if (percent <= 100) {
                        editor.putString("color", "#D2E8CF");
                    }
                    editor.commit();
                    break;
                }
                case 5:{
                    editor.putString("color", "#FFFFFF");
                    editor.commit();
                    break;
                }

            }
        }
        String n = preferences.getString("color", "#FFFFFF");
        Todolist_Layout.setBackgroundColor(Color.parseColor(n));
    }

    void displayList() {
        //Dbhelper의 읽기모드 객체를 가져와 SQLiteDatabase에 담아 사용준비
        ListDBHelper helper = new ListDBHelper(this);
        SQLiteDatabase database = helper.getReadableDatabase();

        //Cursor라는 그릇에 목록을 담아주기
        Cursor cursor = database.rawQuery("SELECT userid, list_value, list_chk FROM listTBL WHERE userid='"+user_id+"';", null);

        //리스트뷰에 목록 채워주는 도구인 adapter준비
        ListAdapter adapter = new ListAdapter(this);

        //목록의 개수만큼 순회하여 adapter에 있는 list배열에 add
        while (cursor.moveToNext()) {
            adapter.addItemToList(user_id, cursor.getString(1), cursor.getString(2));
        }
        //리스트뷰의 어댑터 대상을 여태 설계한 adapter로 설정
        listView.setAdapter(adapter);
    }

    void insertList(String list_value){
        ListDBHelper helper = new ListDBHelper(this);
        SQLiteDatabase database = helper.getReadableDatabase();

        String qry = "INSERT INTO listTBL(userid ,list_value, list_chk) VALUES('"+user_id+"', '"+list_value+"', 'false')";

        database.execSQL(qry); //만들어준 쿼리문 실행
        Ed_list.setText("");

        displayList(); //리스트뷰 새로고침
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