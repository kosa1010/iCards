package com.example.kosa1010.icards.repository;

import android.content.Context;

import com.example.kosa1010.icards.dao.OrmLiteDatabaseHelper;
import com.example.kosa1010.icards.model.Card;

import java.util.List;

/**
 * Created by kosa1010 on 18.03.17.
 */

public class OrmCardsRepository {

    public static List<Card> findAll(Context context) {
        OrmLiteDatabaseHelper databaseHelper = OrmLiteDatabaseHelper.getInstance(context);
        return databaseHelper.getCardDao().queryForAll();
    }

    public static Card findById(Context context, long cardId) {
        OrmLiteDatabaseHelper databaseHelper = OrmLiteDatabaseHelper.getInstance(context);
        return databaseHelper.getCardDao().queryForId(cardId);
    }

    public static void addCard(Context context, Card card) {
        OrmLiteDatabaseHelper databaseHelper = OrmLiteDatabaseHelper.getInstance(context);
        databaseHelper.getCardDao().create(card);
    }

    public static void updateCard(Context context, Card card) {
        OrmLiteDatabaseHelper databaseHelper = OrmLiteDatabaseHelper.getInstance(context);
        databaseHelper.getCardDao().update(card);
    }

    public static void deleteCard(Context context, Card card) {
        OrmLiteDatabaseHelper databaseHelper = OrmLiteDatabaseHelper.getInstance(context);
        databaseHelper.getCardDao().delete(card);
    }

}