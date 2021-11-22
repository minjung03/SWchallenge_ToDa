
package com.cookandroid.to_da_project;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ListDBHelper extends SQLiteOpenHelper {

    public ListDBHelper(Context context) {
        super(context, "listDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE listTBL(userid TEXT, list_value TEXT, list_chk TEXT, date TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS listTBL");
        onCreate(db);
    }
}
