package com.example.professor.testbrodcastreciver.receivers;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.example.professor.testbrodcastreciver.R;
import com.example.professor.testbrodcastreciver.activities.StartActivity;
import com.example.professor.testbrodcastreciver.database.SaveLoadReminders;
import com.example.professor.testbrodcastreciver.models.Reminder;
import com.example.professor.testbrodcastreciver.services.StartService;
import com.example.professor.testbrodcastreciver.util.ProgectConstans;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executor;

public class Receiver extends BroadcastReceiver {
    public static final String TAG = Receiver.class.getSimpleName();
    private NotificationManager manager;
    private List<Reminder> reminders;
    private Intent startBrodcastIntent;
    private PendingIntent pIntent1;
    private Context con;
    private NotificationManager notificationManager;
    private Calendar cal;
    private Date today;
    private Ringtone r;
    private MediaPlayer player;
    @Override
    public void onReceive(Context context, Intent intent) {
        con = context;
        cal = Calendar.getInstance();
        today = cal.getTime();
        String date = new SimpleDateFormat(ProgectConstans.DATE_FORMAT).format(today);
        reminders = SaveLoadReminders.searchBy(date, ProgectConstans.TEST_STRING);

        Log.d(TAG, "onReceive: "+intent.getIntExtra("hello",0));
      // Log.d(TAG, "onReceive: "+intent.getStringExtra("25"));
        context.stopService(new Intent(context, StartService.class));
        manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
      //  manager.cancel(Integer.valueOf(intent.getIntExtra("hello",0)));
        sendNotif(intent.getIntExtra("test",0));

    }

    @Override
    public IBinder peekService(Context myContext, Intent service) {
        Log.d(TAG, "onReceive peek");
        Log.d(TAG, "action peek = " + service.getAction());
        Log.d(TAG, "extra peek = " + service.getStringExtra("extra"));
        return super.peekService(myContext, service);
    }
    void sendNotif(int id) {
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        Log.d(TAG, "sendNotif: "+uri);
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(con);
        builder.setAutoCancel(true);
        builder.setContentTitle(con.getResources().getString(R.string.app_name));
        builder.setContentText(reminders.get(0).getReminder());
       // builder.setTicker("New Message Alert!");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setSound(uri);
       // builder.addAction(createMessage());
        builder.setPriority(1);
        builder.setDefaults(Notification.DEFAULT_VIBRATE);
        builder.setSound(uri);
        r = RingtoneManager.getRingtone(con, uri);

        Intent resultIntent = new Intent(con, StartActivity.class);
        final Timer time = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Log.d(TAG, "onCreate: set");
                r.stop();
                time.cancel();
            }
        };
        time.scheduleAtFixedRate(task,1000,5000);

        r.play();
       // Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(con);
        stackBuilder.addParentStack(StartActivity.class);

  /* Adds the Intent that starts the Activity to the top of the stack */
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        reminders.get(0).getId(), PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);
        notificationManager = (NotificationManager) con.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(id, builder.build());

    }
    private NotificationCompat.Action createMessage() {

        //  String date = new SimpleDateFormat(ProgectConstans.DATE_FORMAT).format(today);
        //  String message = BaseApplication.sPref.getString("key", "no message");
      /*  List<Reminder> reminders = SaveLoadReminders.searchBy(date,ProgectConstans.TEST_STRING);
        Log.d(TAG, "createMessage: "+date+"  "+reminders.get(0));*/
        startBrodcastIntent = new Intent(con, Receiver.class);
        pIntent1 = PendingIntent.getBroadcast(con, reminders.get(0).getId(), startBrodcastIntent, 0);
        return new NotificationCompat.Action(R.mipmap.ic_launcher, reminders.get(0).getReminder(), pIntent1);
    }
    public void stopRington(){
        if(r.isPlaying())r.stop();
    }

}
