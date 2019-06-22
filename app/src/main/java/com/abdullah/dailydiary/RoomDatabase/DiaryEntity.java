package com.abdullah.dailydiary.RoomDatabase;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;


/**
 * Created by Abdullah on 10/02/2019
 */
@Entity
public class DiaryEntity {


    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "line1")
    private String line1;

    @ColumnInfo(name = "line2")
    private String line2;

    @ColumnInfo(name = "line3")
    private String line3;

    @ColumnInfo(name = "good_deed")
    private String goodDeed;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @ColumnInfo(name = "date")
    private String date;




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getLine2() {
        return line2;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public String getLine3() {
        return line3;
    }

    public void setLine3(String line3) {
        this.line3 = line3;
    }

    public String getGoodDeed() {
        return goodDeed;
    }

    public void setGoodDeed(String goodDeed) {
        this.goodDeed = goodDeed;
    }
}
