package com.example.professor.testbrodcastreciver.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.example.professor.testbrodcastreciver.R;
import com.example.professor.testbrodcastreciver.activities.StartActivity;
import com.example.professor.testbrodcastreciver.database.SaveLoadReminders;
import com.example.professor.testbrodcastreciver.models.Reminder;
import com.example.professor.testbrodcastreciver.receivers.Receiver;
import com.example.professor.testbrodcastreciver.util.ProgectConstans;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class StartService extends Service {
    public static final String TAG = StartService.class.getSimpleName();
    private Intent startBrodcastIntent;
    private PendingIntent pIntent1;
    private NotificationManager notificationManager;
    private Calendar cal;
    private Date today;
    private List<Reminder> reminders;
    private Ringtone r;
    private  Uri uri;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: ");
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        cal = Calendar.getInstance();
        today = cal.getTime();
        setReminders();
        startBrodcastIntent = new Intent(this, Receiver.class);
        uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
       // startBrodcastIntent.setAction(Intent.ACTION_SEND);
        startBrodcastIntent.putExtra("hello",reminders.get(0).getId()); // если PendingIntent имеет одинаковый флаг, то интент изменен не будетhttp://elenergi.ru/
        Log.d(TAG, "onCreate: "+10010);

        pIntent1 = PendingIntent.getBroadcast(this, reminders.get(0).getId(), startBrodcastIntent, 0);

        Log.d(TAG, "onClick1: " + 100);
        sendNotif(reminders.get(0).getId());
        Log.d(TAG, "onCreate: ");

    }
//https://www.youtube.com/watch?v=ocm8sg8eTpM
    void sendNotif(int id) {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this);
        builder.setAutoCancel(true);
        builder.setContentTitle("New Message");
        builder.setContentText("Hello Oleg");
        builder.setTicker("New Message Alert!");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.addAction(createMessage());
        builder.setPriority(1);
        Intent resultIntent = new Intent(this, StartActivity.class);
        Notification notif = builder.build();
        notif.defaults = Notification.DEFAULT_SOUND;
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(StartActivity.class);
        r = RingtoneManager.getRingtone(getBaseContext(), uri);
        r.play();
  /* Adds the Intent that starts the Activity to the top of the stack */
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        reminders.get(0).getId(), PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(id, builder.build());

    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
        r.stop();
    }

    private NotificationCompat.Action createMessage() {

      //  String date = new SimpleDateFormat(ProgectConstans.DATE_FORMAT).format(today);
      //  String message = BaseApplication.sPref.getString("key", "no message");
      /*  List<Reminder> reminders = SaveLoadReminders.searchBy(date,ProgectConstans.TEST_STRING);
        Log.d(TAG, "createMessage: "+date+"  "+reminders.get(0));*/
        return new NotificationCompat.Action(R.mipmap.ic_launcher, reminders.get(0).getReminder(), pIntent1);
    }
    private void setReminders(){
        String date = new SimpleDateFormat(ProgectConstans.DATE_FORMAT).format(today);
        reminders = SaveLoadReminders.searchBy(date,ProgectConstans.TEST_STRING);
        Log.d(TAG, "notificationId: "+reminders.size());

    }
}
