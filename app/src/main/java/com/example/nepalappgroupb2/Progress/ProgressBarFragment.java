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
        int progressInDays = monthsOld(getContext())*30;
        double progressInPercent =  (double) progressInDays / (double)1000 * 100;
        progressBar.setProgress((int)progressInPercent); // progressStatus
        System.out.println(progressInDays);
        System.out.println(monthsOld(getContext()));
    }

    /**
     * Finds how many months in the pregnancy the mom is
     * @param context the context of where it's called from
     * @return the pregnancy in months
     */
    public int monthsOld(Context context) {
        SharedPreferences sp = context.getSharedPreferences("profile", Context.MODE_PRIVATE);

        int year = sp.getInt("year", 0);
        int month = sp.getInt("month", 0);
        int day = sp.getInt("day", 0);
        int monthsPregnant = sp.getInt("monthsPregnant", 0);

        Calendar calendar = Calendar.getInstance();
        calendar.clear();

        if (year == 0 && month == 0 && day == 0) {
            return 0;
        } else {
            calendar.set(year, month, day);
            long birthDate = calendar.getTimeInMillis();
            long currentDate = Calendar.getInstance().getTimeInMillis();

            long progressInDays = TimeUnit.MILLISECONDS.toDays(
                    currentDate + TimeUnit.DAYS.toMillis(30) - birthDate);

            if (progressInDays == 0) {
                return monthsPregnant;
            } else {
                // ikke helt nøjagtigt men det har ingen virkning i vores tilfælde
                long months = 9 + progressInDays / 30;
                return (int) months;
            }
        }
    }
}
