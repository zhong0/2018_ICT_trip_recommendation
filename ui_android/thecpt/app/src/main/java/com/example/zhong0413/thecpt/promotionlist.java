package com.example.zhong0413.thecpt;

public class promotionlist {
    private String title;
    private String description;
    private String proNo;

    public promotionlist() {

    }

    public promotionlist(String t, String d,String no) {
        title = t;
        description = d;
        proNo = no;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getProNo(){
        return proNo;
    }

    public void setTitle(String t) {
        title = t;
    }

    public void setDescription(String d) {
        description = d;
    }
    public void setProNo(String n){
        proNo = n;
    }
}
