package com.example.zhong0413.thecpt;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class resulttable extends AppCompatActivity {
    String UriRate = "http://35.170.200.252/db/rate_table.php";
    String UriPlace = "http://35.170.200.252/db/place.php";
    RequestQueue rq;
    RequestQueue requestQueue;
    public static String[] completelyActName = {"", "", "", "", "", "", "", "", "", ""};
    private static String actNo = new String();
    private static String actTitle = new String();
    private static String itemNo = new String();

    private static String actChose = new String();
    private static String actIntro = new String();
    private static String actAdd = new String();
    private static String actTel = new String();

    private static String[] stDate = {"","",""};
    private static String[] edDate = {"","",""};
    private static String[] exIntro = {"","",""};
    private static String[] exName = {"","",""};

    private static String[] activityName = {"", "", "", "", "", "", "", "", "", ""};
    private static String[] activityNo = {"", "", "", "", "", "", "", "", "", ""};
    private static String[] ItemName = {"", "", "", "", "", "", "", "", "", ""};
    private static String[] ItemNo = {"", "", "", "", "", "", "", "", "", ""};

    private static String[] nowActNo = {"", "", "", "", "", "", "", "", "", ""};
    private static String[] nowItemNo = {"", "", "", "", "", "", "", "", "", ""};

    private Button a1;
    private Button a2;
    private Button a3;
    private Button a4;
    private Button a5;
    private Button a6;
    private Button a7;
    private Button a8;
    private Button a9;
    private Button a10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resulttable);
        buildView();
    }

    private void buildView(){
        if(completelyActName[0].equals("") || completelyActName[0] == choice.reActNo(0)){   //檢查不是重整後的，不要把值設回最一開始的結果
            for(int i = 0 ; i < 10 ; i++ ){
                completelyActName[i] = choice.reActName(i);
                nowActNo[i] = choice.reActNo(i);
                nowItemNo[i] = choice.reItemNo(i);
            }
        }
        for(int i=0 ; i<10 ; i++){
            nowItemNo[i] = choice.reItemNo(i);
            activityNo[i] = choice.reActNo(i);
            Log.i("checkInSetNo",activityNo[i]);
        }


        a1 = (Button)findViewById(R.id.act1);
        a1.setText(completelyActName[0]);

        a2 = (Button)findViewById(R.id.act2);
        a2.setText(completelyActName[1]);

        a3 = (Button)findViewById(R.id.act3);
        a3.setText(completelyActName[2]);

        a4 = (Button)findViewById(R.id.act4);
        a4.setText(completelyActName[3]);

        a5 = (Button)findViewById(R.id.act5);
        a5.setText(completelyActName[4]);

        a6 = (Button)findViewById(R.id.act6);
        a6.setText(completelyActName[5]);

        a7 = (Button)findViewById(R.id.act7);
        a7.setText(completelyActName[6]);

        a8 = (Button)findViewById(R.id.act8);
        a8.setText(completelyActName[7]);

        a9 = (Button)findViewById(R.id.act9);
        a9.setText(completelyActName[8]);

        a10 = (Button)findViewById(R.id.act10);
        a10.setText(completelyActName[9]);

        a1.setOnClickListener(myListener);
        a2.setOnClickListener(myListener);
        a3.setOnClickListener(myListener);
        a4.setOnClickListener(myListener);
        a5.setOnClickListener(myListener);
        a6.setOnClickListener(myListener);
        a7.setOnClickListener(myListener);
        a8.setOnClickListener(myListener);
        a9.setOnClickListener(myListener);
        a10.setOnClickListener(myListener);

    }
    //home button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navfortable, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                Intent intentHome = new Intent(this, home.class);
                intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentHome);
                break;

            case R.id.reChoose:
                recatch();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
    }
    public void recatch() {
        requestQueue = Volley.newRequestQueue(this);
        StringRequest requestMode2 = new StringRequest(Request.Method.POST,UriRate, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseJsonWithGsonRecatch(response);
                reName();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if( error instanceof com.android.volley.AuthFailureError){
                    Toast.makeText(resulttable.this,"Authentication error",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.NetworkError){
                    Toast.makeText(resulttable.this,"請連上網路",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.ParseError){
                    Toast.makeText(resulttable.this,"ParseError ",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.ServerError){
                    Toast.makeText(resulttable.this,"ServerError",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.NoConnectionError){
                    Toast.makeText(resulttable.this,"請連上網路",Toast.LENGTH_SHORT).show();
                }
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<>();
                parameters.put("Mode", mode.reType());
                switch (mode.reType()) {
                    case "personalUser":
                        Log.i("allValueU",mode.reType()+" "+mode.reGender()+" "+mode.reStatus()+" "+mode.reAge()+" "+choice.reChooseAct());
                        parameters.put("user_ID", mode.reAccount());
                        parameters.put("ActTypeForData", choice.reChooseAct());
                        break;

                    case "personalStranger":
                        Log.i("allValueP",mode.reType()+" "+mode.reGender()+" "+mode.reStatus()+" "+mode.reAge()+" "+choice.reChooseAct());
                        parameters.put("gender" , mode.reGender());
                        parameters.put("career_no" , mode.reStatus());
                        parameters.put("age_no", mode.reAge());
                        parameters.put("ActTypeForData", choice.reChooseAct());
                        break;

                    case "Group":
                        Log.i("allValue",mode.reType()+" "+mode.reGender()+" "+mode.reStatus()+" "+mode.reAge()+" "+choice.reChooseAct());
                        parameters.put("gender", mode.reGender());
                        parameters.put("age_no", mode.reAge());
                        parameters.put("group_type_no", mode.reStatus());
                        parameters.put("ActTypeForData", choice.reChooseAct());
                        break;
                }
                return parameters;
            }
        };
        MySingleton.getInstance(this).addToRequestQueue(requestMode2);
    }
    public void parseJsonWithGsonRecatch(String response){
        Gson gson = new Gson();
        List<toJson> toJsonsList = gson.fromJson(response , new TypeToken<List<toJson>>(){}.getType());
        if(toJsonsList!= null && toJsonsList.size()>0) {
            int count = 0;
            for (toJson Tojson : toJsonsList) {
                if(count>9){
                    break;
                }
                ItemNo[count] = Tojson.item_no;
                activityName[count] = Tojson.name;
                activityNo[count] = Tojson.activity_no;
                ItemName[count] = Tojson.item;
                count++;
            }
        }
    }
    private void reName(){
        for(int i = 0 ; i < 10 ; i++){
            completelyActName[i] = activityName[i]+ItemName[i];
            nowItemNo[i] = ItemNo[i];
            nowActNo[i] = activityNo[i];
        }
        a1.setText(completelyActName[0]);
        a2.setText(completelyActName[1]);
        a3.setText(completelyActName[2]);
        a4.setText(completelyActName[3]);
        a5.setText(completelyActName[4]);
        a6.setText(completelyActName[5]);
        a7.setText(completelyActName[6]);
        a8.setText(completelyActName[7]);
        a9.setText(completelyActName[8]);
        a10.setText(completelyActName[9]);
    }


    private View.OnClickListener myListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            for(int i =0 ; i<10 ; i++){
                Log.i("checkLis",nowActNo[i]);
            }
            switch (v.getId()) {
                case R.id.act1:
                    actTitle = completelyActName[0];
                    actNo = nowActNo[0];
                    itemNo = nowItemNo[0];
                    break;
                case R.id.act2:
                    actTitle = completelyActName[1];
                    actNo = nowActNo[1];
                    itemNo = nowItemNo[1];
                    break;
                case R.id.act3:
                    actTitle = completelyActName[2];
                    actNo = nowActNo[2];
                    itemNo = nowItemNo[2];
                    break;
                case R.id.act4:
                    actTitle = completelyActName[3];
                    actNo = nowActNo[3];
                    itemNo = nowItemNo[3];
                    break;
                case R.id.act5:
                    actTitle = completelyActName[4];
                    actNo = nowActNo[4];
                    itemNo = nowItemNo[4];
                    break;
                case R.id.act6:
                    actTitle = completelyActName[5];
                    actNo = nowActNo[5];
                    itemNo = nowItemNo[5];
                    break;
                case R.id.act7:
                    actTitle = completelyActName[6];
                    actNo = nowActNo[6];
                    itemNo = nowItemNo[6];
                    break;
                case R.id.act8:
                    actTitle = completelyActName[7];
                    actNo = nowActNo[7];
                    itemNo = nowItemNo[7];
                    break;
                case R.id.act9:
                    actTitle = completelyActName[8];
                    actNo = nowActNo[8];
                    itemNo = nowItemNo[8];
                    break;
                case R.id.act10:
                    actTitle = completelyActName[9];
                    actNo = nowActNo[9];
                    itemNo = nowItemNo[9];
                    break;
            }
            Log.i("checkactNo",actTitle+" "+actNo+" "+itemNo);
            catchAllActivity();
        }
    };

    public void catchAllActivity() {
        Log.i("check","incatch");
        rq = Volley.newRequestQueue(this);
        StringRequest requestMode2 = new StringRequest(Request.Method.POST,UriPlace, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseJsonWithGson(response);
                Intent intent = new Intent(resulttable.this, actdetail.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if( error instanceof com.android.volley.AuthFailureError){
                    Toast.makeText(resulttable.this,"Authentication error",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.NetworkError){
                    Toast.makeText(resulttable.this,"NetworkError",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.ParseError){
                    Toast.makeText(resulttable.this,"ParseError ",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.ServerError){
                    Toast.makeText(resulttable.this,"ServerError",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.NoConnectionError){
                    Toast.makeText(resulttable.this,"NoConnectionError",Toast.LENGTH_SHORT).show();
                }
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<>();
                Log.i("checkactNoinParams",actNo);
                parameters.put("activity_no",actNo);
                return parameters;
            }
        };
        MySingleton.getInstance(this).addToRequestQueue(requestMode2);
    }
    public void parseJsonWithGson(String response){
        Gson gson = new Gson();
        List<toJson> toJsonsList = gson.fromJson(response , new TypeToken<List<toJson>>(){}.getType());
        int i=0;
        for(toJson Tojson : toJsonsList){
            if(i>2){
                break;
            }
            if(itemNo.equals("4")) {
                stDate[i] = Tojson.startDate;
                edDate[i] = Tojson.endDate;
                exIntro[i] = Tojson.exhib_intro;
                exName[i] = Tojson.exhib_name;
            }
            if(i == 0){
                actChose = Tojson.palce_name;
                actIntro = Tojson.intro;
                actAdd = Tojson.addr;
                actTel = Tojson.tel;
            }
            i++;
        }
    }

    public static String reItemNo(){
        return itemNo;
    }
    public static String reActTitle(){
        return actTitle;
    }
    public static String reActNo(){
        return actNo;
    }
    public static String reActIntro(){
        return actIntro;
    }
    public static String reActAdd(){
        return actAdd;
    }
    public static String reActTel(){
        return actTel;
    }
    public static String reExbDate(int n){
        return stDate[n]+"~"+edDate[n];
    }
    public static String reExbIntro(int n){
        return exIntro[n];
    }
    public static String reExb(int n){
        return exName[n];
    }
}

