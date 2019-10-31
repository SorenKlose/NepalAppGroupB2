package com.example.nepalappgroupb2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

import static java.util.Calendar.getInstance;

public class ProgressBarFragment extends Fragment {

    private ProgressBar progressBar;








    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_progress_bar, container, false);

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        long currentDate = Calendar.getInstance().getTimeInMillis();
        // com.example.nepalappgroupb2.Calendar birthDate = new GregorianCalendar();

        long birthDate = 25;

        long progressInDays = TimeUnit.MILLISECONDS.toDays(currentDate - birthDate);


        progressBar.setProgress(50); // progressStatus

        return view;


    }

}
