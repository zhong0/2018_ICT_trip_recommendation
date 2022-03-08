package com.example.zhong0413.thecpt;

import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.widget.ImageView;

public class myDiscountList {
    private String title;
    private String description;
    private String deadline;
    private Uri uri;
    private String no;
    private String ID;
    public boolean chose;

    public myDiscountList(){

    }
    public myDiscountList(Uri u, String t, String d , String ddl,String n,String id){
        uri = u;
        title = t;
        description = d;
        deadline = ddl;
        no = n;
        ID = id;
    }

    public String getTitle(){
        return title;
    }
    public String getDiscription(){
        return description;
    }
    public String getDeadline(){
        return deadline;
    }
    public Uri getUri(){
        return uri;
    }

    public String getID(){ return ID; }
    public void setTitle(String t){
        title = t;
    }
    public void setDescription(String d){
        description = d;
    }

    public String getCoupNo(){
        return no;
    }
    public void setDeadline(String ddl){
        deadline = ddl;
    }

}
