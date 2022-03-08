package com.example.zhong0413.thecpt;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class exhibidetail extends AppCompatActivity {

    private TextView tvExName;
    private TextView tvExDate;
    private TextView tvExIntro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exhibidetail);

        buildView();
    }

    private void buildView(){
        tvExName = (TextView)findViewById(R.id.tvExbName);
        tvExName.setText(actdetail.reExName());

        tvExDate = (TextView)findViewById(R.id.exDate);
        tvExDate.setText(actdetail.reExDate());

        tvExIntro = (TextView)findViewById(R.id.exIntro);
        tvExIntro.setText(actdetail.reExIntro());
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
}

