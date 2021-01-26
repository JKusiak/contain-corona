package com.example.containcorona;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {
    public static final String NOTIFICATIONS_CHANNEL_ID = "many_diseased_channel";
    SharedPreferences appPreferences;
    NotificationManagerCompat notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appPreferences = this.getSharedPreferences("com.example.containcorona", Context.MODE_PRIVATE);
        createNotificationChannels();
        notificationManager = NotificationManagerCompat.from(this);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        NavController navController = Navigation.findNavController(this, R.id.fragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }

    @Override
    protected void onStop() {
        super.onStop();
        notifyUser();
    }


    public void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel many_diseased_channel = new NotificationChannel(
                    NOTIFICATIONS_CHANNEL_ID,
                    "Many diseased channel",
                    NotificationManager.IMPORTANCE_HIGH
            );
            many_diseased_channel.setDescription("Wanna bake a bread?");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(many_diseased_channel);
        }
    }

    public void notifyUser() {
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

}