package com.example.zhong0413.thecpt;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class mydicount extends AppCompatActivity {
    private String UriDelete ="http://35.170.200.252/db/consumeCoupon.php";
    List<myDiscountList> mDiscount;
    RequestQueue rq;
    private SharedPreferences getData;
    private String user;
    private static String shopID = "";
    private static String consumed = "";
    private static int[] position = new int[100];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mydicount);

        getData = getSharedPreferences("myPrefs",MODE_PRIVATE);
        user =getData.getString("account_key","");


        showMyCoupon();
        RecyclerView myrv = (RecyclerView) findViewById(R.id.myrecyclerview);
        recycleViewMyDiscount myAdapter = new recycleViewMyDiscount(this, mDiscount);
        myrv.setLayoutManager(new GridLayoutManager(this, 2));
        myrv.setAdapter(myAdapter);
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 0){
            if(resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra("shopID");
                    shopID = barcode.displayValue;
                    consumeCoupon();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void consumeCoupon(){
        rq = Volley.newRequestQueue(mydicount.this);
        StringRequest request = new StringRequest(Request.Method.POST, UriDelete, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseJsonWithGson(response);
                if (consumed.equals("0")) {
                    Toast.makeText(mydicount.this, "兌換失敗，請再刷一次QRCODE！", Toast.LENGTH_SHORT).show();
                    mydicount.modShopID("");
                } else {
                    mydicount.modShopID("");
                    Toast.makeText(mydicount.this, "兌換成功！", Toast.LENGTH_SHORT).show();
                    startActivity(getIntent());
                    finish();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if( error instanceof com.android.volley.AuthFailureError){
                    Toast.makeText(mydicount.this,"Authentication error",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.NetworkError){
                    Toast.makeText(mydicount.this,"請連上網路",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.ParseError){
                    Toast.makeText(mydicount.this,"ParseError ",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.ServerError){
                    Toast.makeText(mydicount.this,"ServerError",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.NoConnectionError){
                    Toast.makeText(mydicount.this,"請連上網路",Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("user_ID",user);
                parameters.put("cou_ID",recycleViewMyDiscount.getCoupID());
                parameters.put("shop_ID",shopID);
                return parameters;
            }
        };
        MySingleton.getInstance(mydicount.this).addToRequestQueue(request);
    }
    private void parseJsonWithGson(String response){
        Gson gson = new Gson();
        List<toJson> toJsonsList = gson.fromJson(response , new TypeToken<List<toJson>>(){}.getType());
        for(toJson Tojson : toJsonsList){
            consumed = Tojson.check_consume;
        }
    }

    private void showMyCoupon(){
        mDiscount = new ArrayList<>();
        for(int i= 0; i<discountarea.getMyCouponNum() ; i++){
            int no = discountarea.getCoupNo(i);
            Log.i("checkShow",Integer.toString(no));
            mDiscount.add(new myDiscountList(discountarea.reImage(no),discountarea.getPlaceName(i),discountarea.getCoupIntro(i),discountarea.getCoupDDL(i),discountarea.getMyCoupNo(i),discountarea.getCoupID(i)));
        }
    }


    public static String getShopID(){
        return shopID;
    }
    public static void modShopID(String n){
        shopID = "";
    }
    public static String getConsumed(){
        return consumed;
    }
    public static void setConsumed(String n){
        consumed = n;
    }
}
