package com.mobdeve.s16.bainto.john.freshnesscheck;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        startAlarmService(context, intent);
    }

    private void startAlarmService(Context context, Intent intent) {
        String itemName = intent.getStringExtra(Alarm.ALARM_ITEM_NAME);
        int alarmId = intent.getIntExtra(Alarm.ALARM_ID, 0);

        Intent intentService = new Intent(context, AlarmService.class);
        intentService.putExtra(Alarm.ALARM_ITEM_NAME, itemName);
        intentService.putExtra(Alarm.ALARM_ID, alarmId);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intentService);
        } else {
            context.startService(intentService);
        }
    }
}
