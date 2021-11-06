package com.cookandroid.to_da_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SettingidpassCheck extends AppCompatActivity {

    Button btn_CheckCancel;
    Button btn_CheckSumit;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_idpass_check);

        btn_CheckCancel = (Button)findViewById(R.id.btn_CheckCancel);
        btn_CheckSumit  = (Button)findViewById(R.id.btn_CheckSumit);
        //StackTraceElement[] a = new Throwable().getStackTrace();


        btn_CheckSumit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(this, getCallingActivity().getClassName(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), SettingidpassChange.class);
                startActivity(intent);

                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });
    }
}
