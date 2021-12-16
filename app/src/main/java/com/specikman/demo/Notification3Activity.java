package com.specikman.demo;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.media.session.MediaSessionCompat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.specikman.demo.databinding.ActivityNotification3Binding;

public class Notification3Activity extends AppCompatActivity {
    private ActivityNotification3Binding binding = null;
    private final String CHANNEL_ID = "ChannelID";
    private final String CHANNEL_NAME = "ChannelName";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotification3Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        createNotificationChannel();
        binding.btnShow.setOnClickListener(e->{
            showNotification(createNotification(
                    "This is title text",
                    "This is content text",
                    "This is subtext",
                    NotificationCompat.PRIORITY_DEFAULT,
                    R.drawable.ic_music,
                    BitmapFactory.decodeResource(getResources(),R.drawable.img_music_album)
            ));
        });

        binding.btnNext.setOnClickListener(e->{
            Intent intent = new Intent(this, Notification4Activity.class);
            startActivity(intent);
        });
    }

    private void createNotificationChannel() {
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.createNotificationChannel(channel);
    }

    private Notification createNotification(String title, String content, String subtext, int priority
            , int smallIcon, Bitmap largeIcon) {
        return new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(smallIcon)
                .setContentTitle(title)
                .setContentText(content)
                .setSubText(subtext)
                .setLargeIcon(largeIcon)
                .setPriority(priority)
                .addAction(R.drawable.ic_music_prev,"Previous",null)
                .addAction(R.drawable.ic_music_play,"Play",null)
                .addAction(R.drawable.ic_music_next,"Next",null)
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                .setShowActionsInCompactView(1)
                .setMediaSession(new MediaSessionCompat(this,"TAG").getSessionToken()))
                .build();
    }

    private void showNotification(Notification notification) {
        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        manager.notify(0, notification);
    }

}