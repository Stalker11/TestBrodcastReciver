package com.example.professor.testbrodcastreciver.util;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TimePicker;

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
        Log.d(TAG, "onTimeSet: "+cal.get(Calendar.DAY_OF_MONTH)+"-"+cal.get(Calendar.MONTH));
        new DatePickerDialog(cont, d,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    // отображаем диалоговое окно для выбора времени
    private void setTime() {
        new TimePickerDialog(cont, t,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE), true).show();

    }

    // установка обработчика выбора времени
   private TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
            cal.set(Calendar.MINUTE, minute);
            Log.d(TAG, "onTimeSet: "+cal.get(Calendar.HOUR_OF_DAY)+"-"+cal.get(Calendar.MINUTE));
            callback.dateSetted(cal);
        }
    };

    // установка обработчика выбора даты
   private DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, monthOfYear);
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            Log.d(TAG, "onTimeSet: "+cal.get(Calendar.DAY_OF_MONTH)+"-"+cal.get(Calendar.MONTH));
            setTime();
        }
    };

}
