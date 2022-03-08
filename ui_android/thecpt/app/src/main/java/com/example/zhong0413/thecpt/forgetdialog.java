package com.example.zhong0413.thecpt;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class forgetdialog extends AppCompatActivity {
    String UriCatchQ = "http://35.170.200.252/db/forgotPassword.php";
    String UriCatchPsw = "http://35.170.200.252/db/forgotPassword_confirm.php";

    RequestQueue rq;
    private TextView tvAcc;
    private EditText account;
    private TextView question;
    private EditText answer;
    private TextView yourPassword;
    private TextView password;

    private Button sure;
    private Button cancel;
    private Button next;
    private Button nextnext;

    private String textAccount = "";
    private String forgotQ = "";
    private String forgotA = "";
    private String userPassword = "";
    private String check = "";
    private String check2 = "";
    private Boolean checkNet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgetdialog);

        buildView();
    }

    private void buildView(){
        textAccount = "";
        forgotQ = "";
        forgotA = "";
        userPassword = "";

        tvAcc = (TextView)findViewById(R.id.tvShowForgot);
        account = (EditText)findViewById(R.id.edAccount);

        question = (TextView)findViewById(R.id.forgotQ);
        answer = (EditText) findViewById(R.id.forgotA);

        yourPassword = (TextView)findViewById(R.id.tvShowYourPsw);
        password = (TextView)findViewById(R.id.pswd);

        next = (Button)findViewById(R.id.btNext);
        next.setOnClickListener(nextListener);

        nextnext = (Button)findViewById(R.id.btNext2);
        nextnext.setOnClickListener(nextnextListener);

        sure = (Button)findViewById(R.id.sure);
        sure.setOnClickListener(sureListener);

        cancel = (Button)findViewById(R.id.cancel);
        cancel.setOnClickListener(cancelListener);
    }

    private View.OnClickListener nextListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            textAccount = account.getText().toString();
            if(textAccount.equals("")){
                Toast.makeText(forgetdialog.this,"請輸入帳號",Toast.LENGTH_SHORT).show();
            }else{
                catchQuestion();
                final ProgressDialog dialog= ProgressDialog.show(forgetdialog.this,"驗證中", "Please wait ...",true);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                            dialog.dismiss();
                        }
                        catch(InterruptedException ex){
                            ex.printStackTrace();
                        }
                    }
                }).start();
                /*final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                    }
                }, 3000);*/

            }
        }
    };
    private View.OnClickListener nextnextListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            forgotA = answer.getText().toString();
            if(forgotA.equals("")){
                Toast.makeText(forgetdialog.this,"請輸入答案",Toast.LENGTH_SHORT).show();
            }else{
                catchPassword();

                final ProgressDialog dialog= ProgressDialog.show(forgetdialog.this,"確認答案中", "Please wait ...",true);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                            dialog.dismiss();
                        }
                        catch(InterruptedException ex){
                            ex.printStackTrace();
                        }
                    }
                }).start();
            }
        }
    };
    private View.OnClickListener sureListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };
    private View.OnClickListener cancelListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    private void catchQuestion(){
        rq = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, UriCatchQ, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseJsonWithGsonForQuestion(response);
                if (check.equals("0")) {
                    Toast toast = Toast.makeText(forgetdialog.this, "查無此帳號", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER | Gravity.TOP,0,900);
                    toast.show();
                }else{
                    tvAcc.setVisibility(View.INVISIBLE);
                    account.setVisibility(View.INVISIBLE);
                    next.setVisibility(View.INVISIBLE);

                    question.setVisibility(View.VISIBLE);
                    answer.setVisibility(View.VISIBLE);
                    question.setText(forgotQ);
                    nextnext.setVisibility(View.VISIBLE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if( error instanceof com.android.volley.AuthFailureError){
                    Toast.makeText(forgetdialog.this,"Authentication error",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.NetworkError){
                    Toast.makeText(forgetdialog.this,"請連上網路",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.ParseError){
                    Toast.makeText(forgetdialog.this,"ParseError ",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.ServerError){
                    Toast.makeText(forgetdialog.this,"ServerError",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.NoConnectionError){
                    Toast.makeText(forgetdialog.this,"請連上網路",Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("user_ID",textAccount);
                return parameters;
            }
        };
        MySingleton.getInstance(forgetdialog.this).addToRequestQueue(request);
    }

    public void parseJsonWithGsonForQuestion(String response){
        Gson gson = new Gson();
        List<toJson> toJsonsList = gson.fromJson(response , new TypeToken<List<toJson>>(){}.getType());
        for(toJson Tojson : toJsonsList){
            forgotQ = Tojson.question;
            check = Tojson.reply;
        }
    }

    private void catchPassword(){
        rq = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, UriCatchPsw, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseJsonWithGsonForPassword(response);
                if (check2.equals("0")) {
                    Toast toast = Toast.makeText(forgetdialog.this, "答案錯誤", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER | Gravity.TOP,0 ,900);
                    toast.show();
                }else {
                    question.setVisibility(View.INVISIBLE);
                    answer.setVisibility(View.INVISIBLE);
                    nextnext.setVisibility(View.INVISIBLE);
                    password.setText(userPassword);
                    yourPassword.setVisibility(View.VISIBLE);
                    password.setVisibility(View.VISIBLE);
                    sure.setVisibility(View.VISIBLE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if( error instanceof com.android.volley.AuthFailureError){
                    Toast.makeText(forgetdialog.this,"Authentication error",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.NetworkError){
                    Toast.makeText(forgetdialog.this,"請連上網路",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.ParseError){
                    Toast.makeText(forgetdialog.this,"ParseError ",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.ServerError){
                    Toast.makeText(forgetdialog.this,"ServerError",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.NoConnectionError){
                    Toast.makeText(forgetdialog.this,"請連上網路",Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("user_ID",textAccount);
                parameters.put("forgot_answer",forgotA);
                return parameters;
            }
        };
        MySingleton.getInstance(forgetdialog.this).addToRequestQueue(request);
    }

    public void parseJsonWithGsonForPassword(String response){
        Gson gson = new Gson();
        List<toJson> toJsonsList = gson.fromJson(response , new TypeToken<List<toJson>>(){}.getType());
        for(toJson Tojson : toJsonsList){
            userPassword = Tojson.password;
            check2 = Tojson.reply;
        }
    }
}

