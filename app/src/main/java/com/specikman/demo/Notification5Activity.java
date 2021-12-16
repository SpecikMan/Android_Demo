package com.specikman.demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.specikman.demo.databinding.ActivityNotification4Binding;
import com.specikman.demo.databinding.ActivityNotification5Binding;

public class Notification5Activity extends AppCompatActivity {

    private ActivityNotification5Binding binding = null;
    private final String CHANNEL_ID = "ChannelID";
    private final String CHANNEL_NAME = "ChannelName";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotification5Binding.inflate(getLayoutInflater());
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
    }

    private void createNotificationChannel() {
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.createNotificationChannel(channel);
    }


    private void showNotification(String title, String content, String subtext, int priority
            , int smallIcon) {
        //Set broadcast intent
        Intent broadcastIntent = new Intent(this, BroadcastReceiver.class);
        broadcastIntent.putExtra("message_from_notification", "Button Click");
        PendingIntent actionIntent = PendingIntent.getBroadcast(this, 0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        //Build notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(smallIcon)
                .setContentTitle(title)
                .setContentText(content)
                .setSubText(subtext)
                .setPriority(priority)
                .setColor(Color.BLUE)
                .addAction(R.mipmap.ic_launcher, "Toast", actionIntent)
                .addAction(R.mipmap.ic_launcher, "Update", actionIntent)
                .addAction(R.mipmap.ic_launcher, "Cancel", actionIntent)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true);
        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        manager.notify(0, builder.build());
    }
}