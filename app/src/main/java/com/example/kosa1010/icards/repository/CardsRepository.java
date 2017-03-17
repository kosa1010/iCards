package com.example.kosa1010.icards.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.kosa1010.icards.dao.MyDataBaseHelper;
import com.example.kosa1010.icards.model.Card;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kosa1010 on 15.03.17.
 */

public class CardsRepository {

    public static List<Card> findAll(Context context) {
        MyDataBaseHelper databaseHelper = MyDataBaseHelper.getInstance(context);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from card", null);

        List<Card> cardsList = new ArrayList<>();
        while (cursor.moveToNext()) {
            Card card = new Card();
            card.setId(cursor.getInt(0));
            card.setName(cursor.getString(1));
            card.setType(cursor.getString(2));
            card.setCode(cursor.getString(3));
            card.setLogin(cursor.getString(4));
            card.setPass(cursor.getString(5));
            cardsList.add(card);
        }
        return cardsList;
    }

    public static Card findById(Context context, long userId) {
        MyDataBaseHelper databaseHelper = MyDataBaseHelper.getInstance(context);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        Cursor cursor = db.rawQuery("select * from user where id = ?", new String[]{Long.toString(userId)});
        Card card = null;
        if (cursor.moveToNext()) {
            card = new Card();
            card.setId(cursor.getInt(0));
            card.setName(cursor.getString(1));
            card.setType(cursor.getString(2));
            card.setCode(cursor.getString(3));
            card.setLogin(cursor.getString(4));
            card.setPass(cursor.getString(5));
        }
        db.close();
        return card;
    }

    public static void updateCard(Context context, Card card) {
        MyDataBaseHelper databaseHelper = MyDataBaseHelper.getInstance(context);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", card.getName());
        values.put("type", card.getType());
        values.put("code", card.getCode());
        values.put("login", card.getLogin());
        values.put("pass", card.getPass());
        int result = db.update("card", values, "id = ?", new String[]{Long.toString(card.getId())});
        Log.d("update count", "update count: " + result);
        db.close();
    }

    public static void deleteCard(Context context, Card card) {
        MyDataBaseHelper databaseHelper = MyDataBaseHelper.getInstance(context);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        int result = db.delete("card", "id = ?", new String[]{Long.toString(card.getId())});
        Log.d("delete count", "delete count: " + result);
        db.close();
    }
}