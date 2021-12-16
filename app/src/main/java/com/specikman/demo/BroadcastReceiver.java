package com.specikman.demo;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class BroadcastReceiver extends android.content.BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String message = intent.getStringExtra("message_from_notification");
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }
}
