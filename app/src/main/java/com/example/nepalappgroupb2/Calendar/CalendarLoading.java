package com.example.nepalappgroupb2.Calendar;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.example.nepalappgroupb2.Domain.DataFromSheets;
import com.example.nepalappgroupb2.R;

public class CalendarLoading extends Fragment {
    private DataFromSheets db = new DataFromSheets();

    private LottieAnimationView loading;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.calendar_loading_screen, container, false);

        //starting the loading animation
        loading = layout.findViewById(R.id.lottie_loading);
        loading.loop(true);
        loading.playAnimation();

        //loading data from sheet and showing loading screen
        new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                try {
                    db.fromSheets(getContext());
                    //SystemClock.sleep(4000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            //open new fragment when finish loading
            @Override
            protected void onPostExecute(Object o) {
                loading.cancelAnimation();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.calendar_framelayout, new CalendarRcView())
                        .commit();
            }
        }.execute();

        return layout;
    }
}
