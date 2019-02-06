package com.abdullah.dailydiary.helpers;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.RemoteInput;

import com.abdullah.dailydiary.R;

/**
 * Created by muhammadabdullah on 24/03/18.
 */

public class NotficationsManager {

    public static void notficationBuilder(Context mContext) {

        //String replyLabel = mContext.getResources().getString(R.string.reply_label);
        RemoteInput remoteInput = new RemoteInput.Builder("extra")
                .setLabel("reply")
                .build();

        PendingIntent replyPendingIntent =
                PendingIntent.getBroadcast(mContext.getApplicationContext(),
                        1,
                        new Intent(),
                        PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Action action =
                new NotificationCompat.Action.Builder(R.drawable.ic_add_black_24dp,
                        "hi", replyPendingIntent)
                        .addRemoteInput(remoteInput)
                        .build();
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext,"1")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Hello World")
                .setContentText("Testing content")
                .addAction(action)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library
            CharSequence name = "channelName";
            String description = "channelDescription";
            int importance = NotificationManagerCompat.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("1", name, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(description);
            // Register the channel with the system
            //NotificationManagerCompat notificationManager = NotificationManagerCompat.from(mContext);
            //notificationManager.createNotificationChannel(channel);

            NotificationManager notificationManager1 = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);;
            notificationManager1.createNotificationChannel(channel);
        }



        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(mContext);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify((int) System.currentTimeMillis(), mBuilder.build());


    }



}
