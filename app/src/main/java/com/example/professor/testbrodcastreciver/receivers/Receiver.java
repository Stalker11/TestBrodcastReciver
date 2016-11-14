package com.example.professor.testbrodcastreciver.receivers;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.example.professor.testbrodcastreciver.services.StartService;

public class Receiver extends BroadcastReceiver {
    public static final String TAG = Receiver.class.getSimpleName();
    private NotificationManager manager;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive");
        Log.d(TAG, "action = " + intent.getAction());
        Log.d(TAG, "onReceive " + intent.getIntExtra("close",0));
        context.stopService(new Intent(context, StartService.class));
        manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancel(intent.getIntExtra("close",0));

    }

    @Override
    public IBinder peekService(Context myContext, Intent service) {
        Log.d(TAG, "onReceive peek");
        Log.d(TAG, "action peek = " + service.getAction());
        Log.d(TAG, "extra peek = " + service.getStringExtra("extra"));
        return super.peekService(myContext, service);
    }

}
