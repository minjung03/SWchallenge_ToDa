package com.cookandroid.to_da_project;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    ListView Listview;
    ListAdapter list_Adapter;
    ArrayList<List> listArray;
    List listitem;

    String[] list_num = {"0"};
    String[] list_value = {"안드로이드 프로젝트 하기"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todolist_list);

        Listview = findViewById(R.id.todoListView);
        listArray = new ArrayList<List>();

        for (int i = 0; i < list_num.length; i++) {
            listitem = new List(list_num[i], list_value[i]);
            listArray.add(listitem); //각 아이템을 List 클래스로 사용, Array에 add
        }
        //List 어댑터에 ArrayList로 세팅하고 ListView와 연결
        list_Adapter = new ListAdapter(this, listArray);
        Listview.setAdapter(list_Adapter);

    }
}
