package com.example.zhong0413.thecpt;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
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

public class register extends AppCompatActivity {
    String Uri = "http://35.170.200.252/db/user_register.php";

    RequestQueue rq;
    private SharedPreferences prefs;
    private ToggleButton man;
    private ToggleButton woman;

    private Spinner age;
    private Spinner career;
    private String[] allAge = {"未滿18歲","18-25歲","26-35歲","36-45歲","46-55歲","56-65歲","66歲以上"};
    private String[] allCareer = {"科技人","工業工程人","設計人","美妝美髮人","醫療人","餐飲人","保險人","商管人","軍公教","學生","其他人"};
    private String gender = "male";

    private Button checkAcc;
    private Button btRegister;
    private EditText account;
    private String uAccount = new String();
    private EditText password;
    private String psword = new String();
    private EditText passwordAgain;
    private String pswordag = new String();

    private EditText forgetQ;
    private String question = "";
    private EditText forgetA;
    private String answer = "";

    private boolean ver = false;  //一般驗證
    private boolean alreadyVerified = false;  //怕有87驗證後再重輸入帳號
    private boolean doubleCheck = false; //驗證有無符合規格

    private String ageForData = new String();
    private String careerForData = new String();

    private String uAge = new String() ;
    private String uCareer = new String();

    private String checkAccount;
    private int leafNum = 36;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        buildView();
    }
    private void buildView(){
        account = (EditText)findViewById(R.id.regAcc);
        checkAcc = (Button)findViewById(R.id.btChkAcc);
        checkAcc.setOnClickListener(checkListener);

        password = (EditText)findViewById(R.id.regPassword);
        passwordAgain = (EditText)findViewById(R.id.regPasswordAg);

        man = (ToggleButton)findViewById(R.id.regTBMan);
        man.setOnCheckedChangeListener(manToggle);

        woman = (ToggleButton) findViewById(R.id.regTBWoman);
        woman.setOnCheckedChangeListener(womanToggle);

        age = (Spinner)findViewById(R.id.regAgeSpinner);
        ArrayAdapter<String> ageList = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,allAge);
        ageList.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        age.setAdapter(ageList);

        career = (Spinner)findViewById(R.id.regCarSpinner);
        ArrayAdapter<String> careerList = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,allCareer);
        careerList.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        career.setAdapter(careerList);

        forgetQ = (EditText)findViewById(R.id.forgetQue);
        forgetA = (EditText)findViewById(R.id.forgetAns);

        btRegister = (Button)findViewById(R.id.btReg);
        btRegister.setOnClickListener(regListener);

        prefs = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
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

    public void sureAccount(){
        rq = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, Uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseJsonWithGsonCheckAccount(response);
                if(checkAccount.equals("alreadyhad")) {
                    ver = false;
                    Toast.makeText(register.this, "此帳號已被註冊過", Toast.LENGTH_SHORT).show();
                }else{
                    showMsg();
                    if(doubleCheck) {
                        ver = true;
                        Toast.makeText(register.this, "驗證成功！", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if( error instanceof com.android.volley.AuthFailureError){
                    Toast.makeText(register.this,"Authentication error",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.NetworkError){
                    Toast.makeText(register.this,"請連上網路",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.ParseError){
                    Toast.makeText(register.this,"ParseError ",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.ServerError){
                    Toast.makeText(register.this,"ServerError",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.NoConnectionError){
                    Toast.makeText(register.this,"請連上網路",Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("user_ID", uAccount);
                return parameters;
            }
        };
        MySingleton.getInstance(register.this).addToRequestQueue(request);
    }
    public void parseJsonWithGsonCheckAccount(String response){
        Gson gson = new Gson();
        List<toJson> toJsonsList = gson.fromJson(response , new TypeToken<List<toJson>>(){}.getType());
        for(toJson Tojson : toJsonsList){
            checkAccount = Tojson.checkAccount;
        }
    }
    private void showMsg(){
        if(account.equals("")){
            ver = false;
            doubleCheck = false;
            Toast.makeText(register.this,"帳號尚未輸入",Toast.LENGTH_SHORT).show();
        }else if(account.length()<4){
            ver = false;
            doubleCheck = false;
            Toast.makeText(register.this, "帳號長度過短", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkPassword(){
        psword = password.getText().toString();
        pswordag = passwordAgain.getText().toString();
        if(psword.length()<4){
            Toast.makeText(register.this,"密碼過短",Toast.LENGTH_SHORT).show();
            return false;
        }else if(!psword.equals(pswordag)){
            Toast.makeText(register.this,"兩次輸入密碼不同",Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
        }
    }
    private boolean checkForgetQuestion(){
        question = forgetQ.getText().toString();
        if(question.equals("")){
            Toast.makeText(register.this,"尚未輸入防忘密碼問題！",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean checkForgetAnswer(){
        answer = forgetA.getText().toString();
        if(answer.equals("")){
            Toast.makeText(register.this,"尚未輸入防忘密碼答案！",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void ageToData(){
        int selectAge = age.getSelectedItemPosition();
        uAge = allAge[selectAge];
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

    private void careerToData(){
        int selectCareer = career.getSelectedItemPosition();
        uCareer = allCareer[selectCareer];
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
                break;
            case "其他人":
                careerForData = "11";
                break;
        }
    }

    private void saveToApp(){
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString("account_key",uAccount);
        editor.putString("gender_key",gender);
        editor.putString("age_key",uAge);
        editor.putString("career_key",uCareer);
        editor.putBoolean("logged",true);
        editor.putInt("leafNum",leafNum);

        editor.commit();
    }
    private void connectDB(){
        rq = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, Uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if( error instanceof com.android.volley.AuthFailureError){
                    Toast.makeText(register.this,"Authentication error",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.NetworkError){
                    Toast.makeText(register.this,"請連上網路",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.ParseError){
                    Toast.makeText(register.this,"ParseError ",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.ServerError){
                    Toast.makeText(register.this,"ServerError",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.NoConnectionError){
                    Toast.makeText(register.this,"請連上網路",Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("user_ID", uAccount);
                parameters.put("password", psword);
                parameters.put("gender", gender);
                parameters.put("age_no", ageForData);
                parameters.put("career_no", careerForData);
                parameters.put("question",question);
                parameters.put("forgot_answer",answer);
                return parameters;
            }
        };
        MySingleton.getInstance(register.this).addToRequestQueue(request);
    }

    private CompoundButton.OnCheckedChangeListener manToggle = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked){
                gender = "male";
                woman.setChecked(false);
            }else if(isChecked == false && woman.isChecked()==false) {
                man.setChecked(true);
            }
        }
    };
    private CompoundButton.OnCheckedChangeListener womanToggle = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked){
                gender = "female";
                man.setChecked(false);
            }else if(isChecked == false && man.isChecked()==false) {
                woman.setChecked(true);
            }
        }
    };

    private View.OnClickListener checkListener = new View.OnClickListener() {   //驗證
        @Override
        public void onClick(View v) {
            doubleCheck = true;
            Toast.makeText(register.this,"驗證中..",Toast.LENGTH_SHORT).show();

            uAccount = account.getText().toString();
            sureAccount();
            alreadyVerified = true;
        }
    };


    private View.OnClickListener regListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {    //註冊
            String newAcc = account.getText().toString();
            if(!ver){
                Toast.makeText(register.this,"帳號未通過驗證！",Toast.LENGTH_SHORT).show();
                ver = true;
                alreadyVerified = false;
            }else if(!newAcc.equals(uAccount) || !alreadyVerified){
                Toast.makeText(register.this,"此帳號尚未驗證！",Toast.LENGTH_SHORT).show();
                alreadyVerified = false;
            }else if(!checkPassword()){
            }else if(!checkForgetQuestion()){
            }else if(!checkForgetAnswer()){
            }else{
                ageToData();
                careerToData();
                saveToApp();

                connectDB();

                Intent intent = new Intent();
                intent.setClass(register.this,account.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        }
    };
}

