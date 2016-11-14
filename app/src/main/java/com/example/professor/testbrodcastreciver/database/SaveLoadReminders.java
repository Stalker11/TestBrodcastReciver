package com.example.professor.testbrodcastreciver.database;

import android.content.Context;
import android.util.Log;

import com.example.professor.testbrodcastreciver.models.Reminder;
import com.example.professor.testbrodcastreciver.util.ProgectConstans;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

public class SaveLoadReminders implements Dao.DaoObserver{
    private static final String TAG = SaveLoadReminders.class.getSimpleName();
    private static Context context;
    private static PreparedQuery<Reminder> prepared;
    private static QueryBuilder<Reminder, Long> searchBy;

    public static void setContext(Context cont) {
        context = cont;
    }
    public static void saveReminder(Reminder reminder){
        try {
            HelperFactory.getHelper().getDao().create(reminder);

        } catch (SQLException e) {
            e.printStackTrace();
            Log.d(TAG, "writeFilm: " + e.getMessage());
        }
    }
    public static List<Reminder> loadReminders(){
        List<Reminder> reminders = null;
        try {
            HelperDataBase storage = HelperFactory.getHelper();
            reminders = storage.getDao().queryForAll();
        } catch (SQLException e) {
            Log.d(TAG, "readFilm: " + e.getMessage());
        }
        return reminders;

    }
    public static void deleteReminder(Reminder reminder){
        try {
            int deleted = HelperFactory.getHelper().getDao().delete(reminder);
            Log.d(TAG, "deleteFilm: " + deleted);
            if (deleted == 1) {
            } else if (deleted == 0) {
            }
        } catch (SQLException e) {
        }
    }
    public static List<Reminder> searchBy(String date, String category){
        List<Reminder> reminders = null;
        try {
            searchBy = HelperFactory.getHelper().getDao().queryBuilder();
            searchBy.where().eq(ProgectConstans.DATABASE_DATE,date).and().eq(ProgectConstans.DATABASE_CATEGORY,category);
            prepared = searchBy.prepare();
            reminders = HelperFactory.getHelper().getDao().query(prepared);

        } catch (SQLException e) {

        }
        return reminders;
    }

    @Override
    public void onChange() {
        Log.d(TAG, "onChange: ");
    }
}
