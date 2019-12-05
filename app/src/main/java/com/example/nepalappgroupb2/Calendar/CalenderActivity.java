package com.example.nepalappgroupb2.Calendar;

import android.app.Activity;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import com.example.nepalappgroupb2.BenytRecyclerviewEkspanderbar;
import com.example.nepalappgroupb2.R;
import com.example.nepalappgroupb2.searchWordProvider;

public class CalenderActivity extends AppCompatActivity implements searchWordProvider {

    private final MutableLiveData<String> searchWord = new MutableLiveData<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calender_card_layout);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.calendar_framelayout, new BenytRecyclerviewEkspanderbar())
                .commit();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }
    public MutableLiveData<String> getSearchWord() {
        return searchWord;
    }
}
