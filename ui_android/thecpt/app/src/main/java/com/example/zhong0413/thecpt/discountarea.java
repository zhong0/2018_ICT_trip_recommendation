package com.example.zhong0413.thecpt;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.CharacterPickerDialog;
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
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class discountarea extends AppCompatActivity {

    List<couponlist> lstCoupon;
    private String Urishow ="http://35.170.200.252/db/showMyCoupon_info.php";
    private RequestQueue rq;
    private static String user;

    private Context mContext;
    private Activity mActivity;
    private CoordinatorLayout mCLayout;

    private TextView showLeafNum;
    private Button btMyDiscount;
    private SharedPreferences getData;
    private int leafNum;
    private String tvLeafNum;

    private static int num;

    private static String[] placeName = new String[100];
    private static String[] coupIntro = new String[100];
    private static String[] coupDDL = new String[100];
    private static String[] coupNo = new String[100];
    private static String[] coupID = new String[100];
    private static String[] imageNo= {"1","2"};
    private static Bitmap getImage;
    private static Uri[] uri = new Uri[2];
    private Uri tmp;
    private int imageNum = -1;
    private int fileNum =1;
    private int coupNum = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.discountarea);
        imageNum = -1;
        fileNum = 1;


        catchImage();

        mContext = getApplicationContext();
        mActivity = discountarea.this;

        showLeafNum = (TextView)findViewById(R.id.tvLeafNum);
        getData = getSharedPreferences("myPrefs", MODE_PRIVATE);

        user = getData.getString("account_key","");
        leafNum = getData.getInt("leafNum",36);
        tvLeafNum = Integer.toString(leafNum) + "片";
        showLeafNum.setText(tvLeafNum);

        lstCoupon = new ArrayList<>();
        putAllCoupon();
        RecyclerView myrv = (RecyclerView)findViewById(R.id.leafRecyler);
        recyclerviewCoupon myAdapter = new recyclerviewCoupon(this,lstCoupon);
        myrv.setLayoutManager(new GridLayoutManager(this,1));
        myrv.setAdapter(myAdapter);

        btMyDiscount = (Button)findViewById(R.id.btMyDiscount);
        btMyDiscount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydicount.setConsumed("");
                catchMyCoupon();
            }
        });
    }
    private void putAllCoupon(){
        for(int i = 0; i < home.reNum(); i++){
            Log.i("checkDiscount",home.reCouponNo(i));
            lstCoupon.add(new couponlist(home.rePlaceName(i),home.reCouponIntro(i),home.reLeafAmount(i),home.reCouponNo(i)));
        }
    }

    private void catchMyCoupon(){
        rq = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, Urishow, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseJsonWithGson(response);
                Intent intent = new Intent(discountarea.this,mydicount.class);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if( error instanceof com.android.volley.AuthFailureError){
                    Toast.makeText(discountarea.this,"Authentication error",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.NetworkError){
                    Toast.makeText(discountarea.this,"請連上網路",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.ParseError){
                    Toast.makeText(discountarea.this,"ParseError ",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.ServerError){
                    Toast.makeText(discountarea.this,"ServerError",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.NoConnectionError){
                    Toast.makeText(discountarea.this,"請連上網路",Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("user_ID",user);
                return parameters;
            }
        };
        MySingleton.getInstance(discountarea.this).addToRequestQueue(request);
    }

    public void parseJsonWithGson(String response){
        Gson gson = new Gson();
        List<toJson> toJsonsList = gson.fromJson(response , new TypeToken<List<toJson>>(){}.getType());
        num = 0;
        for(toJson Tojson : toJsonsList){
            placeName[num] = Tojson.palce_name;
            coupIntro[num] = Tojson.cou_intro;
            coupDDL[num]  = Tojson.cou_deadline;
            coupNo[num] = Tojson.cou_no;
            coupID[num] = Tojson.cou_ID;
            num++;
        }
    }


    public void catchImage(){
            imageNum++;
            ImageRequest imageRequest = new ImageRequest(
                    "http://35.170.200.252/coupon_img/" + Integer.toString(imageNum+1) + ".png", // Image URL
                    new Response.Listener<Bitmap>() { // Bitmap listener
                        @Override
                        public void onResponse(Bitmap response) {
                            uri[imageNum] = saveImageToInternalStorage(response);
                            if(imageNum == 0){
                                catchImage();
                            }
                        }
                    },
                    0, // Image width
                    0, // Image height
                    ImageView.ScaleType.CENTER_CROP, // Image scale type
                    Bitmap.Config.RGB_565, //Image decode configuration
                    new Response.ErrorListener() { // Error listener
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Do something with error response
                            error.printStackTrace();
                        }
                    }
            );
            MySingleton.getInstance(discountarea.this).addToRequestQueue(imageRequest);
    }

    protected Uri saveImageToInternalStorage(Bitmap bitmap){
        // Initialize ContextWrapper
        ContextWrapper wrapper = new ContextWrapper(getApplicationContext());

        // Initializing a new file
        // The bellow line return a directory in internal storage
        File file = wrapper.getDir("Images",MODE_PRIVATE);

        // Create a file to save the image
        file = new File(file, Integer.toString(fileNum) + ".png");

        try{
            // Initialize a new OutputStream
            OutputStream stream = null;

            // If the output file exists, it can be replaced or appended to it
            stream = new FileOutputStream(file);

            // Compress the bitmap
            bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);

            // Flushes the stream
            stream.flush();

            // Closes the stream
            stream.close();

        }catch (IOException e) // Catch the exception
        {
            e.printStackTrace();
        }

        // Parse the gallery image url to uri
        Uri savedImageURI = Uri.parse(file.getAbsolutePath());

        // Return the saved image Uri
        fileNum++;
        return savedImageURI;
    }

    public static Uri reImage(int i){
        Log.i("checkUri",Integer.toString(i));
        return uri[i];
    }
    public static String getPlaceName(int i){
        return placeName[i];
    }

    public static String getCoupIntro(int i){
        return  coupIntro[i];
    }

    public static String getCoupDDL(int i){
        return coupDDL[i];
    }

    public static String getCoupID(int i){
        return coupID[i];
    }
    public static int getMyCouponNum(){
        return num;
    }
    public static int getCoupNo(int i){
        if(coupNo[i].equals("1")){
            return 0;
        }else{
            return 1;
        }
    }
    public static String getMyCoupNo(int i){
        return coupNo[i];
    }
    public static String getUser(){
        return user;
    }
}
