package com.abdullah.dailydiary.RoomDatabase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;


/**
 * Created by muhammadabdullah on 27/10/18.
 */

@Database(entities = {DiaryEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DiaryDao diaryDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "daily_diary_app_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    
}
