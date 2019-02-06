package com.abdullah.dailydiary.dto;

import com.orm.SugarRecord;

import java.io.Serializable;

/**
 * Created by Hamza on 29/06/2017.
 */

public class DailyDiary extends SugarRecord implements Serializable {
    private String thankfulLine1;
    private String thankfulLine2;
    private String thankfull3;
    private String goodDeed;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    private String date;

    public String getGoodDeed() {
        return goodDeed;
    }

    public void setGoodDeed(String goodDeed) {
        this.goodDeed = goodDeed;
    }

    public String getThankfull3() {
        return thankfull3;
    }

    public void setThankfull3(String thankfull3) {
        this.thankfull3 = thankfull3;
    }

    public String getThankfulLine2() {
        return thankfulLine2;
    }

    public void setThankfulLine2(String thankfulLine2) {
        this.thankfulLine2 = thankfulLine2;
    }

    public String getThankfulLine1() {
        return thankfulLine1;
    }

    public void setThankfulLine1(String thankfulLine1) {
        this.thankfulLine1 = thankfulLine1;
    }
}
