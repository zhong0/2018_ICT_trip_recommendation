package com.example.zhong0413.thecpt;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
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

import java.util.HashMap;
import java.util.Map;

public class leavesSave extends AppCompatActivity {

    ImageButton imbtBottle;
    ImageButton imbtqrCamrea;
    private SharedPreferences getData;
    private SharedPreferences prefs;
    private RequestQueue rq;
    private int leafNum;
    private String tvLeafNum;
    public String leafID = "";
    String uri = "http://35.170.200.252/db/getLeaf.php";

    private ImageView goldenLeaf;
    private long animationDuration = 1000;
    private String account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leavessave);
        buildView();
    }
    private void buildView() {

        getData = getSharedPreferences("myPrefs",MODE_PRIVATE);
        leafNum = getData.getInt("leafNum",leafNum);
        account = getData.getString("account_key","");

        tvLeafNum = Integer.toString(leafNum);

        imbtBottle = (ImageButton)findViewById(R.id.btBottle);
        imbtqrCamrea = (ImageButton)findViewById(R.id.qrCamera);

        imbtBottle.setOnClickListener(Listener);
        imbtqrCamrea.setOnClickListener(Listener);

        goldenLeaf = (ImageView)findViewById(R.id.imgGoldenLeaf);

    }

    private View.OnClickListener Listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btBottle:
                    Toast.makeText(leavesSave.this,"您剩餘的金葉子還有"+" "+tvLeafNum+ "片",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.qrCamera:
                    Intent intent = new Intent();
                    intent.setClass(leavesSave.this,qrcode.class);
                    startActivityForResult(intent,0);
                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 0){
            if(resultCode == CommonStatusCodes.SUCCESS){
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra("leafID");
                    leafID = barcode.displayValue;
                    giveLeaf();
                }else{

                }
            }
        }else{ }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void giveLeaf(){
        rq = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("3")){
                    Toast.makeText(leavesSave.this,"很抱歉，此店今天的金葉子額度已用完！",Toast.LENGTH_SHORT).show();
                }else if(response.equals("2")){
                    Toast.makeText(leavesSave.this,"您今日已在本店領取過金葉子！",Toast.LENGTH_SHORT).show();
                }else if(response.equals("0")){
                    Toast.makeText(leavesSave.this,"此QR code尚未與本系統合作",Toast.LENGTH_SHORT).show();
                }else if(response.equals("1")){
                    leafNum++;
                    tvLeafNum = Integer.toString(leafNum);
                    goldenLeaf.setVisibility(View.VISIBLE);
                    handleAnimation(goldenLeaf);
                    Toast.makeText(leavesSave.this,"您獲得了一片新葉子，點擊瓶子確認！",Toast.LENGTH_SHORT).show();
                    prefs = getSharedPreferences("myPrefs",MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putInt("leafNum",leafNum);
                    editor.commit();

                }else{

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if( error instanceof com.android.volley.AuthFailureError){
                    Toast.makeText(leavesSave.this,"Authentication error",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.NetworkError){
                    Toast.makeText(leavesSave.this,"請連上網路",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.ParseError){
                    Toast.makeText(leavesSave.this,"ParseError ",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.ServerError){
                    Toast.makeText(leavesSave.this,"ServerError",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.NoConnectionError){
                    Toast.makeText(leavesSave.this,"請連上網路",Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("user_ID", account);
                parameters.put("shop_ID", leafID);
                return parameters;
            }
        };
        MySingleton.getInstance(leavesSave.this).addToRequestQueue(request);
    }

    public void handleAnimation(View view){
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(goldenLeaf,"y",1000f);
        animatorY.setDuration(animationDuration);
        ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(goldenLeaf, view.ALPHA,1.0f,1.0f);
        alphaAnimation.setDuration(animationDuration);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorY,alphaAnimation);
        animatorSet.start();

    }
}
