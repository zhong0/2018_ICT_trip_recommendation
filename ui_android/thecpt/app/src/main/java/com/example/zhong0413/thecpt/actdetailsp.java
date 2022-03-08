package com.example.zhong0413.thecpt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class actdetailsp extends AppCompatActivity {
    private RequestQueue rq;
    private String Uri = "http://35.170.200.252/db/showPromotion_detail.php";
    private String placeName;
    private String proNo;
    private String proIntro;
    private String proDeadline;

    private TextView tvProPlace;
    private TextView tvProIntro;
    private TextView tvProDDL;
    private ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actdetailsp);

        Intent intent = getIntent();
        placeName = intent.getExtras().getString("proTitle");
        proNo = intent.getExtras().getString("proNo");


        tvProPlace = (TextView)findViewById(R.id.tvProPlaceName);
        tvProPlace.setText(placeName);
        tvProIntro = (TextView)findViewById(R.id.tvProIntro);
        tvProDDL = (TextView)findViewById(R.id.tvProDeadline);
        img = (ImageView)findViewById(R.id.promotionImg);
        img.setImageURI(speciailAct.getPromotionImage());

        catchDetail();
    }

    private void catchDetail(){
        rq = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, Uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseJsonWithGson(response);

                tvProDDL.setText(proDeadline);
                tvProIntro.setText(proIntro);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if( error instanceof com.android.volley.AuthFailureError){
                    Toast.makeText(actdetailsp.this,"Authentication error",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.NetworkError){
                    Toast.makeText(actdetailsp.this,"請連上網路",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.ParseError){
                    Toast.makeText(actdetailsp.this,"ParseError ",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.ServerError){
                    Toast.makeText(actdetailsp.this,"ServerError",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.NoConnectionError){
                    Toast.makeText(actdetailsp.this,"請連上網路",Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("pro_no",proNo);
                return parameters;
            }
        };
        MySingleton.getInstance(actdetailsp.this).addToRequestQueue(request);
    }

    public void parseJsonWithGson(String response){
        Gson gson = new Gson();
        List<toJson> toJsonsList = gson.fromJson(response , new TypeToken<List<toJson>>(){}.getType());
        for(toJson Tojson : toJsonsList){
            proIntro = Tojson.pro_intro;
            proDeadline = Tojson.pro_deadline;
        }
    }
}
