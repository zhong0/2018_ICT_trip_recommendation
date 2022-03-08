package com.example.zhong0413.thecpt;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
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

public class home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private String Uri = "http://35.170.200.252/db/history_get.php";
    private String UriLeaf = "http://35.170.200.252/db/checkLeafResetTime.php";
    private String UriPromotion = "http://35.170.200.252/db/showPromotion_info.php";
    private static String[] placeNamePro = new String[100];
    private static String[] proBrief = new String[100];
    private static String[] proNo = new String[100];

    private RequestQueue rq;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private SharedPreferences getData;
    private SharedPreferences prefs;

    private String UriCatchCoupon = "http://35.170.200.252/db/showCoupon_info.php";
    private static String[] placeName = new String[100];
    private static String[] couponNo = new String[100];
    private static String[] couponIntro = new String[100];
    private static int[] leavesAmount = new int[100];

    private Boolean rated;
    private Boolean login;
    private String account;
    private ImageButton start;
    private ImageButton btChoseTrip;

    private String gender;
    private String clickedTrip;
    Toolbar toolbar;
    public static String check = "home";

    private static String[] allHistoryActName = new String[100];
    private static String[] allHistoryActNo = new String[100];
    private static String[] allHistoryItemName = new String[100];
    private static String[] allHistoryDate = new String[100];
    private static int count = 0;

    private static int num = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        buildView();
    }

    private void buildView(){
        getData = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        rated = getData.getBoolean("saveRate_key",true);
        account = getData.getString("account_key","");
        login = getData.getBoolean("logged",false);
        clickedTrip = getData.getString("clickedTrip_key","0");
        gender = getData.getString("gender_key","");

        toolbar = (Toolbar)findViewById(R.id.toolbar);

        drawerLayout = (DrawerLayout)findViewById(R.id.homepage);
        NavigationView navigationView = (NavigationView)findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        setSupportActionBar(toolbar);
        Log.i("gender",gender);
        if(account.equals("")){
            toolbar.setNavigationIcon(R.drawable.unknownicon);
        }else if(!account.equals("") && gender.equals("male")){
            toolbar.setNavigationIcon(R.drawable.manicon);
        }else if(!account.equals("") && gender.equals("female")){
            toolbar.setNavigationIcon(R.drawable.womanicon);
        }else{
            toolbar.setNavigationIcon(R.drawable.hamburger);
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(account.equals("")){
            navigationView.getMenu().getItem(0).setActionView(R.layout.menuimage);
        }

        btChoseTrip = (ImageButton)findViewById(R.id.imbtNewTrip);
        if(clickedTrip.equals("1")){
            Toast.makeText(home.this,"點選金葉子查看新旅程",Toast.LENGTH_SHORT).show();
            btChoseTrip.setVisibility(View.VISIBLE);
        }


        if(login){
            checkLeaf();
        }

        start = (ImageButton)findViewById(R.id.btStart);
        btChoseTrip.setOnClickListener(myListener);
        start.setOnClickListener(myListener);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        if(id == R.id.AccountMenu){
            Intent intentL = new Intent();
            if(login){
                intentL.setClass(home.this,account.class);
                startActivity(intentL);
            }else{
                intentL.setClass(home.this,login.class);
                startActivity(intentL);
            }
            return true;
        }else if(id == R.id.HistoryMenu){
            if(account.equals("")){
                Toast.makeText(home.this,"請先至我的帳戶登入",Toast.LENGTH_SHORT).show();
            }else {
                catchAllActivity();
            }
            return true;
        }else if(id == R.id.discountMenu){
            if(account.equals("")){
                Toast.makeText(home.this,"請先至我的帳戶登入",Toast.LENGTH_SHORT).show();
            }else {
                catchAllCoupon();
            }
            return true;
        }else if(id == R.id.leafMenu){
            if(account.equals("")){
                Toast.makeText(home.this,"請先至我的帳戶登入",Toast.LENGTH_SHORT).show();
            }else{
                Intent intentL  = new Intent();
                intentL.setClass(home.this,leavesSave.class);
                startActivity(intentL);
            }
            return true;
        }
        else if(id == R.id.specailActMenu){
            catchPromotionDetail();
            return true;
        }
        return false;
    }

    private View.OnClickListener myListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btStart:
                    Intent intent = new Intent();
                    intent.setClass(home.this,mode.class);
                    startActivity(intent);
                    break;
                case R.id.imbtNewTrip:
                    Intent intentN = new Intent();
                    intentN.setClass(home.this,history.class);
                    startActivity(intentN);
                    break;
            }

        }
    };
    public void catchAllActivity() {
        rq = Volley.newRequestQueue(this);
        StringRequest requestMode2 = new StringRequest(Request.Method.POST, Uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseJsonWithGson(response);
                check = "home";
                Intent intentH  = new Intent();
                intentH.setClass(home.this,allhistory.class);
                startActivity(intentH);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if( error instanceof com.android.volley.AuthFailureError){
                    Toast.makeText(home.this,"Authentication error",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.NetworkError) {
                    Toast.makeText(home.this, "NetworkError", Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.ParseError){
                    Toast.makeText(home.this,"ParseError ",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.ServerError){
                    Toast.makeText(home.this,"ServerError",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.NoConnectionError){
                    Toast.makeText(home.this,"NoConnectionError",Toast.LENGTH_SHORT).show();
                }
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<>();
                parameters.put("user_ID",account);
                return parameters;
            }
        };
        MySingleton.getInstance(this).addToRequestQueue(requestMode2);
    }

    public void parseJsonWithGson(String response){
        Gson gson = new Gson();
        List<toJson> toJsonsList = gson.fromJson(response , new TypeToken<List<toJson>>(){}.getType());
        count = 0;
        for(toJson Tojson : toJsonsList){
            allHistoryItemName[count] = Tojson.item;
            allHistoryActName[count] = Tojson.palce_name;
            allHistoryActNo[count] = Tojson.activity_no;
            allHistoryDate[count] = Tojson.date;
            count++;
        }
    }

    public void checkLeaf() {
        rq = Volley.newRequestQueue(this);
        StringRequest requestMode2 = new StringRequest(Request.Method.POST, UriLeaf, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if( error instanceof com.android.volley.AuthFailureError){
                    Toast.makeText(home.this,"Authentication error",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.NetworkError) {
                    Toast.makeText(home.this, "NetworkError", Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.ParseError){
                    Toast.makeText(home.this,"ParseError ",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.ServerError){
                    Toast.makeText(home.this,"ServerError",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.NoConnectionError){
                    Toast.makeText(home.this,"NoConnectionError",Toast.LENGTH_SHORT).show();
                }
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<>();
                parameters.put("user_ID",account);
                return parameters;
            }
        };
        MySingleton.getInstance(this).addToRequestQueue(requestMode2);
    }


    private void catchAllCoupon(){
        rq = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, UriCatchCoupon, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseJsonWithGsonCoup(response);
                Intent intentD = new Intent();
                intentD.setClass(home.this, discountarea.class);
                startActivity(intentD);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if( error instanceof com.android.volley.AuthFailureError){
                    Toast.makeText(home.this,"Authentication error",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.NetworkError){
                    Toast.makeText(home.this,"請連上網路",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.ParseError){
                    Toast.makeText(home.this,"ParseError ",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.ServerError){
                    Toast.makeText(home.this,"ServerError",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.NoConnectionError){
                    Toast.makeText(home.this,"請連上網路",Toast.LENGTH_SHORT).show();
                }
            }
        }) {

        };
        MySingleton.getInstance(home.this).addToRequestQueue(request);
    }

    public void parseJsonWithGsonCoup(String response){
        Gson gson = new Gson();
        List<toJson> toJsonsList = gson.fromJson(response , new TypeToken<List<toJson>>(){}.getType());
        num = 0;
        for(toJson Tojson : toJsonsList){
            placeName[num] = Tojson.palce_name;
            couponNo[num] = Tojson.cou_no;
            couponIntro[num] = Tojson.cou_intro;
            leavesAmount[num] = Tojson.leaves_amount;
            Log.i("check",couponNo[num]);
            num++;
        }

    }


    private void catchPromotionDetail(){
        rq = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, UriPromotion, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseJsonWithGsonPro(response);
                Intent intentS  = new Intent();
                intentS.setClass(home.this,speciailAct.class);
                startActivity(intentS);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if( error instanceof com.android.volley.AuthFailureError){
                    Toast.makeText(home.this,"Authentication error",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.NetworkError){
                    Toast.makeText(home.this,"請連上網路",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.ParseError){
                    Toast.makeText(home.this,"ParseError ",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.ServerError){
                    Toast.makeText(home.this,"ServerError",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.NoConnectionError){
                    Toast.makeText(home.this,"請連上網路",Toast.LENGTH_SHORT).show();
                }
            }
        }) {};
        MySingleton.getInstance(home.this).addToRequestQueue(request);
    }

    public void parseJsonWithGsonPro(String response){
        Gson gson = new Gson();
        List<toJson> toJsonsList = gson.fromJson(response , new TypeToken<List<toJson>>(){}.getType());
        num = 0;
        for(toJson Tojson : toJsonsList){
            placeNamePro[num] = Tojson.palce_name;
            proBrief[num] = Tojson.pro_brief;
            proNo[num] = Tojson.pro_no;
            num++;
        }
    }

    public static int reNumofHistory(){
        return count;
    }

    public static String reNameofHisory(int i){
        return allHistoryActName[i] + allHistoryItemName[i];
    }

    public static String reallHistoryActNo(int i){
        return allHistoryActNo[i];
    }

    public static String reallDateofHistroy(int i){
        return allHistoryDate[i];
    }

    public static String rePlaceName(int i){
        return placeName[i];
    }
    public static String reCouponNo(int i){
        return couponNo[i];
    }
    public static String reCouponIntro(int i){
        return couponIntro[i];
    }
    public static int reLeafAmount(int i){
        return leavesAmount[i];
    }
    public static int reNum(){
        return num;
    }

    public static String reProPlaceName(int i){
        return placeNamePro[i];
    }

    public static String reProBrief(int i){
        return proBrief[i];
    }
    public static String reProNo(int i){
        return proNo[i];
    }
}


