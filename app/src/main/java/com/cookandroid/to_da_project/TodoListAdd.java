package com.cookandroid.to_da_project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class TodoListAdd extends AppCompatActivity {

    Button btn_ListAdd_Add;
    Button btn_Listback;
    EditText Ed_list;

    ListView Listview;
    ListAdapter list_Adapter;
    ArrayList<List> listArray;
    List listitem;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView(R.layout.todolist_add);

        btn_ListAdd_Add = (Button) findViewById(R.id.btn_ListAdd_Add);
        btn_Listback = (Button) findViewById(R.id.btn_Listback);
        Ed_list = findViewById(R.id.Ed_list);

        Listview = findViewById(R.id.todoListView);
        listArray = new ArrayList<List>();

        btn_ListAdd_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listitem = new List("3", Ed_list.getText().toString());
                listArray.add(listitem); //각 아이템을 List 클래스로 사용, Array에 add
                list_Adapter.notifyDataSetChanged();

                /*Intent intent = new Intent(getApplicationContext(), TodoList.class);
                startActivity(intent);

                overridePendingTransition(R.anim.fadein, R.anim.fadeout);*/
            }
        });

        list_Adapter = new ListAdapter(this, listArray);
        Listview.setAdapter(list_Adapter);

        btn_Listback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TodoList.class);
                startActivity(intent);

                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });
    }
}
