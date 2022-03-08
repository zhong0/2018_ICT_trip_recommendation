package com.example.zhong0413.thecpt;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class account extends AppCompatActivity {
    String Uri ="http://35.170.200.252/db/Login.php";

    private TextView tvAccount;
    private TextView tvGender;
    private TextView tvAge;
    private TextView tvCareer;

    private String account = new String();
    private String gender = new String();
    private String age = new String();
    private String career = new String();

    private SharedPreferences getData;
    private SharedPreferences prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account);

        buildView();
    }
    @Override
    public void onBackPressed() {

    }
    private void buildView(){
        final Button logOut;
        final Button modify;

        tvAccount = (TextView) findViewById(R.id.accAcc);
        tvGender = (TextView) findViewById(R.id.accGender);
        tvAge = (TextView) findViewById(R.id.accAge);
        tvCareer = (TextView)findViewById(R.id.accCar);

        logOut = (Button)findViewById(R.id.btLogout);
        logOut.setOnClickListener(myListener);

        modify = (Button)findViewById(R.id.btModify);
        modify.setOnClickListener(myListener);

        setInfo();
    }


    private void setInfo(){
        getData = getSharedPreferences("myPrefs",Context.MODE_PRIVATE);
        account = getData.getString("account_key","");
        gender = getData.getString("gender_key","");
        age = getData.getString("age_key","");
        career = getData.getString("career_key","");

        tvAccount.setText(account);
        if(gender.equals("male")){
            tvGender.setText("男性");
        }else{
            tvGender.setText("女性");
        }
        tvAge.setText(age);
        tvCareer.setText(career);
    }

    private void clearData(){
        prefs = getSharedPreferences("myPrefs",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString("account_key","");
        editor.putString("gender_key","");
        editor.putString("age_key","");
        editor.putString("career_key","");
        editor.putString("clickedTrip_key","0");
        editor.putBoolean("saveRate_key",true);
        editor.putBoolean("logged",false);

        editor.commit();
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

    private View.OnClickListener myListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btLogout:  //登出
                    clearData();
                    Intent intentL = new Intent();
                    intentL.setClass(account.this,login.class);
                    intentL.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intentL);
                    break;
                case R.id.btModify:  //修改資料
                    Intent intentM = new Intent();
                    intentM.setClass(account.this,modify.class);
                    intentM.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intentM);
                    break;
            }
        }
    };
}
