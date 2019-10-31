package com.example.nepalappgroupb2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, new Calender())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onClick(View view) {
        if(view == btn) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameLayout, new Calender())
                    .addToBackStack(null)
                    .commit();
        }
    }
}