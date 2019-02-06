package com.abdullah.dailydiary.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.abdullah.dailydiary.R;
import com.abdullah.dailydiary.dto.DailyDiary;
import com.abdullah.dailydiary.helpers.Globals;

import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.DailyDiaryViewHolder> {

    private List<DailyDiary> DailyDiaryThreadArrayList;
    private Activity mActivity;
    private Context mContext;

    public RecyclerViewAdapter(List<DailyDiary> list, Context context, Activity activity) {
        try {
            DailyDiaryThreadArrayList = list;
            mContext = context;
            mActivity = activity;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public DailyDiaryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.recycler_view_item, parent, false);
        return new DailyDiaryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DailyDiaryViewHolder holder, int position) {
        try {
            DailyDiary DailyDiary = DailyDiaryThreadArrayList.get(DailyDiaryThreadArrayList.size()-1-position);
            holder.bindHolder(DailyDiary);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return DailyDiaryThreadArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class DailyDiaryViewHolder extends RecyclerView.ViewHolder {

        TextView date, goodDeed;
        LinearLayout linearLayout;

        public DailyDiaryViewHolder(View itemView) {
            super(itemView);
            goodDeed = (TextView) itemView.findViewById(R.id.text_view_good_deed);
            date = (TextView) itemView.findViewById(R.id.text_view_date);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linear_layout);
        }

        public void bindHolder(final DailyDiary dailyDiary) {
            try {
                goodDeed.setText(dailyDiary.getGoodDeed());
                date.setText(dailyDiary.getDate());
                linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openBottomSheet(dailyDiary);
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void openBottomSheet(DailyDiary dailyDiary) {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(mContext);
            View view = mActivity.getLayoutInflater().inflate(R.layout.diary_item, null);
            EditText first,second,third,goodDeed;
            TextView date;
            first = (EditText) view.findViewById(R.id.et_first);
            second = (EditText) view.findViewById(R.id.et_second);
            third = (EditText) view.findViewById(R.id.et_third);
            goodDeed = (EditText) view.findViewById(R.id.et_good_deed);
            date = (TextView) view.findViewById(R.id.text_view_date);

            first.setText(dailyDiary.getThankfulLine1());
            second.setText(dailyDiary.getThankfulLine2());
            third.setText(dailyDiary.getThankfull3());
            goodDeed.setText(dailyDiary.getGoodDeed());
            date.setText(dailyDiary.getDate());
            bottomSheetDialog.setContentView(view);
            BottomSheetBehavior mBehavior = BottomSheetBehavior.from((View) view.getParent());
            mBehavior.setPeekHeight(Globals.getInstance().mScreenHeight);
            bottomSheetDialog.show();
        }
    }
}