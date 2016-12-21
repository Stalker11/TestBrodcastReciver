package com.example.professor.testbrodcastreciver.activities;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.example.professor.testbrodcastreciver.R;
import com.example.professor.testbrodcastreciver.services.StartService;

public class StartActivity extends FragmentActivity {
    public static final String TAG = StartActivity.class.getSimpleName();
    private NotificationManager notificationManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.start_activity);
        super.onCreate(savedInstanceState);
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        stopService(new Intent(getBaseContext(), StartService.class));
        final AudioManager audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        Ringtone ring = RingtoneManager.getRingtone(this, uri);
        RingtoneManager rr = new RingtoneManager(this);
        rr.stopPreviousRingtone();

        Log.d(TAG, "onCreate: "+ audioManager.isMusicActive()+"  "+ring.isPlaying());

    }
}
