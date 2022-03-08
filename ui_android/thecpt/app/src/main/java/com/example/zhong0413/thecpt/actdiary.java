package com.example.zhong0413.thecpt;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.view.View.VISIBLE;

public class actdiary extends AppCompatActivity {

    String uri = "http://35.170.200.252/db/history_get.php";
    String UrigetActDetail = "http://35.170.200.252/db/history_get_2.php";
    String Urimemory = "http://35.170.200.252/db/history_memory.php";
    String Uridelete = "http://35.170.200.252/db/history_delete.php";
    RequestQueue rq;

    private SharedPreferences getData;
    private String userAccount;
    private String actNo;
    private String actName;
    private String actIntro;
    private String actActAdd;
    private String actActTel;
    private String actDate;
    private String ct;

    private TextView name;
    private TextView intro;
    private TextView add;
    private TextView tel;
    private EditText content;
    private Button btSave;
    private ImageButton map;
    private String userThought = "";

    private ImageButton imgPhone;
    private TextView fixContent;
    private static final String TAG = "actdiary";
    private static final int ERROR_DIALOG_REQUEST = 9001;

    private static String[] allHistoryActName = new String[100];
    private static String[] allHistoryActNo = new String[100];
    private static String[] allHistoryItemName = new String[100];
    private static String[] allHistoryDate = new String[100];
    private static int count = 0;

    public static String check = "actdiary";
    public static Boolean sureDelete = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //recieve data
        Intent intent = getIntent();
        actName = intent.getExtras().getString("ActTitle");
        actNo = intent.getExtras().getString("ActNo");
        actDate = intent.getExtras().getString("ActDate");

        getData = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        userAccount = getData.getString("account_key","");


        catchAllActivity();

        final ProgressDialog dialog= ProgressDialog.show(actdiary.this,"搜尋中", "Searching ...",true);
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


