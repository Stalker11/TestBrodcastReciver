package com.example.professor.testbrodcastreciver.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Reminders")
public class Reminder implements Parcelable{
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String reminder;
    @DatabaseField
    private String time;
    @DatabaseField
    private String category;
    @DatabaseField
    private int priority;

    public String getReminder() {
        return reminder;
    }

    public void setReminder(String reminder) {
        this.reminder = reminder;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getId() {
        return id;
    }

    public Reminder() {
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(reminder);
        parcel.writeString(time);
        parcel.writeString(category);
        parcel.writeInt(id);
        parcel.writeInt(priority);
    }
    private Reminder(Parcel parcel){
        id = parcel.readInt();
        reminder = parcel.readString();
        time = parcel.readString();
        category = parcel.readString();
        priority = parcel.readInt();
    }
    public static final Parcelable.Creator<Reminder> CREATOR = new Parcelable.Creator<Reminder>(){
        public Reminder createFromParcel(Parcel in) {
            return new Reminder(in);
        }

        public Reminder[] newArray(int size) {
            return new Reminder[size];
        }
    };
}
