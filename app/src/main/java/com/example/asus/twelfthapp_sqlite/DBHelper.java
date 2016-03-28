package com.example.asus.twelfthapp_sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        // конструктор суперкласса
        super(context, "myDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("LOG_TAG,", "--- onCreate database ---");
        // создаем таблицу с полями
//        db.execSQL(" create table mytable ("
//                + "id integer primary key autoincrement,"
//                + "name text,"
//                + "email text,"
//                + "login text"
//                + ");");

        db.execSQL("create table mytable ("
                + "id integer primary key autoincrement,"
                + "name text,"
                + "last text,"
                + "email text," // added a ','
                + "pass,"
                + "login" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
