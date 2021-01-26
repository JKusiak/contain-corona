package com.example.containcorona;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Timer;
import java.util.TimerTask;

import static com.example.containcorona.MainActivity.NOTIFICATIONS_CHANNEL_ID;

public class NotificationService extends Service {

    Timer timer;
    TimerTask timerTask;
    String TAG = "Timers";
    int Your_X_SECS = 5;
    SharedPreferences appPreferences;
    NotificationManagerCompat notificationManager;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        startTimer();
        return START_STICKY;
    }


    @Override
    public void onCreate() {
        notificationManager = NotificationManagerCompat.from(getApplicationContext());
    }

    @Override
    public void onDestroy() {
        stoptimertask();
        super.onDestroy();
    }

    //to be able to run in TimerTask
    final Handler handler = new Handler();

    public void startTimer() {
        timer = new Timer();
        initializeTimerTask();

        //schedule the timer, after the first 5000ms the TimerTask will run every 10000ms
        timer.schedule(timerTask, 5000, Your_X_SECS * 1000); //
    }

    public void stoptimertask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public void initializeTimerTask() {

        timerTask = new TimerTask() {
            public void run() {

                //use a handler to run a toast that shows the current timestamp
                handler.post(new Runnable() {
                    public void run() {
                        //&& dailyCases >= xxx
                        if (appPreferences.getBoolean("switch_state", false) ){
                            Notification testNotification = new NotificationCompat.Builder(getApplicationContext(), NOTIFICATIONS_CHANNEL_ID)
                                    .setSmallIcon(R.drawable.ic_info)
                                    .setContentTitle("Rapid new cases growth")
                                    .setContentText("Please take care and be extra cautious")
                                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                                    .build();

                            notificationManager.notify(1, testNotification);
                        }
                    }
                });
            }
        };
    }
}