package com.example.kosa1010.icards.dao;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.kosa1010.icards.model.Card;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

/**
 * Created by kosa1010 on 18.03.17.
 */

public class OrmLiteDatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static OrmLiteDatabaseHelper instance;

    private static final String DATABASE_NAME = "iCards.db";
    private static final int DATABASE_VERSION = 1;

    private RuntimeExceptionDao<Card, Long> cardDao;

    private OrmLiteDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static OrmLiteDatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new OrmLiteDatabaseHelper(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, Card.class);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        recreateTables();
    }

    private void recreateTables() {
        try {
            TableUtils.dropTable(connectionSource, Card.class, true);
            TableUtils.createTableIfNotExists(connectionSource, Card.class);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    public RuntimeExceptionDao<Card, Long> getCardDao() {
        if (cardDao == null) {
            cardDao = getRuntimeExceptionDao(Card.class);
        }
        return cardDao;
    }
}
