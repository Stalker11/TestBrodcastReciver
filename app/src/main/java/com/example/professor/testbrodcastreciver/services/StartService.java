package com.example.professor.testbrodcastreciver.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.example.professor.testbrodcastreciver.R;
import com.example.professor.testbrodcastreciver.receivers.Receiver;

public class StartService extends Service {
    public static final String TAG = StartService.class.getSimpleName();
   private Intent intent1;
   private PendingIntent pIntent1;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: ");
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        intent1 = new Intent(this, Receiver.class);
        intent1.putExtra("25", "Hello");
        intent1.putExtra("880", "Oleg");
        intent1.setAction("Hello");
        pIntent1 = PendingIntent.getBroadcast(this, 0, intent1, 0);

        Log.d(TAG, "onClick1: " + 100);
        sendNotif(1, pIntent1);
        Log.d(TAG, "onCreate: ");
    }

    void sendNotif(int id, PendingIntent pIntent) {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this);
        builder.setAutoCancel(true);
        builder.setContentTitle("New Message");
        builder.setContentText("Hello Oleg");
        builder.setTicker("New Message Alert!");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.addAction(new NotificationCompat.Action(R.mipmap.ic_launcher
                , "Hello my friend", pIntent));
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(id, builder.build());
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
    }
}
