package com.example.professor.testbrodcastreciver.dialogs;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.example.professor.testbrodcastreciver.util.SetDateCallBack;

import java.util.Calendar;

public class SetReminderDate {
    private Context cont;
    private SetDateCallBack callback;
    private Calendar cal = Calendar.getInstance();
    private static final String TAG = SetReminderDate.class.getSimpleName();

    public SetReminderDate(SetDateCallBack cal, Context cont) {
        this.callback = cal;
        this.cont = cont;
        setDate();
    }

    private void setDate() {
        Log.d(TAG, "onTimeSet: " + cal.get(Calendar.DAY_OF_MONTH) + "-" + cal.get(Calendar.MONTH));
        new DatePickerDialog(cont, new DateListener(),
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    private void setTime() {
        new TimePickerDialog(cont, new TimeListener(),
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE), true).show();

    }

    private class TimeListener implements TimePickerDialog.OnTimeSetListener {
        @Override
        public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
            if (timePicker.isShown()) {
                cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                cal.set(Calendar.MINUTE, minute);
                Log.d(TAG, "onTimeSet: " + cal.get(Calendar.HOUR_OF_DAY) + "-" + cal.get(Calendar.MINUTE) + " time listener");
                callback.dateSetted(cal);
            }
        }
    }

    private class DateListener implements DatePickerDialog.OnDateSetListener {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
            if (datePicker.isShown()) {
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, monthOfYear);
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                Log.d(TAG, "onTimeSet: " + cal.get(Calendar.DAY_OF_MONTH) + "-" + cal.get(Calendar.MONTH) + " date listener");
                setTime();
            }
        }
    }

}
