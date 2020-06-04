package com.twopandas.remind.ui.tools;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.twopandas.remind.MainActivity;
import com.twopandas.myishupanda.R;

import java.util.Calendar;


public class AlarmService extends Service {

    private PendingIntent pendingIntent;

    AlarmManager alarmManager;

    private PendingIntent pendingIntentfs;

    AlarmManager alarmManagerfs;

    static String restarter;

    public static final String CHANNEL_ID="Mini Panda Service";

    private void createNotificationChannels(Context context)
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            NotificationChannel channel= new NotificationChannel(
                    CHANNEL_ID,
                    "Mini Panda Service",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("Mini Panda Service");

            NotificationManager manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            manager.createNotificationChannel(channel);

        }
    }


    public AlarmService(Context applicationContext) {
        super();
        Log.i("HERE", "here I am!");
    }

    public AlarmService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannels(this);


        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class).putExtra("toolFragment", "toolItem"), PendingIntent.FLAG_UPDATE_CURRENT);



        Notification notificationyo= new NotificationCompat.Builder(this,CHANNEL_ID)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setTicker("mini Panda Notification Service Active")
                .setSmallIcon(android.R.drawable.ic_menu_report_image)
                .setContentTitle("Service Running")
                .setContentText("Panda Mode Activated")
                .setContentIntent(contentIntent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(),
                        R.mipmap.ic_launcher))
                .build();


        startForeground(1,notificationyo);
        int ctimeh=0;

        Calendar cal = Calendar.getInstance();

        int h=cal.get(Calendar.HOUR_OF_DAY);

        String ho=new Integer(h).toString();

        Calendar calendar = Calendar.getInstance();

        int minu=0;

        int seco=0;

        boolean check=false;

        if((Integer.parseInt(ho) + 1) > 23){
        ctimeh=23;
        minu=59;
        seco=59;
        check=true;}
        else
        {
            ctimeh=(Integer.parseInt(ho)+ 1);
        }
        Log.i("pandatime", String.valueOf(ctimeh));

        calendar.set(Calendar.HOUR_OF_DAY,ctimeh);
        calendar.set(Calendar.MINUTE,minu);
        calendar.set(Calendar.SECOND,seco);
        Intent myIntent = new Intent(this, Notification_service.class);
        String packageName = getPackageName();
        PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
        if (!pm.isIgnoringBatteryOptimizations(packageName)) {
            myIntent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);}
        pendingIntent = PendingIntent.getService(this, 0,
                myIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);


        Calendar calfs = Calendar.getInstance();
        calfs.set(Calendar.HOUR_OF_DAY,ctimeh);
        calfs.set(Calendar.MINUTE,minu);
        calfs.set(Calendar.SECOND,seco);
        Intent foosleep = new Intent(this, Foodandsleep_service.class);
        String packageNa = getPackageName();
        PowerManager pmg = (PowerManager) getSystemService(POWER_SERVICE);
        if (!pmg.isIgnoringBatteryOptimizations(packageNa)) {
            foosleep.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);}
        pendingIntentfs = PendingIntent.getService(this, 0,
                foosleep,PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManagerfs = (AlarmManager) getSystemService(ALARM_SERVICE);

        if(check==false){
            alarmManager.setExact(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
        alarmManagerfs.setExact(AlarmManager.RTC_WAKEUP,calfs.getTimeInMillis(),pendingIntentfs);}
        else
        {alarmManagerfs.setExact(AlarmManager.RTC_WAKEUP,(calfs.getTimeInMillis()+1000),pendingIntentfs);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP,(calendar.getTimeInMillis()+1000),pendingIntent); check=false;}

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        final String st = intent.getExtras().getString("start").toString();
        startalarm(st);
        return START_STICKY;
    }


    @Override
    public void onDestroy() {

        super.onDestroy();
        Log.i("EXIT", "ondestroy!");
        Intent broadcastIntent = new Intent(this, AlarmRestarterBroadcastReceiver.class);
        broadcastIntent.putExtra("res",restarter);
        sendBroadcast(broadcastIntent);
        stopalarm();
    }


    public void startalarm(String start) {

        createNotificationChannels(this);

        restarter=start;

    }


    public static void change(String ch)
    {
        restarter=ch;
    }


    public void stopalarm() {

        new Notification_service().notserstop();

        new Foodandsleep_service().notserstop();

        alarmManager.cancel(pendingIntent);

        Log.i("HERE", "service stopped");
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}