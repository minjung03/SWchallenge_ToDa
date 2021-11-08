
package com.cookandroid.to_da_project;

import android.content.Intent;
import android.content.SharedPreferences;
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
    ListAdapter adapter;

    String list_num;
    String list_value;

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

        adapter = new ListAdapter(TodoList.this);
        listView.setAdapter(adapter);

        img_listAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list_num = String.valueOf(adapter.getCount()+1);
                list_value = Ed_list.getText().toString();
                adapter.addItem(list_num, list_value);
                Ed_list.setText("");

                adapter.notifyDataSetChanged();
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
