
package com.cookandroid.to_da_project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ResourceBundle;

public class TodoList extends AppCompatActivity {
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView(R.layout.todolist_list);

        Button ListAddButton = (Button) findViewById(R.id.btn_ListAdd);

        SharedPreferences preferences = getSharedPreferences("change_color", MODE_PRIVATE);
        String n = preferences.getString("color1", "#FFDEFA");

        LinearLayout TodolistLayout = findViewById(R.id.Todolist_list);
        TodolistLayout.setBackgroundColor(Color.parseColor(n));

        ListAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TodoListAdd.class);
                startActivity(intent);

                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });
    }
}
