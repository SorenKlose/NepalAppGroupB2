package com.example.nepalappgroupb2.Calendar;

import android.animation.Animator;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.bumptech.glide.Glide;
import com.example.nepalappgroupb2.BenytRecyclerviewEkspanderbar;
import com.example.nepalappgroupb2.Domain.DataFromSheets;
import com.example.nepalappgroupb2.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class CalendarLoading extends Fragment {
    private DataFromSheets db = new DataFromSheets();

    private LottieAnimationView loading;
    public static List<Integer> monthList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.calendar_loading_screen, container, false);

        loading = layout.findViewById(R.id.lottie_loading);
        loading.loop(true);
        loading.playAnimation();

        //loading data from sheet and showing loading screen
        new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                try {
                    db.fromSheets();
                    //SystemClock.sleep(4000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            //open new fragment when finish loading
            @Override
            protected void onPostExecute(Object o) {
                monthList = db.getMonths();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.calendar_framelayout, new BenytRecyclerviewEkspanderbar())
                        .commit();
            }
        }.execute();

        return layout;
    }
}
