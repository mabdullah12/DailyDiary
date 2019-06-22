package com.abdullah.dailydiary.helpers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.util.Pair;
import android.widget.Switch;

import com.abdullah.dailydiary.RoomDatabase.AppDatabase;
import com.abdullah.dailydiary.RoomDatabase.DiaryEntity;
import com.abdullah.dailydiary.RoomDatabase.DiaryRepo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class WorkScheduler extends Worker {

   // @Inject


    DiaryRepo diaryRepo;
    private SimpleDateFormat sdf =  new SimpleDateFormat("dd-MMM-yyyy");
    public WorkScheduler(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        diaryRepo = new DiaryRepo(AppDatabase.getDatabase(getApplicationContext()).diaryDao());
        diaryRepo.diaryLiveListByDate(String.valueOf(sdf.format(Calendar.getInstance().getTime())), new CallBacks() {
            @Override
            public void callBack(Pair<DiaryEnums, Integer>  pair) {


                if (pair.first != DiaryEnums.ZERO){
                    NotficationsManager notficationsManager = new NotficationsManager();
                    notficationsManager.notficationBuilder(getApplicationContext(), pair ,  "Diary Time");
                }

                /*switch (pair.first.getDesc()){
                    case DiaryEnums.ZERO.getDesc():
                        break;
                    default:

                }*/


            }
        });
        return Worker.Result.success();
    }





}
