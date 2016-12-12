package com.example.professor.testbrodcastreciver.activities;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.example.professor.testbrodcastreciver.R;

public class StartActivity extends FragmentActivity {
    public static final String TAG = StartActivity.class.getSimpleName();
    private NotificationManager notificationManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.start_activity);
        super.onCreate(savedInstanceState);
        RingtoneManager ringMan = new RingtoneManager(this);
        ringMan.stopPreviousRingtone();
    }
}
