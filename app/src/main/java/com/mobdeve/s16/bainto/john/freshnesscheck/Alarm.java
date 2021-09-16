package com.mobdeve.s16.bainto.john.freshnesscheck;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Alarm {
    public static final String ALARM_ITEM_NAME = "ALARM_ITEM_NAME", ALARM_ID = "ALARM_ID", NOTIF_FLAG = "NOTIF_FLAG";
    private static final String TAG = "Alarm";
    private int alarmId, year, month, day;
    private String itemName;

    public Alarm(int alarmId, int year, int month, int day, String itemName) {
        this.alarmId = alarmId;
        this.year = year;
        this.month = month;
        this.day = day;
        this.itemName = itemName;
    }

    public void schedule(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(ALARM_ITEM_NAME, itemName);
        intent.putExtra(ALARM_ID, alarmId);
        Log.d(TAG, "schedule: itemName = " + itemName + ", id = "+ alarmId);

        PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(context, alarmId, intent, 0);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.YEAR, year);
        
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);


        if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
            calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
        }

        alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(),
                alarmPendingIntent
        );
    }

    public void deleteAlarm(Context context){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(context, alarmId, intent, 0);
        alarmManager.cancel(alarmPendingIntent);
    }
}