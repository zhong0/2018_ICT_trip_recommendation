package com.example.zhong0413.thecpt;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

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

public class choice extends AppCompatActivity {

    String Uri = "http://35.170.200.252/db/rate_table.php";

    private ToggleButton sport;
    private ToggleButton play;
    private ToggleButton quiet;
    private ToggleButton nothing;
    private ToggleButton walk;

    private static String chooseActType = "";
    public RequestQueue requestQueue;

    private static String[] activityName = {"", "", "", "", "", "", "", "", "", ""};
    private static String[] activityNo = {"", "", "", "", "", "", "", "", "", ""};
    private static String[] ItemName = {"", "", "", "", "", "", "", "", "", ""};
    private static String[] ItemNo = {"", "", "", "", "", "", "", "", "", ""};

    private static String type;
    private static String account;
    private static String gender;
    private static String age;
    private static String status;

    private static boolean in = false;

    private Button searchTrip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choice);

        buildView();
    }
    private void buildView(){
        for(int i=0 ; i<10;i++){
            resulttable.completelyActName[i] = "";
        }
        type = mode.reType();
        account = mode.reAccount();
        gender = mode.reGender();
        age = mode.reAge();
        status = mode.reStatus();
        Log.i("checkALL",gender+" "+status+" "+age+" "+chooseActType);
        chooseActType = "";
        searchTrip = (Button)findViewById(R.id.btSearchTrip);
        searchTrip.setOnClickListener(myListener);

        sport = (ToggleButton)findViewById(R.id.tgbtSport);
        sport.setOnCheckedChangeListener(sportToggle);

        play = (ToggleButton)findViewById(R.id.tgbtPlay);
        play.setOnCheckedChangeListener(playToggle);

        quiet = (ToggleButton)findViewById(R.id.tgbtExhib);
        quiet.setOnCheckedChangeListener(quietToggle);

        nothing = (ToggleButton)findViewById(R.id.tgbtNothing);
        nothing.setOnCheckedChangeListener(nothingToggle);

        walk = (ToggleButton)findViewById(R.id.tgbtWalk);
        walk.setOnCheckedChangeListener(walkToggle);
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

    private CompoundButton.OnCheckedChangeListener sportToggle = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        }
    };
    private CompoundButton.OnCheckedChangeListener playToggle = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        }
    };
    private CompoundButton.OnCheckedChangeListener quietToggle = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        }
    };

    private CompoundButton.OnCheckedChangeListener nothingToggle = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        }
    };

    private CompoundButton.OnCheckedChangeListener walkToggle = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        }
    };


    public void catchAllActivity() {
        Log.i("check","checkAct");
        requestQueue = Volley.newRequestQueue(this);
        StringRequest requestMode2 = new StringRequest(Request.Method.POST,Uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseJsonWithGson(response);

                Intent intent = new Intent();
                intent.setClass(choice.this,resulttable.class);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if( error instanceof com.android.volley.AuthFailureError){
                    Toast.makeText(choice.this,"Authentication error",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.NetworkError){
                    Toast.makeText(choice.this,"請連上網路",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.ParseError){
                    Toast.makeText(choice.this,"ParseError ",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.ServerError){
                    Toast.makeText(choice.this,"ServerError",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.NoConnectionError){
                    Toast.makeText(choice.this,"請連上網路",Toast.LENGTH_SHORT).show();
                }
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<>();
                Log.i("checkALLInType",type);
                parameters.put("Mode", type);
                switch (type) {
                    case "personalUser":
                        parameters.put("user_ID", account);
                        parameters.put("ActTypeForData", chooseActType);
                        break;

                    case "personalStranger":
                        Log.i("checkALLIn",gender+" "+status+" "+age+" "+chooseActType);
                        parameters.put("gender" , gender);
                        parameters.put("career_no" , status);
                        parameters.put("age_no", age);
                        parameters.put("ActTypeForData", chooseActType);
                        break;

                    case "Group":
                        Log.i("checkALLIn",gender+" "+status+" "+age+" "+chooseActType);
                        parameters.put("gender", gender);
                        parameters.put("age_no", age);
                        parameters.put("group_type_no", status);
                        parameters.put("ActTypeForData", chooseActType);
                        break;
                }
                return parameters;
            }
        };
        MySingleton.getInstance(this).addToRequestQueue(requestMode2);
    }
    public void parseJsonWithGson(String response){
        Gson gson = new Gson();
        List<toJson> toJsonsList = gson.fromJson(response , new TypeToken<List<toJson>>(){}.getType());
        int count = 0;
        if(toJsonsList!= null && toJsonsList.size()>0) {
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

    private void typeString(){
        if(sport.isChecked()){
            chooseActType += "1";
        }
        if(play.isChecked()){
            chooseActType += "2";
        }
        if(quiet.isChecked()){
            chooseActType += "3";
        }
        if(walk.isChecked()){
            chooseActType += "4";
        }
        if(nothing.isChecked()){
            chooseActType += "5";
        }
        if(chooseActType.equals("")){
            chooseActType = "12345";
        }
    }

    private View.OnClickListener myListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            typeString();
            catchAllActivity();

            final ProgressDialog dialog= ProgressDialog.show(choice.this,"搜索中", "Exploring ...",true);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                        dialog.dismiss();
                    }
                    catch(InterruptedException ex){
                        ex.printStackTrace();
                    }
                }
            }).start();
        }
    };

    public static String reActName(int n){
        return activityName[n] + ItemName[n];
    }
    public static String reActNo(int n){
        return activityNo[n];
    }
    public static String reItemNo(int n){
        return ItemNo[n];
    }
    public static String reChooseAct() {
        return chooseActType;
    }
}

