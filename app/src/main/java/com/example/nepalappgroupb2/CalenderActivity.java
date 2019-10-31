package com.example.nepalappgroupb2;

import android.app.Activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CalenderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calender_card_layout);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.calendar_framelayout, new Calendar())
                .commit();
    }
}
