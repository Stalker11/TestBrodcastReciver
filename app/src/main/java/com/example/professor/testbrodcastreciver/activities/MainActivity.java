package com.example.professor.testbrodcastreciver.activities;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.example.professor.testbrodcastreciver.R;
import com.example.professor.testbrodcastreciver.adapters.GeneralSpinnerAdapter;
import com.example.professor.testbrodcastreciver.receivers.Receiver;
import com.example.professor.testbrodcastreciver.services.StartService;

//http://startandroid.ru/ru/uroki/vse-uroki-spiskom/204-urok-119-pendingintent-flagi-requestcode-alarmmanager.html
public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();
   private NotificationManager nm;
    private AlarmManager am;
    private Intent intent1;
    private Intent intent2;
    private PendingIntent pIntent1;
    private PendingIntent pIntent2;
    private Intent resultIntent;
    private PendingIntent result;
    private NotificationManager notificationManager;
    private Spinner daySpinner;
    private Spinner monthSpinner;
    private Spinner yearSpinner;
    private Spinner hourSpinner;
    private Spinner secondsSpinner;
    private Spinner formatSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        OnItemClick click = new OnItemClick();

        daySpinner = (Spinner) findViewById(R.id.day_spinner);
        monthSpinner = (Spinner) findViewById(R.id.month_spinner);
        yearSpinner = (Spinner) findViewById(R.id.year_spinner);
        hourSpinner = (Spinner) findViewById(R.id.hour_spinner);
        secondsSpinner = (Spinner) findViewById(R.id.seconds_spinner);
        formatSpinner = (Spinner) findViewById(R.id.format_spinner);

        daySpinner.setAdapter(new GeneralSpinnerAdapter(this,(this.getResources().getStringArray(R.array.days))));
        monthSpinner.setAdapter(new GeneralSpinnerAdapter(this,(this.getResources().getStringArray(R.array.monthes))));
        yearSpinner.setAdapter(new GeneralSpinnerAdapter(this,(this.getResources().getStringArray(R.array.years))));
        hourSpinner.setAdapter(new GeneralSpinnerAdapter(this,(this.getResources().getStringArray(R.array.hours))));
        secondsSpinner.setAdapter(new GeneralSpinnerAdapter(this,(this.getResources().getStringArray(R.array.seconds))));
        formatSpinner.setAdapter(new GeneralSpinnerAdapter(this,(this.getResources().getStringArray(R.array.date_format))));
        nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        am = (AlarmManager) this.getSystemService(ALARM_SERVICE);
        notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(MainActivity.this, StartService.class);
        //result = PendingIntent.getBroadcast(this, 0, intent, 0);
        result = PendingIntent.getService(MainActivity.this, 0,intent, 0);
       // am.set(AlarmManager.RTC, System.currentTimeMillis() + 4000, result);
        am.setRepeating(AlarmManager.RTC,
                SystemClock.elapsedRealtime(),5000,result);
        daySpinner.setOnItemSelectedListener(click);
        monthSpinner.setOnItemSelectedListener(click);
        yearSpinner.setOnItemSelectedListener(click);
        hourSpinner.setOnItemSelectedListener(click);
        secondsSpinner.setOnItemSelectedListener(click);
        formatSpinner.setOnItemSelectedListener(click);
    }

    public void onClick1(View view) {

        intent1 = createIntent("action 1", "extra 1");
        pIntent1 = PendingIntent.getBroadcast(this, 0, intent1, 0);

        intent2 = createIntent("action 2", "extra 2");
        pIntent2 = PendingIntent.getBroadcast(this, 0, intent2, 0);
        Log.d(TAG, "onClick1: " + 100);
        sendNotif(1,pIntent1);
        //displayNotification(1);
        compare();
    }

    public void onClick2(View view) {

    }

    Intent createIntent(String action, String extra) {
        Intent intent = new Intent(this, Receiver.class);
        intent.setAction(action);
        intent.putExtra("extra", extra);
        return intent;
    }

    void compare() {
        Log.d(TAG, "intent1 = intent2: " + intent1.filterEquals(intent2));
        Log.d(TAG, "pIntent1 = pIntent2: " + pIntent1.equals(pIntent2));
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
        ,"Hello my friend",pIntent));
        notificationManager.notify(id, builder.build());
       /* builder.flags |= Notification.FLAG_AUTO_CANCEL;

        nm.notify(id, builder);*/
    }

    protected void displayNotification(int id) {
        Log.i("Start", "notification");

  /* Invoking the default notification service */
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this);
        mBuilder.setAutoCancel(true);

        mBuilder.setContentTitle("New Message");
        mBuilder.setContentText("You've received UnRead message.");
        mBuilder.setTicker("New Message Alert!");
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);

  /* Increase notification number every time a new notification arrives */
        // mBuilder.setNumber(++10);

  /* Creates an explicit intent for an Activity in your app */

        Intent resultIntent = new Intent(this, StartActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(StartActivity.class);

  /* Adds the Intent that starts the Activity to the top of the stack */
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);

  /* notificationID allows you to update the notification later on. */
        notificationManager.notify(id, mBuilder.build());

    }
    private class OnItemClick implements AdapterView.OnItemSelectedListener{

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            Log.d(TAG, "onItemSelected: "+adapterView.getItemAtPosition(i)+"  "+i+" "+adapterView.getLastVisiblePosition()+"  ");
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            Log.d(TAG, "onNothingSelected: "+adapterView.getClass().getSimpleName());
        }
    }
}
