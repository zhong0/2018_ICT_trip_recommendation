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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class mode extends AppCompatActivity {

    private static SharedPreferences getData;

    private ToggleButton personal;
    private ToggleButton stranger;
    private ToggleButton group;
    private TextView tvgender;
    private ToggleButton tgMan;
    private ToggleButton tgWoman;
    private ToggleButton tgMix;
    private TextView tvage;
    private static Spinner spage;
    private TextView tvCarorTyp;
    private static Spinner spCarorTyp;
    private Button chooseTrip;

    private static String[] allAgeStranger = {"未滿18歲","18-25歲","26-35歲","36-45歲","46-55歲","56-65歲","66歲以上"};
    private static String[] allAgeGroup = {"中學生","大學生","年輕人","青年人","中年人","老年人","各年齡層都有"};
    private static String[] allCareer = {"科技人","工業工程人","設計人","美妝美髮人","醫療人","餐飲人","保險人","商管人","軍公教","學生","其他人"};
    private static String[] allRelationship= {"心靈避風港的家人","半生熟的普通朋友","老膩在一起的麻吉","甜蜜分不開的情侶"};

    private static String account = "";
    private static String gender = "male";
    private static String age = "";
    private static String status = "";
    private static String type = "personalUser"; //相當於模式

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mode);

        buildView();
    }

    private void buildView(){
        chooseTrip = (Button)findViewById(R.id.btChooseTrip);
        chooseTrip.setOnClickListener(myListener);

        personal = (ToggleButton)findViewById(R.id.tgbtModePerson);
        personal.setOnCheckedChangeListener(personToggle);

        stranger = (ToggleButton)findViewById(R.id.tgbtModeStranger);
        stranger.setOnCheckedChangeListener(strangerToggle);

        group = (ToggleButton)findViewById(R.id.tgbtModeGroup);
        group.setOnCheckedChangeListener(groupToggle);

        tvgender = (TextView)findViewById(R.id.tvGender);
        tgMan = (ToggleButton)findViewById(R.id.tgbtGenderMan);
        tgMan.setOnCheckedChangeListener(manToggle);

        tgWoman = (ToggleButton)findViewById(R.id.tgbtGenderWoman);
        tgWoman.setOnCheckedChangeListener(womanToggle);

        tgMix = (ToggleButton)findViewById(R.id.tgbtGenderMix);
        tgMix.setOnCheckedChangeListener(mixToggle);

        tvage = (TextView)findViewById(R.id.tvAge);
        spage = (Spinner)findViewById(R.id.spinnerAge);

        tvCarorTyp = (TextView)findViewById(R.id.tvCareerOrType);
        spCarorTyp = (Spinner)findViewById(R.id.CareerOrTypeSpinner);

        getData = getSharedPreferences("myPrefs",Context.MODE_PRIVATE);
        account = getData.getString("account_key","");
        gender = getData.getString("gender_key","");
        age = getData.getString("age_key","");
        status = getData.getString("career_key","");

    }

    //home
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.navhome,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.home:
                Intent intentHome = new Intent(this,home.class);
                intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentHome);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private CompoundButton.OnCheckedChangeListener personToggle = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked){
                if(account.equals("")){
                    personal.setChecked(false);
                    Toast.makeText(mode.this,"尚未登入，請至首頁拉開左側選單至我的帳戶登入",Toast.LENGTH_SHORT).show();
                }else{
                    stranger.setChecked(false);
                    group.setChecked(false);

                    type = "personalUser";
                    gender = getData.getString("gender_key","");
                    age = getData.getString("age_key","");
                    status = getData.getString("career_key","");

                    chooseTrip.setVisibility(View.VISIBLE);
                }
            }else{
                chooseTrip.setVisibility(View.INVISIBLE);
            }
        }
    };

    private CompoundButton.OnCheckedChangeListener strangerToggle = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked){
                type = "personalStranger";

                personal.setChecked(false);
                group.setChecked(false);

                tvgender.setVisibility(View.VISIBLE);
                tgMan.setVisibility(View.VISIBLE);
                tgMan.setBackgroundResource(R.drawable.mantoggle);
                tgMan.setChecked(true);
                tgWoman.setVisibility(View.VISIBLE);
                tgWoman.setBackgroundResource(R.drawable.womantoggle);
                tgWoman.setChecked(false);
                gender = "male";

                tvage.setVisibility(View.VISIBLE);
                setAge("stranger");
                spage.setVisibility(View.VISIBLE);

                tvCarorTyp.setVisibility(View.VISIBLE);
                tvCarorTyp.setText("職業");
                setCareerOrType("stranger");
                spCarorTyp.setVisibility(View.VISIBLE);

                chooseTrip.setVisibility(View.VISIBLE);
            }else{
                tvgender.setVisibility(View.INVISIBLE);
                tgMan.setVisibility(View.INVISIBLE);
                tgWoman.setVisibility(View.INVISIBLE);
                tvage.setVisibility(View.INVISIBLE);
                spage.setVisibility(View.INVISIBLE);
                tvCarorTyp.setVisibility(View.INVISIBLE);
                spCarorTyp.setVisibility(View.INVISIBLE);
                chooseTrip.setVisibility(View.INVISIBLE);
            }
        }
    };

    private CompoundButton.OnCheckedChangeListener groupToggle = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked){
                type = "Group";

                personal.setChecked(false);
                stranger.setChecked(false);

                tvgender.setVisibility(View.VISIBLE);
                tgMan.setVisibility(View.VISIBLE);
                tgMan.setChecked(true);
                tgMan.setBackgroundResource(R.drawable.mentoggle);
                tgWoman.setVisibility(View.VISIBLE);
                tgWoman.setChecked(false);
                tgMix.setVisibility(View.VISIBLE);
                tgWoman.setBackgroundResource(R.drawable.womentoggle);
                tgWoman.setChecked(false);
                gender = "male";

                tvage.setVisibility(View.VISIBLE);
                setAge("group");
                spage.setVisibility(View.VISIBLE);


                tvCarorTyp.setVisibility(View.VISIBLE);
                tvCarorTyp.setText("關係");
                setCareerOrType("group");
                spCarorTyp.setVisibility(View.VISIBLE);

                chooseTrip.setVisibility(View.VISIBLE);
            }else{
                tvgender.setVisibility(View.INVISIBLE);
                tgMan.setVisibility(View.INVISIBLE);
                tgWoman.setVisibility(View.INVISIBLE);
                tgMix.setVisibility(View.INVISIBLE);
                tvage.setVisibility(View.INVISIBLE);
                spage.setVisibility(View.INVISIBLE);
                tvCarorTyp.setVisibility(View.INVISIBLE);
                spCarorTyp.setVisibility(View.INVISIBLE);
                chooseTrip.setVisibility(View.INVISIBLE);
            }
        }
    };

    private CompoundButton.OnCheckedChangeListener manToggle = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked){
                tgWoman.setChecked(false);
                tgMix.setChecked(false);
                gender = "male";
            }else if(isChecked == false && tgWoman.isChecked()==false && tgMix.isChecked()==false){
                tgMan.setChecked(true);
            }
        }
    };

    private CompoundButton.OnCheckedChangeListener womanToggle = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked){
                tgMan.setChecked(false);
                tgMix.setChecked(false);
                gender = "female";
            }else if(isChecked == false && tgMan.isChecked()==false && tgMix.isChecked()==false){
                tgWoman.setChecked(true);
            }
        }
    };

    private CompoundButton.OnCheckedChangeListener mixToggle = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked){
                tgMan.setChecked(false);
                tgWoman.setChecked(false);
                gender = "mix";
            }else if(isChecked == false && tgWoman.isChecked()==false && tgMan.isChecked()==false){
                tgMix.setChecked(true);
            }
        }
    };

    private void setAge(String str){
        switch (str){
            case "stranger":
                ArrayAdapter<String> ageListStr = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,allAgeStranger);
                ageListStr.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                spage.setAdapter(ageListStr);
                break;
            case "group":
                ArrayAdapter<String> ageListGr = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,allAgeGroup);
                ageListGr.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                spage.setAdapter(ageListGr);
                break;
        }
    }

    private void setCareerOrType(String str){
        switch(str){
            case "stranger":
                ArrayAdapter<String> careerList = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,allCareer);
                careerList.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                spCarorTyp.setAdapter(careerList);
                break;
            case "group":
                ArrayAdapter<String> relationList = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,allRelationship);
                relationList.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                spCarorTyp.setAdapter(relationList);
                break;
        }
    }
    private View.OnClickListener myListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent();
            intent.setClass(mode.this,choice.class);
            startActivity(intent);
        }
    };

    public static String reType(){
        return type;
    }
    public static String reAccount(){
        return account;
    }


    public static String reGender(){
        if(type.equals("personalUser")){
            if(gender.equals("女性")){
                return "female";
            }else{
                return "male";
            }
        }
        Log.i("checkGender",gender);
        return gender;
    }

    public static String reAge() {
        String uAge = age;
        int selectAge = spage.getSelectedItemPosition();
        if(type.equals("personalStranger")){
            uAge =  allAgeStranger[selectAge];
        }else if(type.equals("Group")){
            uAge = allAgeGroup[selectAge];
        }
        Log.i("checkAge",uAge);
        switch (uAge) {
            case "未滿18歲":
                return "1";
            case "18-25歲":
                return "2";
            case "26-35歲":
                return "3";
            case "36-45歲":
                return "4";
            case "46-55歲":
                return "5";
            case "56-65歲":
                return "6";
            case "66歲以上":
                return "7";
            case "中學生":
                return "8";
            case "大學生":
                return "9";
            case "年輕人":
                return "10";
            case "青年人":
                return "11";
            case "中年人":
                return "12";
            case "老年人":
                return "13";
            default:
                return "14";
        }
    }

    public static String reStatus(){
        String uCareer = status;
        int selectCareer = spCarorTyp.getSelectedItemPosition();
        if(type.equals("personalStranger")){
            uCareer = allCareer[selectCareer];
        }else if(type.equals("Group")){
            uCareer = allRelationship[selectCareer];
        }
        Log.i("checkCareer",uCareer);
        switch(uCareer){
            case "科技人":
                return "1";
            case "工業工程人":
                return "2";
            case "設計人":
                return "3";
            case "美妝美髮人":
                return "4";
            case "醫療人":
                return "5";
            case "餐飲人":
                return "6";
            case "保險人":
                return "7";
            case "商管人":
                return "8";
            case "軍公教":
                return "9";
            case "學生":
                return "10";
            case "其他人":
                return "11";
            case "心靈避風港的家人":
                return "1";
            case "半生熟的普通朋友":
                return "2";
            case "老膩在一起的麻吉":
                return "3";
            case "甜蜜分不開的情侶":
                return "4";
            default:
                return "";
        }
    }
}

