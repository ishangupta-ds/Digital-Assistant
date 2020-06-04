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

public class Foodandsleep_service extends IntentService {

    public static AlarmManager alarmManager1;

    public static PendingIntent pendingIntent1;

    private NotificationManagerCompat notificationManager1;

    public static final String CHANNEL_ID4="Food and Sleep Notification";


    private void createNotificationChannels(Context context)
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            NotificationChannel channel4= new NotificationChannel(
                    CHANNEL_ID4,
                    "Food and Sleep Notification",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel4.setDescription("Food and Sleep Notification");

            NotificationManager manager1 = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
            manager1.createNotificationChannels(Collections.singletonList(channel4));

        }
    }


    void send_notofications(Context context,String msg,String titl)
    {

        int SUMMARY_ID1=200;

        notificationManager1 = NotificationManagerCompat.from(context);

        long when=System.currentTimeMillis();

        Notification notification= new NotificationCompat.Builder(context,CHANNEL_ID4)
                .setWhen(when)
                .setTicker("Panda Food and Sleep Notification Service")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(titl)
                .setContentText(msg)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(),
                        R.mipmap.ic_launcher))
                .setGroup("com.ishupanda.mypanda.DAILYALARM")
                .build();



        Notification summaryNotification1 =
                new NotificationCompat.Builder(context, CHANNEL_ID4)
                        .setContentTitle("Daily notifications")
                        //set content text to support devices running API level < 24
                        .setContentText("Daily Notifications")
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        //build summary info into InboxStyle template
                        .setStyle(new NotificationCompat.InboxStyle()
                                .setBigContentTitle("Alarms")
                                .setSummaryText("Missed Notifications"))
                        //specify which group this notification belongs to
                        .setGroup("com.ishupanda.mypanda.DAILYALARM")
                        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setLargeIcon(BitmapFactory.decodeResource(this.getResources(),
                                R.mipmap.ic_launcher))
                        //set this notification as the summary for the group
                        .setGroupSummary(true)
                        .build();


        notificationManager1.notify((((int) ((new Date().getTime() / 1L) % Integer.MAX_VALUE))+300),notification);
        notificationManager1.notify(SUMMARY_ID1, summaryNotification1);

    }

    public Foodandsleep_service() {
        super("Foodandsleep_service");
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        Calendar cal = Calendar.getInstance();

        int h=cal.get(Calendar.HOUR_OF_DAY);

        String ho=new Integer(h).toString();

        int hou=Integer.parseInt(ho);

        Log.i("foodsleep check", String.valueOf(hou));

        String msgs=" ";
        String ti="";

        if(hou==6 || hou==8 || hou==13 || hou==17 || hou==20 || hou==23) {

            if(hou==6)
            {
                msgs="Good Morning";
                ti="Panda WakeUp Notification Service";
            }

            if(hou==8)
            {
                msgs="Its time for breakfast";
                ti="Panda Food Notification Service";
            }

            if(hou==13)
            {
                msgs="Its time for lunch";
                ti="Panda Food Notification Service";
            }

            if(hou==17)
            {
                msgs="Its time for snacks";
                ti="Panda Food Notification Service";
            }

            if(hou==20)
            {
                msgs="Its time for dinner";
                ti="Panda Food Notification Service";
            }

            if(hou==23)
            {
                msgs="Good Night, its time for bed";
                ti="Panda Sleep Notification Service";
            }

            createNotificationChannels(this);

            send_notofications(this,msgs,ti);

            Log.i("foodsleep notification", String.valueOf(hou));

        }

        Calendar calendar1 = Calendar.getInstance();

        int ctimeh2=0;

        int minu2=0;

        int seco2=0;

        boolean check2=false;

        if((hou + 1) > 23){
            ctimeh2=23;
            minu2=59;
            seco2=59;
            check2=true;}
        else
        {
            ctimeh2=hou+ 1;
        }
        Log.i("food and sleep set for", String.valueOf(ctimeh2));

        calendar1.set(Calendar.HOUR_OF_DAY,ctimeh2);
        calendar1.set(Calendar.MINUTE,minu2);
        calendar1.set(Calendar.SECOND,seco2);


        Intent myIntent = new Intent(this, Foodandsleep_service.class);
        String packageName = getPackageName();
        PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
        if (!pm.isIgnoringBatteryOptimizations(packageName)) {
            myIntent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);}
        pendingIntent1 = PendingIntent.getService(this, 0,
                myIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager1 = (AlarmManager) getSystemService(ALARM_SERVICE);


        if(check2==false){

            alarmManager1.setExact(AlarmManager.RTC_WAKEUP,calendar1.getTimeInMillis(),pendingIntent1);}
        else
        {alarmManager1.setExact(AlarmManager.RTC_WAKEUP,(calendar1.getTimeInMillis()+1000),pendingIntent1);
        check2=false;}


    }



    public void notserstop()
    {
        if(pendingIntent1 != null ) {
            alarmManager1.cancel(pendingIntent1);
        }
    }
}
