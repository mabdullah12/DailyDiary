package com.abdullah.dailydiary.helpers;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.RemoteInput;
import android.support.v4.util.Pair;

import com.abdullah.dailydiary.R;
import com.abdullah.dailydiary.RoomDatabase.AppDatabase;
import com.abdullah.dailydiary.RoomDatabase.DiaryEntity;

import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by muhammadabdullah on 24/03/18.
 */

public class NotficationsManager {

    public void notficationBuilder(Context mContext, Pair<DiaryEnums, Integer> pair, String notificationContentTitle){//int replyType , String notificationContentTitle, String notificationContentText) {




        //String replyLabel = mContext.getResources().getString(R.string.reply_label);
        RemoteInput remoteInput = new RemoteInput.Builder("extra")
                .setLabel("reply")
                .build();


        Intent intent = new Intent(mContext, NotificationReplyBroadcast.class);
        intent.putExtra("level", pair.first);
        intent.putExtra("id", pair.second);
        PendingIntent replyPendingIntent =
                PendingIntent.getBroadcast(mContext.getApplicationContext(),
                        1,
                       /* new Intent(mContext, NotificationReplyBroadcast.class)*/ intent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Action action =
                new NotificationCompat.Action.Builder(R.drawable.ic_add_black_24dp,
                        "", replyPendingIntent)
                        .addRemoteInput(remoteInput)
                        .build();

        AppDatabase appDatabase;
        List<DiaryEntity> diaryEntityList;
        NotificationCompat.Builder mBuilder;
        try {
            appDatabase = AppDatabase.getDatabase(mContext);
            //diaryEntityList = appDatabase.diaryDao().getAllDiaryEntityByDate("%"+String.valueOf(Calendar.getInstance().getTime())+"%");

            mBuilder = new NotificationCompat.Builder(mContext,"1")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(notificationContentTitle)
                    .setContentText(pair.first.getDesc())
                    .addAction(action)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        }
        catch (Exception e){
            mBuilder = new NotificationCompat.Builder(mContext,"1")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("Exception")
                    .setContentText(e.getMessage())
                    .addAction(action)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        }




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

            NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);;
            notificationManager.createNotificationChannel(channel);
        }



        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(mContext);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify((int) System.currentTimeMillis(), mBuilder.build());


    }


}
