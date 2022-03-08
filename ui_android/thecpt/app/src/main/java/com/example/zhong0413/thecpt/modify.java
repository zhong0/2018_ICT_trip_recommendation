package com.example.zhong0413.thecpt;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class modify extends AppCompatActivity {
    String Uri = "http://35.170.200.252/db/user.php";
    private SharedPreferences prefs;
    private SharedPreferences getData;

    private Spinner age;
    private Spinner career;
    private String[] allAge = {"未滿18歲","18-25歲","26-35歲","36-45歲","46-55歲","56-65歲","66歲以上"};
    private String[] allCareer = {"科技人","工業工程人","設計人","美妝美髮人","醫療人","餐飲人","保險人","商管人","軍公教","學生","其他人"};


    private Button saveMod;
    private String accountForData = new String();
    private String genderForData = new String();
    private String ageForData = new String();
    private String careerForData = new String();

    private Boolean checkNet = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify);
        buildView();

    }
    private void buildView(){
        saveMod = (Button)findViewById(R.id.btModSave);
        saveMod.setOnClickListener(myListener);

        age = (Spinner)findViewById(R.id.modAgeSpinner);
        ArrayAdapter<String> ageList = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,allAge);
        ageList.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        age.setAdapter(ageList);

        career = (Spinner)findViewById(R.id.modCarSpinner);
        ArrayAdapter<String> careerList = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,allCareer);
        careerList.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        career.setAdapter(careerList);
    }

    @Override
    public void onBackPressed() {

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

    private void getData(){
        getData = getSharedPreferences("myPrefs",Context.MODE_PRIVATE);
        accountForData = getData.getString("account_key","");
        genderForData = getData.getString("gender_key","");
    }

    private void changeData(){
        int selectAge = age.getSelectedItemPosition();
        String uAge = allAge[selectAge];
        int selectCareer = career.getSelectedItemPosition();
        String uCareer = allCareer[selectCareer];

        Log.i("change",uAge + uCareer);
        prefs = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("age_key",uAge);
        editor.putString("career_key",uCareer);

        editor.commit();
    }

    private void ageForData(){
        int selectAge = age.getSelectedItemPosition();
        String uAge = allAge[selectAge];
        switch(uAge){
            case "未滿18歲":
                ageForData = "1";
                break;
            case "18-25歲":
                ageForData = "2";
                break;
            case "26-35歲":
                ageForData = "3";
                break;
            case "36-45歲":
                ageForData = "4";
                break;
            case "46-55歲":
                ageForData = "5";
                break;
            case "56-65歲":
                ageForData = "6";
                break;
            case "66歲以上":
                ageForData = "7";
                break;
        }
    }

    private void careerForData(){
        int selectCareer = career.getSelectedItemPosition();
        String uCareer = allCareer[selectCareer];
        switch(uCareer){
            case "科技人":
                careerForData = "1";
                break;
            case "工業工程人":
                careerForData = "2";
                break;
            case "設計人":
                careerForData = "3";
                break;
            case "美妝美髮人":
                careerForData = "4";
                break;
            case "醫療人":
                careerForData = "5";
                break;
            case "餐飲人":
                careerForData = "6";
                break;
            case "保險人":
                careerForData = "7";
                break;
            case "商管人":
                careerForData = "8";
                break;
            case "軍公教":
                careerForData = "9";
                break;
            case "學生":
                careerForData = "10";

            case "其他人":
                careerForData = "11";
                break;
        }
    }
    private void changeDataforDB(){
        RequestQueue rq = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, Uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if( error instanceof com.android.volley.AuthFailureError){
                    Toast.makeText(modify.this,"Authentication error",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.NetworkError){
                    Toast.makeText(modify.this,"請連上網路",Toast.LENGTH_SHORT).show();
                    checkNet = false;
                }
                if( error instanceof com.android.volley.ParseError){
                    Toast.makeText(modify.this,"ParseError ",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.ServerError){
                    Toast.makeText(modify.this,"ServerError",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.NoConnectionError){
                    Toast.makeText(modify.this,"請連上網路",Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                Log.i("check",accountForData);
                Log.i("check",genderForData);
                Log.i("check",ageForData);
                Log.i("check",careerForData);
                parameters.put("user_ID",accountForData);
                parameters.put("gender",genderForData);
                parameters.put("age_no",ageForData);
                parameters.put("career_no",careerForData);
                return parameters;
            }
        };
        MySingleton.getInstance(modify.this).addToRequestQueue(request);
    }
    private View.OnClickListener myListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(!checkNet){
            }else{
                changeData();
                getData();
                ageForData();
                careerForData();
                changeDataforDB();
            }
            Intent intent = new Intent();
            intent.setClass(modify.this,account.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    };
}
