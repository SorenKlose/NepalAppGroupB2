package com.example.nepalappgroupb2.Progress;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.nepalappgroupb2.R;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;


public class ProgressBarFragment extends Fragment {

    private ProgressBar progressBar;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_progress_bar, container, false);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        update();
        return view;
    }


    public void update(){

        SharedPreferences sp = this.getActivity().getSharedPreferences("profile", Context.MODE_PRIVATE);

        int year = sp.getInt("year", -1);
        int month = sp.getInt("month", -1);
        int day = sp.getInt("day", -1);
        int monthsPregnant = sp.getInt("monthsPregnant", -1);

        Calendar calendar = Calendar.getInstance();
        calendar.clear();

        calendar.set(year, month, day);

        long birthDate = calendar.getTimeInMillis();
        long currentDate = Calendar.getInstance().getTimeInMillis();

        long progressInDays = TimeUnit.MILLISECONDS.toDays(
                currentDate + TimeUnit.DAYS.toMillis(30) - birthDate);
        // kontroller om datasetter står på 0/0/0
        if (progressInDays == 0){
            progressInDays = monthsPregnant * 30;
        } else {
            progressInDays += 9*30; // ikke helt præcist men også ligegyldigt da forholdet er så stort
        }
        double progressInPercent =  (double) progressInDays / (double)1000 * 100;
        progressBar.setProgress((int)progressInPercent); // progressStatus
    }
}
