package com.example.kachow_now;
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class App extends Application{

    public static final String ACCEPTED_ID = "accepted";
    public static final String REJECTED_ID = "rejected";

    public void onCreate() {
        super.onCreate();

        createNotificationChannels();
    }

    private void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(
                    ACCEPTED_ID,
                    "Accepted",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription("Order accepted");

            NotificationChannel channel2 = new NotificationChannel(
                    REJECTED_ID,
                    "Rejected",
                    NotificationManager.IMPORTANCE_LOW
            );
            channel2.setDescription("Order rejected");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
            manager.createNotificationChannel(channel2);
        }
    }
}

