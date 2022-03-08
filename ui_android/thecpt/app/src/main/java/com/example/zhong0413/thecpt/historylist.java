package com.example.zhong0413.thecpt;

import android.util.Log;

public class historylist {

    private String title;
    private String date;
    private String actNo;

    public historylist(){

    }
    public historylist(String t, String d , String no ){
        title = t;
        date = d;
        actNo = no;
    }
    public String getTitle(){
        return title;
    }
    public String getDate(){
        return date;
    }
    public String getActNo(){
        return actNo;
    }
    public void setTitle(String t){
        title = t;
    }
    public void setDate(String d){
        date = d;
    }
    public void setActNo(String no){
        actNo = no;
    }
}
