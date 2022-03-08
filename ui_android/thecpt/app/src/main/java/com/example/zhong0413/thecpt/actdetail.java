package com.example.zhong0413.thecpt;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
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

import java.util.HashMap;
import java.util.Map;

public class actdetail extends AppCompatActivity {
    String uri = "http://35.170.200.252/db/Mode.php";

    private SharedPreferences prefs;
    private SharedPreferences getData;
    private RequestQueue rq;

    private String itemNo = resulttable.reItemNo();
    private TextView tvActTitle;
    private TextView tvActIntro;
    private TextView tvActAdd;
    private TextView tvActTel;

    private Button ex1;
    private Button ex2;
    private Button ex3;
    private Button startTrip;

    private static String exChose = new String();
    private static String exChoseDate = new String();
    private static String exChoseIntro = new String();

    private String nowAccount;
    private String nowGender;
    private String nowAge;
    private String nowCareer = "";
    private String nowRelation = "";
    private static final String TAG = "actdetail";
    private static final int ERROR_DIALOG_REQUEST = 9001;
    private ImageButton imgPhone;
    private Uri phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(itemNo.equals("4")){
            setContentView(R.layout.actdetailforexhibi);
            buildViewForExhib();

        }else{
            setContentView(R.layout.actdetail);
            buildViewForNormal();
        }

