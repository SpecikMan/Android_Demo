package com.specikman.demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;

import com.specikman.demo.databinding.ActivityNotification3Binding;
import com.specikman.demo.databinding.ActivityNotification4Binding;

public class Notification4Activity extends AppCompatActivity {
    private ActivityNotification4Binding binding = null;
    private final String CHANNEL_ID = "ChannelID";
    private final String CHANNEL_NAME = "ChannelName";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotification4Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        createNotificationChannel();
        binding.btnShow.setOnClickListener(e -> {
            showNotification(
                    "Download",
                    "Download in progress",
                    "This is subtext",
                    NotificationCompat.PRIORITY_DEFAULT,
                    R.drawable.ic_notification
            );
        });

        binding.btnNext.setOnClickListener(e -> {
            Intent intent = new Intent(this, Notification5Activity.class);
            startActivity(intent);
        });
    }

    private void createNotificationChannel() {
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.createNotificationChannel(channel);
    }


    private void showNotification(String title, String content, String subtext, int priority
            , int smallIcon) {
        int progressMax = 100;
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(smallIcon)
                .setContentTitle(title)
                .setContentText(content)
                .setSubText(subtext)
                .setPriority(priority)
                .setOngoing(true)
                .setOnlyAlertOnce(true)
                .setProgress(progressMax, 0, true);

        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        manager.notify(0, builder.build());

        new Thread(() -> {
            SystemClock.sleep(2000);
            for (int progress = 0; progress <= progressMax; progress += 10) {
                builder.setProgress(progressMax,progress,true);
                manager.notify(0,builder.build());
                SystemClock.sleep(1000);
            }
            builder.setContentText("Download Complete").setProgress(0,0,false)
            .setOngoing(false);
            manager.notify(0,builder.build());
        }
        ).start();
    }
}