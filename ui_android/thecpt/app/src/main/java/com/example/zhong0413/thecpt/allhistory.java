package com.example.zhong0413.thecpt;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class allhistory extends AppCompatActivity {
    private SharedPreferences getData;
    private String userAccount;


    List<historylist> lstHistory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.allhistory);
        getData = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        userAccount = getData.getString("account_key","");
        lstHistory = new ArrayList<>();
        if(userAccount.equals("")){
            Toast.makeText(allhistory.this,"您尚未登入",Toast.LENGTH_SHORT).show();
        }else{
            putAllhistory();
        }

        RecyclerView myrv = (RecyclerView)findViewById(R.id.myrecyclerview);
        recyleViewAdapter myAdapter = new recyleViewAdapter(this,lstHistory);
        myrv.setLayoutManager(new GridLayoutManager(this,1));
        myrv.setAdapter(myAdapter);
    }



    private void putAllhistory(){
        if(home.check.equals("home")){
            for(int i = 0 ; i< home.reNumofHistory() ; i++) {
                lstHistory.add(new historylist(home.reNameofHisory(i), home.reallDateofHistroy(i), home.reallHistoryActNo(i)));
            }
        }else{
            for(int i = 0 ; i< actdiary.reNumofHistory() ; i++){
                lstHistory.add(new historylist(actdiary.reNameofHisory(i),actdiary.reallDateofHistroy(i),actdiary.reallHistoryActNo(i)));
                Log.i("test",actdiary.reNameofHisory(i) +" " + actdiary.reallDateofHistroy(i)+" "+actdiary.reallHistoryActNo(i));
            }
        }
    }

}
