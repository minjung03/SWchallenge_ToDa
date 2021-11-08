
package com.cookandroid.to_da_project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.ResourceBundle;

public class TodoList extends AppCompatActivity {

    LinearLayout Todolist_Layout;
    SharedPreferences preferences;
    Button btn_ListAdd;
    Button btn_ListBack;

    ListView Listview;
    ListAdapter list_Adapter;
    ArrayList<List> listArray;
    List listitem;

    String[] list_num = {"1" , "2"};
    String[] list_value = {"안드로이드 프로젝트 하기" , "쓰레기통 치우기"};

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView(R.layout.todolist_list);

        btn_ListAdd = (Button) findViewById(R.id.btn_ListAdd);
        btn_ListBack = (Button) findViewById(R.id.btn_ListBack);
        Todolist_Layout = findViewById(R.id.Todolist_list);

        preferences = getSharedPreferences("change_color", MODE_PRIVATE);
        String n = preferences.getString("color", "#FFFFFF");
        Todolist_Layout.setBackgroundColor(Color.parseColor(n));

        Listview = findViewById(R.id.todoListView);
        listArray = new ArrayList<List>();

        for (int i = 0; i < list_num.length; i++) {
            listitem = new List(list_num[i], list_value[i]);
            listArray.add(listitem); //각 아이템을 List 클래스로 사용, Array에 add
        }
        //List 어댑터에 ArrayList로 세팅하고 ListView와 연결
        list_Adapter = new ListAdapter(this, listArray);
        Listview.setAdapter(list_Adapter);

        btn_ListAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TodoListAdd.class);
                startActivity(intent);

                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
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
}
