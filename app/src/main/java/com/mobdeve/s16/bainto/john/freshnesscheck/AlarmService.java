package com.mobdeve.s16.bainto.john.freshnesscheck;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class AlarmService extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String itemName = intent.getStringExtra(Alarm.ALARM_ITEM_NAME);
        int alarmId = intent.getIntExtra(Alarm.ALARM_ID, 0);

        Intent notificationIntent = new Intent(this, MainActivity.class);
        notificationIntent.putExtra(Alarm.NOTIF_FLAG, true);
        notificationIntent.putExtra(Alarm.ALARM_ID, alarmId);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        Log.d("AlarmService", "Get Notif " + itemName + " " + alarmId);

        Notification notification = new NotificationCompat.Builder(this, App.CHANNEL_ID)
                .setContentTitle("Expiration Notice")
                .setContentText(itemName + " is expired.")
                .setSmallIcon(R.drawable.checklist)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(alarmId, notification);

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}