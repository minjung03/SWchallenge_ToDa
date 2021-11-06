package com.cookandroid.to_da_project;

import android.app.Activity;
import android.widget.Toast;

public class BackDoubleClick {

    private long backKeyClickTime = 0;
    private Activity activity;

    public BackDoubleClick(Activity activity) {
        this.activity = activity;
    }

    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyClickTime + 2000) {
            backKeyClickTime = System.currentTimeMillis(); showToast();
            return;
        }
        if (System.currentTimeMillis() <= backKeyClickTime + 2000) {
            activity.moveTaskToBack(true);
            activity.finish();
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

    public void showToast() {
        Toast.makeText(activity, "뒤로 가기 버튼을 한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
    }
}

