package com.twopandas.remind.ui.tools;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import com.twopandas.myishupanda.R;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

public class Notification_service extends IntentService {

    public static AlarmManager alarmManager;

    public static PendingIntent pendingIntent;

    private NotificationManagerCompat notificationManager;

    public static final String CHANNEL_ID3="Water Notification";

    private void createNotificationChannels(Context context)
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            NotificationChannel channel3= new NotificationChannel(
                    CHANNEL_ID3,
                    "Water Notification",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel3.setDescription("Water Notification");

            NotificationManager manager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
            manager.createNotificationChannels(Collections.singletonList(channel3));

        }
    }


    void send_notofications(Context context)
    {

        int SUMMARY_ID=100;

        notificationManager = NotificationManagerCompat.from(context);

        long when=System.currentTimeMillis();

        Notification notification= new NotificationCompat.Builder(context,CHANNEL_ID3)
                .setWhen(when)
                .setTicker("Panda Notification Service")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Panda Water Notification Service")
                .setContentText("Have water Ishu ")
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(),
                        R.mipmap.ic_launcher))
                .setGroup("com.ishupanda.mypanda.ALARM")
                .build();

        Notification summaryNotification =
                new NotificationCompat.Builder(context, CHANNEL_ID3)
                        .setContentTitle("water notifications")
                        //set content text to support devices running API level < 24
                        .setContentText("water Notifications")
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        //build summary info into InboxStyle template
                        .setStyle(new NotificationCompat.InboxStyle()
                                .setBigContentTitle("Alarms")
                                .setSummaryText("Missed water Alarms"))
                        //specify which group this notification belongs to
                        .setGroup("com.ishupanda.mypanda.ALARM")
                        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setLargeIcon(BitmapFactory.decodeResource(this.getResources(),
                                R.mipmap.ic_launcher))
                        //set this notification as the summary for the group
                        .setGroupSummary(true)
                        .build();

        notificationManager.notify((((int) ((new Date().getTime() / 1L) % Integer.MAX_VALUE))-100), notification);
        notificationManager.notify(SUMMARY_ID, summaryNotification);

    }


    public Notification_service() {
        super("Notification_service");
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        Calendar cal = Calendar.getInstance();

        int h=cal.get(Calendar.HOUR_OF_DAY);

        String ho=new Integer(h).toString();

        int hou=Integer.parseInt(ho);

        Log.i("water check", String.valueOf(hou));

        if(hou>6 && hou<24) {

            createNotificationChannels(this);

            send_notofications(this);

            Log.i("water notification", String.valueOf(hou));
        }

        Calendar calendar = Calendar.getInstance();

        int ctimeh1=0;

        int minu1=0;

        int seco1=0;

        boolean check1=false;

        if((hou + 1) > 23){
            ctimeh1=23;
            minu1=59;
            seco1=59;
            check1=true;}
        else
        {
            ctimeh1=hou+ 1;
        }
        Log.i("water set for", String.valueOf(ctimeh1));

        calendar.set(Calendar.HOUR_OF_DAY,ctimeh1);
        calendar.set(Calendar.MINUTE,minu1);
        calendar.set(Calendar.SECOND,seco1);

        Intent myIntent = new Intent(this, Notification_service.class);
        String packageName = getPackageName();
        PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
        if (!pm.isIgnoringBatteryOptimizations(packageName)) {
            myIntent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);}
        pendingIntent = PendingIntent.getService(this, 0,
                myIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        if(check1==false){

            alarmManager.setExact(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);}
        else
        {alarmManager.setExact(AlarmManager.RTC_WAKEUP,(calendar.getTimeInMillis()+1000),pendingIntent);
        check1=false;}


    }



    public void notserstop()
    {
        if(pendingIntent != null ) {
            alarmManager.cancel(pendingIntent);
        }
    }
}
