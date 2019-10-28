package com.example.nepalappgroupb2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;

public class ProfileActivity extends AppCompatActivity {

    SharedPreferences mPrefs;
    final String welcomeScreenShownPref = "welcomeScreenShown";

    private static final String TAG = "MainActivity";

    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




  //FORSØG PÅ POPUP DER KUN KOMMER EN GANG
        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        // second argument is the default to use if the preference can't be found
        Boolean welcomeScreenShown = mPrefs.getBoolean(welcomeScreenShownPref, false);

        if (!welcomeScreenShown) {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(ProfileActivity.this);
            View mView = getLayoutInflater().inflate(R.layout.popup, null);
            final ImageView mom = (ImageView) mView.findViewById(R.id.iwMom);
            final ImageView pregnate = (ImageView) mView.findViewById(R.id.iwPregnate);
            mBuilder.setView(mView);
            mBuilder.setView(mView);
            final AlertDialog dialog = mBuilder.create();
            dialog.show();

            mom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  //  Intent myIntent = new Intent(ProfileActivity.this, Profile2Acitivty.class);
                  //  ProfileActivity.this.startActivity(myIntent);
                    dialog.dismiss();
                }});
            pregnate.setOnClickListener(new View.OnClickListener(){
                public void onClick (View view){
                    dialog.dismiss();
                }
            });
        }
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putBoolean(welcomeScreenShownPref, true);
        editor.commit();





    //DATE PICKER
        mDisplayDate = (TextView) findViewById(R.id.tvDate);

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        ProfileActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListener
                        , year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                mDateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month + 1;
                        Log.d(TAG, "onDateSet: mm/dd/yyy: " + day + "/" + month + "/" + year);

                        String date = day + "/" + month + "/" + year;
                        mDisplayDate.setText(date);
                    }
                };
            }
        };
    }}
