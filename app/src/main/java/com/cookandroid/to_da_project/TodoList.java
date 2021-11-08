
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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.ResourceBundle;

public class TodoList extends AppCompatActivity {

    LinearLayout Todolist_Layout;
    SharedPreferences preferences;
    Button btn_ListAdd, btn_ListBack;
    ImageView img_listAdd;
    EditText Ed_list;

    ListView listView;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView(R.layout.todolist_list);

        btn_ListAdd = (Button) findViewById(R.id.btn_ListAdd);
        btn_ListBack = (Button) findViewById(R.id.btn_ListBack);
        Todolist_Layout = findViewById(R.id.Todolist_list);
        img_listAdd = findViewById(R.id.img_listAdd);
        Ed_list = findViewById(R.id.Ed_list);
        listView = findViewById(R.id.todoListView);

        preferences = getSharedPreferences("change_color", MODE_PRIVATE);
        String n = preferences.getString("color", "#FFFFFF");
        Todolist_Layout.setBackgroundColor(Color.parseColor(n));

        displayList();

        img_listAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertList(Ed_list.getText().toString());
                // 지우기 위한 테스트 함수
                // deleteList();
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

    void displayList(){
        //Dbhelper의 읽기모드 객체를 가져와 SQLiteDatabase에 담아 사용준비
        ListDBHelper helper = new ListDBHelper(this);
        SQLiteDatabase database = helper.getReadableDatabase();

        //Cursor라는 그릇에 목록을 담아주기
        Cursor cursor = database.rawQuery("SELECT * FROM listTBL",null);

        //리스트뷰에 목록 채워주는 도구인 adapter준비
        ListAdapter adapter = new ListAdapter();

        //목록의 개수만큼 순회하여 adapter에 있는 list배열에 add
        while(cursor.moveToNext()){
            //num 행은 가장 첫번째에 있으니 0번이 되고, name은 1번
            adapter.addItemToList(cursor.getInt(0),cursor.getString(1));
        }

        //리스트뷰의 어댑터 대상을 여태 설계한 adapter로 설정
        listView.setAdapter(adapter);
    }

    void insertList(String list_value){
        //Dbhelper의 쓰기모드 객체를 가져옴
        ListDBHelper helper = new ListDBHelper(this);
        SQLiteDatabase database = helper.getReadableDatabase();

        String qry = "INSERT INTO listTBL(list_value) VALUES('"+list_value+"')";

        database.execSQL(qry); //만들어준 쿼리문 실행

        displayList(); //리스트뷰 새로고침
    }

/* 지우기 위한 테스트 함수
    void deleteList(){
        ListDBHelper helper = new ListDBHelper(this);
        SQLiteDatabase database = helper.getReadableDatabase();
        String qry = "DELETE FROM listTBL WHERE list_num = 2";
        database.execSQL(qry); //만들어준 쿼리문 실행
        displayList(); //리스트뷰 새로고침
    }
*/
}
