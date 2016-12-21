package com.example.professor.testbrodcastreciver.activities;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.icu.util.ValueIterator;
import android.media.AudioManager;
import android.media.RemoteControlClient;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.util.Xml;
import android.view.View;

import com.example.professor.testbrodcastreciver.R;
import com.example.professor.testbrodcastreciver.database.HelperFactory;
import com.example.professor.testbrodcastreciver.database.SaveLoadReminders;
import com.example.professor.testbrodcastreciver.dialogs.SetReminder;
import com.example.professor.testbrodcastreciver.models.Reminder;
import com.example.professor.testbrodcastreciver.receivers.Receiver;
import com.example.professor.testbrodcastreciver.services.StartService;
import com.example.professor.testbrodcastreciver.util.ProgectConstans;
import com.example.professor.testbrodcastreciver.util.SaveMessage;
import com.example.professor.testbrodcastreciver.util.SetDateCallBack;
import com.j256.ormlite.dao.Dao;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

//http://startandroid.ru/ru/uroki/vse-uroki-spiskom/204-urok-119-pendingintent-flagi-requestcode-alarmmanager.html
//http://metanit.com/java/android/18.1.php
public class MainActivity extends AppCompatActivity implements SaveMessage {
    public static final String TAG = MainActivity.class.getSimpleName();
    private AlarmManager am;
    private SetDate setDate;
    private String remind;
    private List<Reminder> reminders;
    private final String ns = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setDate = new SetDate();
        Log.d(TAG, "onCreate: create");

        /*final Timer time = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Log.d(TAG, "onCreate: set");
                time.cancel();
            }
        };
        time.scheduleAtFixedRate(task,5000,5000);
*/
            }

    public void setDate(View v) {
        SetReminder.createDialog(this, setDate);// new SetReminderDate(setDate,this);
    }

    private void setAlarmManager(Calendar calendar) {
        am = (AlarmManager) this.getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(MainActivity.this, Receiver.class);
        intent.putExtra("test",getReminderId(calendar));
        am.set(AlarmManager.RTC, calendar.getTimeInMillis()
                , PendingIntent.getBroadcast(MainActivity.this, getReminderId(calendar)
                        , intent, 0));
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
            saveReminder(calendar);
            setAlarmManager(calendar);
        }
    }

    private void saveReminder(Calendar cal) {
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
    private int getReminderId(Calendar calendar){
        String date = new SimpleDateFormat(ProgectConstans.DATE_FORMAT).format(calendar.getTimeInMillis());
        reminders = SaveLoadReminders.searchBy(date,ProgectConstans.TEST_STRING);
        Log.d(TAG, "notificationId: "+reminders.size());
        return reminders.get(0).getId();
    }
    // Try to add media in app

}