        if(isServiceOK()){
            init();
        }

    }

    private void init(){
        ImageButton btnMap = (ImageButton)findViewById(R.id.btMap);
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(actdetail.this , MapActivity.class);
                MapActivity.searchString = resulttable.reActAdd();
                startActivity(intent);
            }
        });
    }

    private void buildViewForNormal(){
        tvActTitle = (TextView)findViewById(R.id.ActName);
        tvActTitle.setText(resulttable.reActTitle());

        tvActIntro = (TextView)findViewById(R.id.ActInfo);
        tvActIntro.setText(resulttable.reActIntro());

        tvActAdd = (TextView)findViewById(R.id.ActAdd);
        tvActAdd.setText(resulttable.reActAdd());

        tvActTel = (TextView)findViewById(R.id.ActTel);
        tvActTel.setText(resulttable.reActTel());

        startTrip = (Button)findViewById(R.id.btStart);
        startTrip.setOnClickListener(tripListener);

        imgPhone = (ImageButton)findViewById(R.id.phone);
        imgPhone.setOnClickListener(phoneListener);
        if(resulttable.reActTel().equals("無")){
            imgPhone.setVisibility(View.INVISIBLE);
        }
    }

    private void buildViewForExhib(){
        tvActTitle = (TextView)findViewById(R.id.ActName);
        tvActTitle.setText(resulttable.reActTitle());

        tvActIntro = (TextView)findViewById(R.id.ActInfo);
        tvActIntro.setText(resulttable.reActIntro());

        tvActAdd = (TextView)findViewById(R.id.ActAdd);
        tvActAdd.setText(resulttable.reActAdd());

        tvActTel = (TextView)findViewById(R.id.ActTel);
        tvActTel.setText(resulttable.reActTel());

        imgPhone = (ImageButton)findViewById(R.id.phone);
        imgPhone.setOnClickListener(phoneListener);

        startTrip = (Button)findViewById(R.id.btStart);
        startTrip.setOnClickListener(tripListener);

        ex1 = (Button)findViewById(R.id.btExhib1);
        ex1.setOnClickListener(exListener);
        ex1.setText(resulttable.reExb(0));

        ex2 = (Button)findViewById(R.id.btExhib2);
        ex2.setOnClickListener(exListener);
        ex2.setText(resulttable.reExb(1));

        ex3 = (Button)findViewById(R.id.btExhib3);
        ex3.setOnClickListener(exListener);
        ex3.setText(resulttable.reExb(2));

        imgPhone = (ImageButton)findViewById(R.id.phone);
        imgPhone.setOnClickListener(phoneListener);

        if(resulttable.reActTel().equals("無")){
            imgPhone.setVisibility(View.INVISIBLE);
        }
    }

    //home button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navhome, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                Intent intentHome = new Intent(actdetail.this, home.class);
                intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentHome);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private View.OnClickListener exListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btExhib1:
                    exChose = resulttable.reExb(0);
                    exChoseDate = resulttable.reExbDate(0);
                    exChoseIntro = resulttable.reExbIntro(0);
                    break;
                case R.id.btExhib2:
                    exChose = resulttable.reExb(1);
                    exChoseDate = resulttable.reExbDate(1);
                    exChoseIntro = resulttable.reExbIntro(1);
                    break;
                case R.id.btExhib3:
                    exChose = resulttable.reExb(2);
                    exChoseDate = resulttable.reExbDate(2);
                    exChoseIntro = resulttable.reExbIntro(2);
            }

            Intent intent = new Intent();
            intent.setClass(actdetail.this,exhibidetail.class);
            startActivity(intent);
        }
    };

    public static String reExName(){
        return exChose;
    }
    public static String reExDate(){
        return exChoseDate;
    }
    public static String reExIntro(){
        return exChoseIntro;
    }

    private View.OnClickListener phoneListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", resulttable.reActTel(), null));
            startActivity(intent);
        }
    };
    private View.OnClickListener tripListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            saveKey();
            mergeData();
            catchAllActivity();
            saveData();

            Intent intent = new Intent();
            intent.setClass(actdetail.this,backcover.class);
            startActivity(intent);

        }
    };
    private void saveKey(){
        prefs = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("clickedTrip_key","1");   //選擇了旅程
        editor.putBoolean("saveRate_key",false);  //未存評分

        editor.commit();
    }

    private void mergeData(){
        getData = getSharedPreferences("myPrefs",Context.MODE_PRIVATE);
        nowAccount = getData.getString("account_key","");
        switch(mode.reType()){
            case "personalUser":
                nowGender = getData.getString("gender_key","");
                nowAge = getData.getString("age_key","");
                nowCareer = getData.getString("career_key","");
                changeToNo();
                break;
            case "personalStranger":
                nowGender = mode.reGender();
                nowAge = mode.reAge();
                nowCareer = mode.reStatus();
            case "Group" :
                nowGender = mode.reGender();
                nowAge = mode.reAge();
                nowRelation = mode.reStatus();
        }
    }

    private void changeToNo(){
        switch(nowAge){
            case "未滿18歲":
                nowAge = "1";
                break;
            case "18-25歲":
                nowAge = "2";
                break;
            case "26-35歲":
                nowAge = "3";
                break;
            case "36-45歲":
                nowAge = "4";
                break;
            case "46-55歲":
                nowAge = "5";
                break;
            case "56-65歲":
                nowAge = "6";
                break;
            case "66歲以上":
                nowAge = "7";
                break;
        }
        switch (nowCareer){
            case "科技人":
                nowCareer = "1";
                break;
            case "工業工程人":
                nowCareer = "2";
                break;
            case "設計人":
                nowCareer = "3";
                break;
            case "美妝美髮人":
                nowCareer = "4";
                break;
            case "醫療人":
                nowCareer = "5";
                break;
            case "餐飲人":
                nowCareer = "6";
                break;
            case "保險人":
                nowCareer = "7";
                break;
            case "商管人":
                nowCareer = "8";
                break;
            case "軍公教":
                nowCareer = "9";
                break;
            case "學生":
                nowCareer = "10";
                break;
            case "其他人":
                nowCareer = "11";
                break;
        }
    }


    public void catchAllActivity() {
        rq = Volley.newRequestQueue(this);
        StringRequest requestMode2 = new StringRequest(Request.Method.POST,uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if( error instanceof com.android.volley.AuthFailureError){
                    Toast.makeText(actdetail.this,"Authentication error",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.NetworkError){
                    Toast.makeText(actdetail.this,"NetworkError",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.ParseError){
                    Toast.makeText(actdetail.this,"ParseError",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.ServerError){
                    Toast.makeText(actdetail.this,"ServerError",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.NoConnectionError){
                    Toast.makeText(actdetail.this,"NoConnectionError",Toast.LENGTH_SHORT).show();
                }
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<>();
                parameters.put("Mode", mode.reType());
                parameters.put("activity_no", resulttable.reActNo());
                parameters.put("user_ID", nowAccount);
                parameters.put("gender", nowGender);
                parameters.put("age_no", nowAge);
                parameters.put("career_no", nowCareer);
                parameters.put("group_type_no", nowRelation);
                return parameters;
            }
        };
        MySingleton.getInstance(this).addToRequestQueue(requestMode2);
    }

    private void saveData(){
        prefs = getSharedPreferences("myPrefs",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString("nowAccount",nowAccount);
        editor.putString("nowGender",nowGender);
        editor.putString("nowAge",nowAge);
        editor.putString("nowCareer",nowCareer);
        editor.putString("nowRelation",nowRelation);
        editor.putString("nowActNo",resulttable.reActNo());
        editor.putString("nowMode",mode.reType());
        editor.putString("nowActTitle",resulttable.reActTitle());
        editor.putString("nowActIntro",resulttable.reActIntro());
        editor.putString("nowActAdd",resulttable.reActAdd());
        editor.putString("nowActTel",resulttable.reActTel());

        editor.commit();
    }


    public boolean isServiceOK(){
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(actdetail.this);
        if (available == ConnectionResult.SUCCESS){
            return true;
        }else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(actdetail.this,available,ERROR_DIALOG_REQUEST);
            dialog.show();
        }else{
            Toast.makeText(this,"您未許可Google Map",Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}

