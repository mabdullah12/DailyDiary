package com.abdullah.dailydiary.RoomDatabase;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface DiaryDao {

    @Query("Select * from DiaryEntity")
    LiveData<List<DiaryEntity>> getAllDiaryEntity();

    @Query("Select * from DiaryEntity where date like '%' || :dateStr || '%'")
    List<DiaryEntity> getAllDiaryEntityByDate(String dateStr);

    @Update
    void update(DiaryEntity diaryEntity);

    @Insert
    void insert(DiaryEntity diaryEntity);

    @Query("Select * from DiaryEntity where id = :id")
    DiaryEntity getDiaryEntityById(int id);
}
