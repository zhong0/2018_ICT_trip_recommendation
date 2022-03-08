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
import android.widget.Button;
import android.widget.EditText;
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

public class login extends AppCompatActivity {
    String Uri = "http://35.170.200.252/db/Sign_in.php";

    private RequestQueue rq;
    private SharedPreferences prefs;

    private Button login;
    private Button register;
    private Button forget;
    private EditText edAccount;
    private EditText edPassword;

    private String account = new String();
    private String password = new String();
    private String gender = new String();
    private String age = new String();
    private String career = new String();
    private String judge = new String();
    private int leafNum;

    private Boolean checkNet = true;
    public static Boolean needRefresh = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        buildView();
    }

    private void buildView(){
        edAccount = (EditText)findViewById(R.id.loginAcc);
        edPassword = (EditText) findViewById(R.id.loginPassword);

        login = (Button)findViewById(R.id.btLogin);
        register = (Button)findViewById(R.id.btLoginReg);
        forget = (Button)findViewById(R.id.btForget);

        login.setOnClickListener(myListener);
        register.setOnClickListener(myListener);
        forget.setOnClickListener(myListener);
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

    private View.OnClickListener myListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btLogin:  //登入
                    account = edAccount.getText().toString();
                    password = edPassword.getText().toString();
                    Toast.makeText(login.this,"請稍候",Toast.LENGTH_SHORT).show();
                    checkAccount();
                    break;
                case R.id.btLoginReg:  //註冊
                    Intent intentR = new Intent();
                    intentR.setClass(login.this,register.class);
                    intentR.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intentR);
                    break;
                case R.id.btForget:
                    Intent intentD = new Intent();
                    intentD.setClass(login.this,forgetdialog.class);
                    startActivity(intentD);
                    break;
            }
        }
    };


    private void checkAccount(){
        rq = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, Uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseJsonWithGson(response);
                if(judge.equals("0")){
                    Toast.makeText(login.this,"此帳號尚未註冊",Toast.LENGTH_SHORT).show();
                }else if(judge.equals("2")){
                    Toast.makeText(login.this,"密碼錯誤",Toast.LENGTH_SHORT).show();
                }else{
                    setData();
                    needRefresh = true;
                    Intent intent = new Intent(login.this,account.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if( error instanceof com.android.volley.AuthFailureError){
                    Toast.makeText(login.this,"Authentication error",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.NetworkError){
                    Toast.makeText(login.this,"請連上網路",Toast.LENGTH_SHORT).show();
                    checkNet = false;
                }
                if( error instanceof com.android.volley.ParseError){
                    Toast.makeText(login.this,"ParseError ",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.ServerError){
                    Toast.makeText(login.this,"ServerError",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.NoConnectionError){
                    Toast.makeText(login.this,"請連上網路",Toast.LENGTH_SHORT).show();
                    checkNet = false;
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("user_ID",account);
                parameters.put("password",password);
                return parameters;
            }
        };
        MySingleton.getInstance(login.this).addToRequestQueue(request);
    }

    public void parseJsonWithGson(String response){
        Gson gson = new Gson();
        List<toJson> toJsonsList = gson.fromJson(response , new TypeToken<List<toJson>>(){}.getType());
        for(toJson Tojson : toJsonsList){
            judge = Tojson.judgement;
            gender=Tojson.OldUserGender;
            age = Tojson.OldUserAge;
            career = Tojson.OldUserCareer;
            leafNum = Tojson.leaf_num;
        }
    }


    private void setData(){
        prefs = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("account_key",account);
        editor.putString("gender_key",gender);
        editor.putString("age_key",age);
        editor.putString("career_key",career);
        editor.putBoolean("logged",true);
        editor.putInt("leafNum",leafNum);

        editor.commit();
    }
}
