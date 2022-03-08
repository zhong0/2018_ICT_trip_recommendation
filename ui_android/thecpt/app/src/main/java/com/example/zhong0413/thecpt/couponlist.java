package com.example.zhong0413.thecpt;

import android.util.Log;

public class couponlist {
    private String title;
    private String discription;
    private static int leafCost;
    private String couponNo;
    private String cl;
    public couponlist(){

    }
    public couponlist(String t, String d , int l , String no){
        Log.i("checkList1",no);
        title = t;
        discription = d;
        leafCost = l;
        couponNo = no;
        cl = Integer.toString(leafCost);
    }

    public String getTitle(){
        return title;
    }
    public String getDiscription(){
        return discription;
    }
    public static int getLeafCost(){
        return leafCost;
    }
    public String getCouponNo(){
        Log.i("checkList",couponNo);
        return couponNo;
    }
    public String getLeafCostText(){
        return cl;
    }
    public void setTitle(String t){
        title = t;
    }
    public void setDate(String d){
        discription = d;
    }

}

