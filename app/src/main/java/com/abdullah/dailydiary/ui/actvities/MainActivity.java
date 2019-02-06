package com.abdullah.dailydiary.ui.actvities;

import android.os.Bundle;
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
import com.abdullah.dailydiary.dto.DailyDiary;
import com.abdullah.dailydiary.helpers.Globals;
import com.abdullah.dailydiary.helpers.NotficationsManager;
import com.abdullah.dailydiary.ui.adapters.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {



    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private Globals mGlobals;
    private ArrayList<DailyDiary> dailyDiaries = new ArrayList<>();
    private BottomSheetDialog bottomSheetDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeApplication();

    }

    private void initializeApplication() {
        mGlobals = Globals.getUsage(this);
        initData();
        initViews();

    }



    private void initData() {
        try {
            dailyDiaries = (ArrayList<DailyDiary>) DailyDiary.listAll(DailyDiary.class);
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
            }
        });
    }

    private void setAdapter() {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(dailyDiaries, this, this);
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
                    DailyDiary dailyDiary = new DailyDiary();
                    dailyDiary.setDate(String.valueOf(Calendar.getInstance().getTime()));
                    dailyDiary.setGoodDeed(goodDeed.getText().toString());
                    dailyDiary.setThankfulLine1(first.getText().toString());
                    dailyDiary.setThankfulLine2(second.getText().toString());
                    dailyDiary.setThankfull3(third.getText().toString());
                    dailyDiary.save();
                    dailyDiaries.add(dailyDiary);
                    recyclerView.getAdapter().notifyDataSetChanged();
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

}
