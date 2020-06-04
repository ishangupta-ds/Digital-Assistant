package com.twopandas.remind.ui.tools;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

public class AlarmRestarterBroadcastReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(AlarmRestarterBroadcastReceiver.class.getSimpleName(), "Service Stops! Oooooooooooooppppssssss!!!!");

        String restart=intent.getExtras().getString("res").toString();

        if(restart=="1") {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Intent inte=new Intent(context, AlarmService.class);
                inte.putExtra("start",restart);
                context.startForegroundService(inte);
            } else {
                context.startService(new Intent(context, AlarmService.class));
            }
        }


    }


}
