package com.abdullah.dailydiary.ui.actvities;

import android.arch.lifecycle.Observer;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.abdullah.dailydiary.R;
import com.abdullah.dailydiary.RoomDatabase.AppDatabase;
import com.abdullah.dailydiary.RoomDatabase.DailyDiary;
import com.abdullah.dailydiary.RoomDatabase.DiaryEntity;
import com.abdullah.dailydiary.RoomDatabase.DiaryRepo;
import com.abdullah.dailydiary.helpers.Globals;
import com.abdullah.dailydiary.helpers.NotficationsManager;
import com.abdullah.dailydiary.helpers.WorkScheduler;
import com.abdullah.dailydiary.ui.adapters.RecyclerViewAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import static com.abdullah.dailydiary.helpers.Constants.PERIODIC_NOTIFICATION_SCHEDULER;

public class MainActivity extends AppCompatActivity {



    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private Globals mGlobals;
    private List<DiaryEntity> dailyDiaries = new ArrayList<>();
    private BottomSheetDialog bottomSheetDialog;
    private RecyclerViewAdapter recyclerViewAdapter;
    private DiaryRepo diaryRepo;
    private SimpleDateFormat sdf =  new SimpleDateFormat("dd-MMM-yyyy");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeApplication();

    }

    private void initializeApplication() {
        mGlobals = Globals.getUsage(this);
        notificationScheduler();
        initData();
        initViews();

    }



    private void initData() {
        try {
            diaryRepo = new DiaryRepo(mGlobals.appDatabase.diaryDao());
            String dateStr =  String.valueOf(sdf.format(Calendar.getInstance().getTime()));
            diaryRepo.getDiaryLiveList().observe(this, new Observer<List<DiaryEntity>>() {
                @Override
                public void onChanged(@Nullable List<DiaryEntity> diaryEntities) {
                    dailyDiaries.clear();
                    dailyDiaries.addAll(diaryEntities);
                    recyclerViewAdapter.notifyDataSetChanged();
                }
            });
            //dailyDiaries = (ArrayList<DailyDiary>) DailyDiary.listAll(DailyDiary.class);
            /*mGlobals.appDatabase.diaryDao().getAllDiaryEntity().observe(this, new Observer<List<DiaryEntity>>() {
                @Override
                public void onChanged(@Nullable List<DiaryEntity> diaryEntities) {
                    dailyDiaries.clear();
                    dailyDiaries.addAll(diaryEntities);
                    recyclerViewAdapter.notifyDataSetChanged();
                }
            });*/
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void initViews() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.floation_action_button);
        initClickListners();
        setAdapter();

    }

    private void initClickListners() {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBottomSheet();
                //NotficationsManager.notficationBuilder(getApplicationContext());
            }
        });
    }

    private void setAdapter() {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerViewAdapter = new RecyclerViewAdapter(dailyDiaries, this, this);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    private void openBottomSheet() {
        bottomSheetDialog = new BottomSheetDialog(this);
        View view = getLayoutInflater().inflate(R.layout.add_diary, null);
        final EditText first,second,third,goodDeed;
        Button buttonSave;
        first = (EditText) view.findViewById(R.id.et_first);
        second = (EditText) view.findViewById(R.id.et_second);
        third = (EditText) view.findViewById(R.id.et_third);
        goodDeed = (EditText) view.findViewById(R.id.et_good_deed);
        buttonSave = (Button) view.findViewById(R.id.button_save);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (first.getText().length()!=0 &&
                        second.getText().length()!=0 &&
                        third.getText().length()!=0 &&
                        goodDeed.getText().length()!=0) {
                    final DiaryEntity dailyDiary = new DiaryEntity();
                    dailyDiary.setDate(String.valueOf(sdf.format(Calendar.getInstance().getTime())));
                    dailyDiary.setGoodDeed(goodDeed.getText().toString());
                    dailyDiary.setLine1(first.getText().toString());
                    dailyDiary.setLine2(second.getText().toString());
                    dailyDiary.setLine3(third.getText().toString());

                    new Thread(){
                        public void run(){
                            //mGlobals.appDatabase.diaryDao().insert(dailyDiary);
                            diaryRepo.insert(dailyDiary);
                        }
                    }.start();



//                    dailyDiaries.add(dailyDiary);
//                    recyclerView.getAdapter().notifyDataSetChanged();
                    bottomSheetDialog.dismiss();
                }

            }
        });



        bottomSheetDialog.setContentView(view);
        BottomSheetBehavior mBehavior = BottomSheetBehavior.from((View) view.getParent());
        mBehavior.setPeekHeight(Globals.getInstance().mScreenHeight);
        bottomSheetDialog.show();
        //NotficationsManager.notficationBuilder(this);

    }


    private void notificationScheduler(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        if (!sharedPreferences.getBoolean(PERIODIC_NOTIFICATION_SCHEDULER, false)){
            PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(WorkScheduler.class, 16, TimeUnit.MINUTES).build();
            WorkManager.getInstance().enqueue(periodicWorkRequest);


            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(PERIODIC_NOTIFICATION_SCHEDULER, true);
            editor.commit();
        }
    }

}
