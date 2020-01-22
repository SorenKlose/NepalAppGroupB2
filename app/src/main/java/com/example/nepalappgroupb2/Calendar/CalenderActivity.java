package com.example.nepalappgroupb2.Calendar;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import com.example.nepalappgroupb2.Domain.searchWordProvider;
import com.example.nepalappgroupb2.R;

public class CalenderActivity extends AppCompatActivity implements searchWordProvider {

    private final MutableLiveData<String> searchWord = new MutableLiveData<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calender_card_layout);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.calendar_framelayout, new CalendarLoading())
                .commit();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }
    public MutableLiveData<String> getSearchWord() {
        return searchWord;
    }
}
