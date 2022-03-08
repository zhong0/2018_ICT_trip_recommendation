package com.example.zhong0413.thecpt;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

public class coupondetail extends AppCompatActivity {

    private String Uri ="http://35.170.200.252/db/showCoupon_detail.php";
    private String Uri2 ="http://35.170.200.252/db/getCoupon.php";
    private RequestQueue rq;
    private Button buy;
    private String user;
    private SharedPreferences getData;
    private SharedPreferences prefs;
    private int leafNum;
    private String tvLeafLeft;
    private String couponNo;
    private String couponContent;
    private String couponDeadline;
    private int leavesAmount;

    private TextView tvCouponContent;
    private TextView tvCounponDDL;
    private TextView tvCouponAmount;
    private ImageView img;

    private int imgNum;
    private String checkBuy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coupondetail);

        catchDetail();
        getData = getSharedPreferences("myPrefs",MODE_PRIVATE);
        leafNum = getData.getInt("leafNum",36);
        tvLeafLeft = Integer.toString(leafNum);
        user = getData.getString("account_key","");

        Intent intent = getIntent();
        couponNo = intent.getExtras().getString("couponNo");
        Log.i("checkCoupon",couponNo);

        if(couponNo.equals("1")){
            imgNum = 0;
        }else if(couponNo.equals("2")){
            imgNum = 1;
        }

        img = (ImageView)findViewById(R.id.couponDetail);
        img.setImageURI(discountarea.reImage(imgNum));
        tvCouponContent = (TextView)findViewById(R.id.tvCouponContent);
        tvCounponDDL =(TextView)findViewById(R.id.tvCouponDeadline);
        tvCouponAmount = (TextView)findViewById(R.id.tvCouponAmount);

        buy = (Button)findViewById(R.id.btBuy);
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }
    @Override
    public void onBackPressed() {
    }
    private void showDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("確定購買");
        alertDialogBuilder
                .setMessage("確定後將扣除金葉子數量，確定購買？")
                .setCancelable(false)
                .setPositiveButton("確定",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        if(leafNum < leavesAmount){
                            Toast.makeText(coupondetail.this,"您的金葉子數目不足",Toast.LENGTH_SHORT).show();
                        }else {
                            leafNum = leafNum - leavesAmount;
                            tvLeafLeft = Integer.toString(leafNum);
                            buyCoupon();
                        }
                    }
                })
                .setNegativeButton("取消",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                    }
                });

        AlertDialog alert11 = alertDialogBuilder.create();
        alert11.show();
    }

    private void catchDetail(){
        rq = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, Uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseJsonWithGson(response);
                    tvCouponContent.setText(couponContent);
                    tvCounponDDL.setText(couponDeadline);
                    String amount = Integer.toString(leavesAmount) + "片";
                    tvCouponAmount.setText(amount);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if( error instanceof com.android.volley.AuthFailureError){
                    Toast.makeText(coupondetail.this,"Authentication error",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.NetworkError){
                    Toast.makeText(coupondetail.this,"請連上網路",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.ParseError){
                    Toast.makeText(coupondetail.this,"ParseError ",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.ServerError){
                    Toast.makeText(coupondetail.this,"ServerError",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.NoConnectionError){
                    Toast.makeText(coupondetail.this,"請連上網路",Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("cou_no",couponNo);
                return parameters;
            }
        };
        MySingleton.getInstance(coupondetail.this).addToRequestQueue(request);
    }

    public void parseJsonWithGson(String response){
        Gson gson = new Gson();
        List<toJson> toJsonsList = gson.fromJson(response , new TypeToken<List<toJson>>(){}.getType());
        for(toJson Tojson : toJsonsList){
            couponContent = Tojson.cou_content;
            couponDeadline = Tojson.cou_deadline;
            leavesAmount = Tojson.leaves_amount;
        }
    }

    private void buyCoupon(){
        rq = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, Uri2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseJsonWithGsonchk(response);
                if(checkBuy.equals("1")){
                    prefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putInt("leafNum", leafNum);
                    editor.commit();
                    Toast.makeText(coupondetail.this,"購買成功！",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(coupondetail.this,"購買失敗，請再嘗試一次！",Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if( error instanceof com.android.volley.AuthFailureError){
                    Toast.makeText(coupondetail.this,"Authentication error",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.NetworkError){
                    Toast.makeText(coupondetail.this,"請連上網路",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.ParseError){
                    Toast.makeText(coupondetail.this,"ParseError ",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.ServerError){
                    Toast.makeText(coupondetail.this,"ServerError",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.NoConnectionError){
                    Toast.makeText(coupondetail.this,"請連上網路",Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                Log.i("checkCouponDetail",user+" " + couponNo + " " + tvLeafLeft);
                parameters.put("user_ID",user);
                parameters.put("cou_no",couponNo);
                parameters.put("leafLeft",tvLeafLeft);
                return parameters;
            }
        };
        MySingleton.getInstance(coupondetail.this).addToRequestQueue(request);
    }
    public void parseJsonWithGsonchk(String response){
        Gson gson = new Gson();
        List<toJson> toJsonsList = gson.fromJson(response , new TypeToken<List<toJson>>(){}.getType());
        for(toJson Tojson : toJsonsList){
             checkBuy = Tojson.check_buy;
        }
    }
}
