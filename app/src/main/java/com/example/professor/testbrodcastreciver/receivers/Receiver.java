package com.example.professor.testbrodcastreciver.receivers;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.example.professor.testbrodcastreciver.models.Reminder;
import com.example.professor.testbrodcastreciver.services.StartService;

import java.util.ArrayList;

public class Receiver extends BroadcastReceiver {
    public static final String TAG = Receiver.class.getSimpleName();
    private NotificationManager manager;
    private ArrayList<Reminder> reminders;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive"+intent.getParcelableArrayListExtra("test"));
        reminders = intent.getParcelableArrayListExtra("test");
      //  Log.d(TAG, "onReceive = " + intent.getAction());
//        Log.d(TAG, "onReceive " + reminders.get(0).getId());
        Log.d(TAG, "onReceive: "+intent.getIntExtra("hello",0));
      // Log.d(TAG, "onReceive: "+intent.getStringExtra("25"));
        context.stopService(new Intent(context, StartService.class));
        manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancel(Integer.valueOf(intent.getIntExtra("hello",0)));

    }

    @Override
    public IBinder peekService(Context myContext, Intent service) {
        Log.d(TAG, "onReceive peek");
        Log.d(TAG, "action peek = " + service.getAction());
        Log.d(TAG, "extra peek = " + service.getStringExtra("extra"));
        return super.peekService(myContext, service);
    }

}
