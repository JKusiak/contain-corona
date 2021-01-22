package com.example.containcorona;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {
    public static final String NOTIFICATIONS_CHANNEL_ID = "many_diseased_channel";
    public SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createNotificationChannels();
        prefs =  getSharedPreferences("com.example.containcorona", 0);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        NavController navController = Navigation.findNavController(this,  R.id.fragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
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
}