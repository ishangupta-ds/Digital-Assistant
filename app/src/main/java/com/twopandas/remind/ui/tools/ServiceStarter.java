package com.twopandas.remind.ui.tools;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;


import static android.content.Context.MODE_PRIVATE;

public class ServiceStarter extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        SharedPreferences sharedPreferences = context.getSharedPreferences("save", MODE_PRIVATE);

        boolean check=sharedPreferences.getBoolean("value", false);

        if(check) {


                Log.i(ServiceStarter.class.getSimpleName(), "Service starts after boot!!");

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    Intent intes = new Intent(context, AlarmService.class);
                    intes.putExtra("start", "1");
                    context.startForegroundService(intes);

                }

        }

    }
}