        setContentView(R.layout.actdiary);
        buildView();


    }
    @Override
    public void onBackPressed() {
    }
    private void buildView(){

        name = (TextView)findViewById(R.id.ActName);
        intro = (TextView)findViewById(R.id.ActInfo);
        add = (TextView)findViewById(R.id.ActAdd);
        tel = (TextView)findViewById(R.id.ActTel);
        content = (EditText)findViewById(R.id.edContent);
        fixContent = (TextView)findViewById(R.id.fixContent);


        imgPhone = (ImageButton)findViewById(R.id.phone);
        imgPhone.setOnClickListener(phoneListener);
        imgPhone.setVisibility(View.INVISIBLE);


        btSave = (Button)findViewById(R.id.btSaveContent);
        btSave.setOnClickListener(saveListener);

        if(isServiceOK()){
            init();
        }
    }
    private View.OnClickListener phoneListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", actActTel, null));
            startActivity(intent);
        }
    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navforhistorylist, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.trash:
                ShowDialog();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private View.OnClickListener saveListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showDialogSave();
        }
    };

    private void init(){
        ImageButton btnMap = (ImageButton)findViewById(R.id.btMap);
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(actdiary.this , MapActivity.class);
                MapActivity.searchString = actActAdd;
                startActivity(intent);
            }
        });
    }

    public void catchAllActivity() {
        rq = Volley.newRequestQueue(this);
        StringRequest requestMode2 = new StringRequest(Request.Method.POST,UrigetActDetail, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseJsonWithGson(response);

                if(!userThought.equals("")){
                    content.setVisibility(View.INVISIBLE);
                    btSave.setVisibility(View.INVISIBLE);
                    fixContent.setVisibility(VISIBLE);
                    fixContent.setText(userThought);
                }
                name.setText(actName);
                intro.setText(actIntro);
                add.setText(actActAdd);
                tel.setText(actActTel);

                if(!actActTel.equals("無")){
                    imgPhone.setVisibility(VISIBLE);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if( error instanceof com.android.volley.AuthFailureError){
                    Toast.makeText(actdiary.this,"Authentication error",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.NetworkError){
                    Toast.makeText(actdiary.this,"NetworkError",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.ParseError){
                    Toast.makeText(actdiary.this,"ParseError ",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.ServerError){
                    Toast.makeText(actdiary.this,"ServerError",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.NoConnectionError){
                    Toast.makeText(actdiary.this,"NoConnectionError",Toast.LENGTH_SHORT).show();
                }
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<>();
                parameters.put("user_ID",userAccount);
                parameters.put("date",actDate);
                parameters.put("activity_no",actNo);
                return parameters;
            }
        };
        MySingleton.getInstance(this).addToRequestQueue(requestMode2);
    }


    public void parseJsonWithGson(String response){
        Gson gson = new Gson();
        List<toJson> toJsonsList = gson.fromJson(response , new TypeToken<List<toJson>>(){}.getType());
        for(toJson Tojson : toJsonsList) {
            actIntro = Tojson.intro;
            actActAdd = Tojson.addr;
            actActTel = Tojson.tel;
            userThought = Tojson.thoughts;
        }
    }
    public void saveContent() {
        rq = Volley.newRequestQueue(this);
        StringRequest requestMode2 = new StringRequest(Request.Method.POST,Urimemory, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if( error instanceof com.android.volley.AuthFailureError){
                    Toast.makeText(actdiary.this,"Authentication error",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.NetworkError){
                    Toast.makeText(actdiary.this,"NetworkError",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.ParseError){
                    Toast.makeText(actdiary.this,"ParseError ",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.ServerError){
                    Toast.makeText(actdiary.this,"ServerError",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.NoConnectionError){
                    Toast.makeText(actdiary.this,"NoConnectionError",Toast.LENGTH_SHORT).show();
                }
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<>();
                parameters.put("user_ID",userAccount);
                parameters.put("date",actDate);
                parameters.put("activity_no",actNo);
                parameters.put("thoughts",ct);
                return parameters;
            }
        };
        MySingleton.getInstance(this).addToRequestQueue(requestMode2);
    }
    public void deleteAct() {
        rq = Volley.newRequestQueue(this);
        StringRequest requestMode2 = new StringRequest(Request.Method.POST,Uridelete, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if( error instanceof com.android.volley.AuthFailureError){
                    Toast.makeText(actdiary.this,"Authentication error",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.NetworkError){
                    Toast.makeText(actdiary.this,"NetworkError",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.ParseError){
                    Toast.makeText(actdiary.this,"ParseError ",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.ServerError){
                    Toast.makeText(actdiary.this,"ServerError",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.NoConnectionError){
                    Toast.makeText(actdiary.this,"NoConnectionError",Toast.LENGTH_SHORT).show();
                }
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<>();
                parameters.put("user_ID",userAccount);
                parameters.put("date",actDate);
                parameters.put("activity_no",actNo);
                return parameters;
            }
        };
        MySingleton.getInstance(this).addToRequestQueue(requestMode2);
    }

    public boolean isServiceOK(){
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(actdiary.this);
        if (available == ConnectionResult.SUCCESS){
            return true;
        }else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(actdiary.this,available,ERROR_DIALOG_REQUEST);
            dialog.show();
        }else{
            Toast.makeText(this,"您未許可Google Map",Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    public void RecatchAllActivity() {
        rq = Volley.newRequestQueue(this);
        StringRequest requestMode2 = new StringRequest(Request.Method.POST, uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseJsonWithGsonRe(response);
                home.check = "actdiary";

                final ProgressDialog dialog= ProgressDialog.show(actdiary.this,"刪除中", "Deleting ...",true);
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

                Intent intentH  = new Intent();
                intentH.setClass(actdiary.this,allhistory.class);
                startActivity(intentH);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if( error instanceof com.android.volley.AuthFailureError){
                    Toast.makeText(actdiary.this,"Authentication error",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.NetworkError) {
                    Toast.makeText(actdiary.this, "NetworkError", Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.ParseError){
                    Toast.makeText(actdiary.this,"ParseError ",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.ServerError){
                    Toast.makeText(actdiary.this,"ServerError",Toast.LENGTH_SHORT).show();
                }
                if( error instanceof com.android.volley.NoConnectionError){
                    Toast.makeText(actdiary.this,"NoConnectionError",Toast.LENGTH_SHORT).show();
                }
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<>();
                parameters.put("user_ID",userAccount);
                return parameters;
            }
        };
        MySingleton.getInstance(this).addToRequestQueue(requestMode2);
    }

    public void parseJsonWithGsonRe(String response){
        Gson gson = new Gson();
        List<toJson> toJsonsList = gson.fromJson(response , new TypeToken<List<toJson>>(){}.getType());
        count = 0;
        for(toJson Tojson : toJsonsList){
            allHistoryItemName[count] = Tojson.item;
            allHistoryActName[count] = Tojson.palce_name;
            allHistoryActNo[count] = Tojson.activity_no;
            allHistoryDate[count] = Tojson.date;
            count++;
        }
    }
    public static int reNumofHistory(){
        return count;
    }
    public static String reNameofHisory(int i){ return allHistoryActName[i] + allHistoryItemName[i]; }
    public static String reallHistoryActNo(int i){
        return allHistoryActNo[i];
    }
    public static String reallDateofHistroy(int i){
        return allHistoryDate[i];
    }


    private boolean de;
    private void ShowDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("刪除紀錄");
        alertDialogBuilder
                .setMessage("確定刪除此歷史紀錄？")
                .setCancelable(false)
                .setPositiveButton("確定",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        deleteAct();
                        RecatchAllActivity();


                    }
                })
                .setNegativeButton("取消",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        de = false;
                        Log.i("testC","cancel");
                    }
                });

        AlertDialog alert11 = alertDialogBuilder.create();
        alert11.show();
    }

    private void showDialogSave() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("儲存感想");
        alertDialogBuilder
                .setMessage("感想儲存後不得更改，確定儲存？")
                .setCancelable(false)
                .setPositiveButton("確定",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        ct = content.getText().toString();
                        content.setVisibility(View.INVISIBLE);
                        btSave.setVisibility(View.INVISIBLE);
                        fixContent.setText(ct);
                        fixContent.setVisibility(VISIBLE);

                        saveContent();
                    }
                })
                .setNegativeButton("取消",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                    }
                });

        AlertDialog alert11 = alertDialogBuilder.create();
        alert11.show();

    }
}
