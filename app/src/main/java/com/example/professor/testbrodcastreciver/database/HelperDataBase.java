package com.example.professor.testbrodcastreciver.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.professor.testbrodcastreciver.models.Reminder;
import com.example.professor.testbrodcastreciver.util.ProgectConstans;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class HelperDataBase extends OrmLiteSqliteOpenHelper {
    private Dao<Reminder, Long> reminder;
    private static final String TAG = HelperDataBase.class.getSimpleName();
    public HelperDataBase(Context context) {
        super(context, ProgectConstans.DATABASE_NAME, null, ProgectConstans.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Reminder.class);
        } catch (SQLException e) {
            Log.d(TAG, "onCreate: " + e.getMessage());
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }

    public Dao<Reminder, Long> getDao() throws SQLException {
        if (reminder == null) {
            reminder = getDao(Reminder.class);
        }
        return reminder;
    }

    @Override
    public void close() {
        super.close();
    }
}
