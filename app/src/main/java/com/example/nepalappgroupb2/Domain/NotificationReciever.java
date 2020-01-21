package com.example.nepalappgroupb2.Domain;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.nepalappgroupb2.Calendar.CalenderActivity;
import com.example.nepalappgroupb2.R;

import static com.example.nepalappgroupb2.Homepage.HomepageMainActivity.CHANNEL_ID;

public class NotificationReciever extends BroadcastReceiver {
    private NotificationManagerCompat notiManager;


    @Override
    public void onReceive(Context context, Intent intent) {
        notiManager = NotificationManagerCompat.from(context);
        Intent repeatingIntent = new Intent(context, CalenderActivity.class);
        repeatingIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 100, repeatingIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Channel",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("Channel");
            NotificationManager manager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
            manager.createNotificationChannel(channel);
        }

        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.push_icon)
                .setContentTitle("NEW")
                .setContentText("You have something new in calendar")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setContentIntent(pendingIntent)
                .build();
            notiManager.notify(100,notification);
    }
}

