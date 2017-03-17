package com.example.kosa1010.icards.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by kosa1010 on 15.03.17.
 */

public class MyDataBaseHelper extends SQLiteOpenHelper {

    private static MyDataBaseHelper instance;

    private static final String DATABASE_NAME = "bazaICards";
    private static final int VERSION = 1;

    public static MyDataBaseHelper getInstance(Context context) {
        if (instance == null)
            instance = new MyDataBaseHelper(context, DATABASE_NAME, null, VERSION);
        return instance;
    }


    private MyDataBaseHelper(Context context,
                             String name,
                             SQLiteDatabase.CursorFactory factory,
                             int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("database", "onCreate start");
        recreateDB(db);
        Log.d("database", "onCreate finished");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("database", "onUpgrade start");
        recreateDB(db);
        Log.d("database", "onUpgrade finished");
    }

    private void recreateDB(SQLiteDatabase db) {
        db.execSQL("drop table if exists cards");
        db.execSQL("create table cards (" +
                "id integer primary key autoincrement not null, " +
                "name TEXT, " +
                "code TEXT" +
                "type TEXT" +
                "login TEXT" +
                "pass TEXT" +
                ")");
    }
}
