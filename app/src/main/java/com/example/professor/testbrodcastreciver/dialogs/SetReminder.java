package com.example.professor.testbrodcastreciver.dialogs;

import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.professor.testbrodcastreciver.R;
import com.example.professor.testbrodcastreciver.util.SaveMessage;
import com.example.professor.testbrodcastreciver.util.SetDateCallBack;

public class SetReminder {
    private static final String TAG = SetReminder.class.getSimpleName();
    private static EditText messageText;

    public static void createDialog(final Activity act, final SetDateCallBack setDate) {
        LayoutInflater inflater = LayoutInflater.from(act);
        final AlertDialog dialog = new AlertDialog.Builder(act).create();
        final View setReminderDialogView = inflater.inflate(R.layout.set_reminder, null);
        dialog.setView(setReminderDialogView);
        messageText = (EditText) setReminderDialogView.findViewById(R.id.enter_remind);
        setReminderDialogView.findViewById(R.id.ok_button_reminder_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String message = messageText.getText().toString();
                ((SaveMessage) act).saveMessage(message);
                new SetReminderDate(setDate, act);
                dialog.dismiss();
            }
        });
        setReminderDialogView.findViewById(R.id.cancel_button_reminder_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
