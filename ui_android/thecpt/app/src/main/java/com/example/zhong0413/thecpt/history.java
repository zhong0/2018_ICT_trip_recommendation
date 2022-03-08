package com.example.zhong0413.thecpt;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RatingBar;
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

public class history extends AppCompatActivity {
    private RequestQueue rq;
    String Uri = "http://35.170.200.252/db/history.php";

    private static final int ERROR_DIALOG_REQUEST = 9001;
    private SharedPreferences prefs;
    private SharedPreferences getData;

    private String account;
    private String gender;
    private String age;
    private String career;
    private String actNo;
    private String mode;
    private String group;
    private String actTitle;
    private String actIntro;
    private String actAdd;
    private String actTel;
    private boolean savedRate;

    private TextView tvActTitle;
    private TextView tvActIntro;
    private TextView tvActAdd;
    private TextView tvActTel;

    private RatingBar rbRating;
    private Button svRating;

    private String rate;
    private ImageButton imgPhone;
    public static Boolean hadHistory = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getAllData();

        if(savedRate){
            setContentView(R.layout.nohistory);
        }else{
            setContentView(R.layout.history);
            buildView();
            if(isServiceOK()){
                init();
            }
        }
    }

    @Override
    public void onBackPressed() {
    }

    private void init(){
        ImageButton btnMap = (ImageButton)findViewById(R.id.btMap);
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hadHistory = true;
                MapActivity.searchString = actAdd;
                Intent intent = new Intent(history.this , MapActivity.class);
                startActivity(intent);
            }
        });
    }
    private void getAllData(){
        getData = getSharedPreferences("myPrefs",Context.MODE_PRIVATE);
        account = getData.getString("nowAccount","");
        gender = getData.getString("nowGender","");
        age = getData.getString("nowAge","");
        career = getData.getString("nowCareer","");
        group = getData.getString("nowRelation","");
        actNo = getData.getString("nowActNo","");
        mode = getData.getString("nowMode","");
        actTitle = getData.getString("nowActTitle","");
        actIntro = getData.getString("nowActIntro","");
        actAdd = getData.getString("nowActAdd","");
        actTel = getData.getString("nowActTel","");

        savedRate = getData.getBoolean("saveRate_key",true);

    }

    private void buildView(){
        tvActTitle = (TextView)findViewById(R.id.ActName);
        tvActTitle.setText(actTitle);

        tvActIntro = (TextView)findViewById(R.id.ActInfo);
        tvActIntro.setText(actIntro);

        tvActAdd = (TextView)findViewById(R.id.ActAdd);
        tvActAdd.setText(actAdd);

        tvActTel = (TextView)findViewById(R.id.ActTel);
        tvActTel.setText(actTel);

        rbRating = (RatingBar)findViewById(R.id.ratingAct);
        rbRating.setOnRatingBarChangeListener(rbListener);

        svRating = (Button)findViewById(R.id.btRateSave);
        svRating.setOnClickListener(btListener);

        imgPhone = (ImageButton)findViewById(R.id.phone);
        imgPhone.setOnClickListener(phoneListener);
        if(actTel.equals("無")){
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
                Intent intentHome = new Intent(this, home.class);
                intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentHome);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private RatingBar.OnRatingBarChangeListener rbListener = new RatingBar.OnRatingBarChangeListener() {
        @Override
        public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
            rate = String.valueOf(rating);
        }
    };

    private View.OnClickListener phoneListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_DIAL, android.net.Uri.fromParts("tel", actTel, null));
            startActivity(intent);
        }
    };
    private View.OnClickListener btListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            changeRateSaved();
            catchAllActivity();
            prefs = getSharedPreferences("myPrefs",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("clickedTrip_key","0");
            editor.commit();

            Toast.makeText(history.this,"謝謝您的評分！",Toast.LENGTH_SHORT).show();
        }
    };
    private void changeRateSaved(){
        prefs = getSharedPreferences("myPrefs",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("saveRate_key",true);
        editor.commit();

        rbRating.setVisibility(View.INVISIBLE);
        svRating.setVisibility(View.INVISIBLE);
        login.needRefresh = true;
    }

    public void catchAllActivity() {
        rq = Volley.newRequestQueue(this);
        StringRequest requestMode2 = new StringRequest(Request.Method.POST,Uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if( error instanceof com.android.volley.AuthFailureError){
                    Toast.makeText(history.this,"Authentication error",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.NetworkError){
                    Toast.makeText(history.this,"NetworkError",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.ParseError){
                    Toast.makeText(history.this,"ParseError ",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.ServerError){
                    Toast.makeText(history.this,"ServerError",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.NoConnectionError){
                    Toast.makeText(history.this,"NoConnectionError",Toast.LENGTH_SHORT).show();
                }
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<>();
                parameters.put("user_ID",account);
                parameters.put("gender",gender);
                parameters.put("age_no",age);
                parameters.put("career_no",career);
                parameters.put("activity_no",actNo);
                parameters.put("group_type",group);
                parameters.put("Mode",mode);
                parameters.put("rate", rate);
                return parameters;
            }
        };
        MySingleton.getInstance(this).addToRequestQueue(requestMode2);
    }
    public boolean isServiceOK(){
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(history.this);
        if (available == ConnectionResult.SUCCESS){
            return true;
        }else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(history.this,available,ERROR_DIALOG_REQUEST);
            dialog.show();
        }else{
            Toast.makeText(this,"您未許可Google Map",Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
