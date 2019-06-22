package com.abdullah.dailydiary.helpers;

public enum DiaryEnums {
    ZERO(""),
    ONE("Good deed for today isn't written. Please write now"),
    TWO("1st thankful statement for today isn't written. Please write now"),
    THREE("2nd thankful statement for today isn't written. Please write now"),
    FOUR("3rd thankful statement today isn't written. Please write now"),
    FIVE("You haven't written anything today. Start by writing your good deed")
    ;


    private String desc;
    public String getDesc(){
        return desc;
    }

    private DiaryEnums(String desc){
        this.desc = desc;
    }



}
