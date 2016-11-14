package com.example.professor.testbrodcastreciver.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;

import com.example.professor.testbrodcastreciver.R;
import com.example.professor.testbrodcastreciver.database.HelperFactory;
import com.example.professor.testbrodcastreciver.database.SaveLoadReminders;
import com.example.professor.testbrodcastreciver.dialogs.SetReminder;
import com.example.professor.testbrodcastreciver.models.Reminder;
import com.example.professor.testbrodcastreciver.services.StartService;
import com.example.professor.testbrodcastreciver.util.ProgectConstans;
import com.example.professor.testbrodcastreciver.util.SaveMessage;
import com.example.professor.testbrodcastreciver.util.SetDateCallBack;
import com.j256.ormlite.dao.Dao;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

//http://startandroid.ru/ru/uroki/vse-uroki-spiskom/204-urok-119-pendingintent-flagi-requestcode-alarmmanager.html
//http://metanit.com/java/android/18.1.php
public class MainActivity extends AppCompatActivity implements SaveMessage {
    public static final String TAG = MainActivity.class.getSimpleName();
    private AlarmManager am;
    private SetDate setDate;
    private String remind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setDate = new SetDate();
    }

    public void onClick1(View view) {

    }

    public void onClick2(View view) {

    }

    public void setDate(View v) {
        SetReminder.createDialog(this, setDate);// new SetReminderDate(setDate,this);
    }

    private void setAlarmManager(Calendar calendar) {
        am = (AlarmManager) this.getSystemService(ALARM_SERVICE);
        am.set(AlarmManager.RTC, calendar.getTimeInMillis()
                , PendingIntent.getService(MainActivity.this, 0, new Intent(MainActivity.this, StartService.class), 0));
    }

    private void setRemind(String message) {          //test method
       // BaseApplication.sPref.edit().putString("key", message).commit();
       remind = message;
    }

    @Override
    public void saveMessage(String message) {
        setRemind(message);
    }

      private class SetDate implements SetDateCallBack {
        @Override
        public void dateSetted(Calendar calendar) {
            setAlarmManager(calendar);
            saveReminder(calendar);

        }
    }
    private void saveReminder(Calendar cal){
        Reminder reminder = new Reminder();
        Date today = cal.getTime();
        String date = new SimpleDateFormat(ProgectConstans.DATE_FORMAT).format(today);
        reminder.setTime(date);
        reminder.setReminder(remind);
        reminder.setCategory(ProgectConstans.TEST_STRING);
        reminder.setPriority(1);
        SaveLoadReminders.saveReminder(reminder);

    }

    @Override
    protected void onDestroy() {
        HelperFactory.releaseHelper();
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }
}
