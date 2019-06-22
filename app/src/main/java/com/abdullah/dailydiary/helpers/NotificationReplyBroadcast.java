package com.abdullah.dailydiary.helpers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.RemoteInput;

import com.abdullah.dailydiary.RoomDatabase.AppDatabase;
import com.abdullah.dailydiary.RoomDatabase.DiaryRepo;

import java.util.Calendar;

public class NotificationReplyBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context,  Intent intent) {
        AppDatabase appDatabase = AppDatabase.getDatabase(context);
        DiaryRepo diaryRepo = new DiaryRepo(appDatabase.diaryDao());
        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);




        if (remoteInput!= null){
            diaryRepo.insert(intent.getIntExtra("id", -1), remoteInput.getCharSequence("extra").toString(), (DiaryEnums) intent.getSerializableExtra("level"));
        }



//        new Thread() {
//            public void run(){
//                AppDatabase appDatabase = AppDatabase.getDatabase(context);
//
//                Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
//
//                 if(remoteInput!=null)
//2
//                    DiaryEntity diaryEntity = new DiaryEntity();
//                    diaryEntity.setGoodDeed(name.toString());
//                    diaryEntity.setDate(String.valueOf(Calendar.getInstance().getTime()));
//                    appDatabase.diaryDao().insert(diaryEntity);
//                }
//            }
//
//
//        }.start();

    }
}
