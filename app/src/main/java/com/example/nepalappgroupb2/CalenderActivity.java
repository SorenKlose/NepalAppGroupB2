package com.example.nepalappgroupb2;

import android.app.Activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

public class CalenderActivity extends AppCompatActivity implements searchWordProvider{

    private final MutableLiveData<String> searchWord = new MutableLiveData<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calender_card_layout);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.calendar_framelayout, new Calendar())
                .commit();
    }
    public MutableLiveData<String> getSearchWord() {
        return searchWord;
    }
}
