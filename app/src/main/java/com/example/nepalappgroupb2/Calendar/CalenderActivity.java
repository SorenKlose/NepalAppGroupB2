package com.example.nepalappgroupb2.Calendar;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import com.example.nepalappgroupb2.Domain.searchWordProvider;
import com.example.nepalappgroupb2.Homepage.HomepageMainActivity;
import com.example.nepalappgroupb2.R;

public class CalenderActivity extends AppCompatActivity implements searchWordProvider {

    private final MutableLiveData<String> searchWord = new MutableLiveData<>();
    //nu kommer du tilbage til det rigtige sted efter du trykker tilbage
    boolean isFromNoti;
    @Override
    public void onBackPressed() {
        if(isFromNoti) {
            Intent i = new Intent(this, HomepageMainActivity.class);
            startActivity(i);
            finish();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calender_card_layout);

        isFromNoti = getIntent().getBooleanExtra("fromNoti", false);


        getSupportFragmentManager().beginTransaction()
                .replace(R.id.calendar_framelayout, new CalendarLoading())
                .commit();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

    }
    public MutableLiveData<String> getSearchWord() {
        return searchWord;
    }
}
