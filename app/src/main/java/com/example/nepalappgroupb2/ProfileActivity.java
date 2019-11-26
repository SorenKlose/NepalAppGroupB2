package com.example.nepalappgroupb2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.Calendar;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    SharedPreferences mPrefs;
    final String popUpScreenShownPref = "popupscreen";


    private TextView mDisplayDate;
    private EditText nameInput;
    private EditText heightInput;
    private EditText weightInput;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        mDisplayDate = (TextView) findViewById(R.id.tvDate);
        nameInput = (EditText) findViewById(R.id.nameInput);
        heightInput = (EditText) findViewById(R.id.heightInput);
        weightInput = (EditText) findViewById(R.id.weightInput);

        nameInput.setOnClickListener(this);
        heightInput.setOnClickListener(this);
        weightInput.setOnClickListener(this);

        SharedPreferences sp = getSharedPreferences("profile", Context.MODE_PRIVATE);

        nameInput.setText(sp.getString("name", null));
        heightInput.setText(sp.getString("height", null));
        weightInput.setText(sp.getString("weight", null));


        int year = sp.getInt("year", -1);
        int month = sp.getInt("month", -1);
        int day = sp.getInt("day", -1);
        String date = day + "/" + month + "/" + year;
        mDisplayDate.setText(date);



  //FORSØG PÅ POPUP DER KUN KOMMER EN GANG
        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean popUpScreenShown = mPrefs.getBoolean(popUpScreenShownPref, false);

        if (!popUpScreenShown) {
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
                    dialog.dismiss();
                }});
            pregnate.setOnClickListener(new View.OnClickListener(){
                public void onClick (View view){
                    Intent myIntent = new Intent(ProfileActivity.this, Profile2Activity.class);
                    ProfileActivity.this.startActivity(myIntent);
                    finish();
                }
            });
        }
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putBoolean(popUpScreenShownPref, true);
        editor.apply();

        ImageView imFaneM = (ImageView) findViewById(R.id.imFaneM);
        imFaneM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(ProfileActivity.this, Profile2Activity.class);
                ProfileActivity.this.startActivity(myIntent);
                finish();
            }});



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
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month += 1;
                String date = day + "/" + month + "/" + year;
                mDisplayDate.setText(date);

                SharedPreferences sp = getSharedPreferences("profile", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();

                editor.putInt("year", year);
                editor.putInt("month", month);
                editor.putInt("day", day);
                editor.apply();
            }
        };
    }

    @Override
    public void onClick(View view) {
        SharedPreferences sp = getSharedPreferences("profile", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if(view == nameInput){
            editor.putString("name", nameInput.getText().toString());
        } else if(view == heightInput){
            NumberPicker heightPicker = new NumberPicker(ProfileActivity.this);
            heightPicker.setMinValue(1);
            heightPicker.setMaxValue(200);
            editor.putString("height", heightInput.getText().toString());
        } else if(view == weightInput){
                editor.putString("weight", weightInput.getText().toString());
        }
        editor.apply();
    }
};

