package com.example.nepalappgroupb2.Profile;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nepalappgroupb2.Progress.ProgressBarFragment;
import com.example.nepalappgroupb2.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    SharedPreferences mPrefs;
    final String popUpScreenShownPref = "popupscreen";

    private TextView mDisplayDate;
    private EditText nameInput;
    private Button heightInput;
    private Button weightInput;
    private ImageView pictureFrame;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private Dialog inputDialog;
    private Button pregnantButton;
    private Button diary;
    private ProgressBarFragment progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        mDisplayDate = (TextView) findViewById(R.id.date);
        nameInput = (EditText) findViewById(R.id.nameInput);
        heightInput = (Button) findViewById(R.id.heightInput);
        weightInput = (Button) findViewById(R.id.weightInput);
        pictureFrame = (ImageView) findViewById(R.id.pictureFrame);
        pregnantButton = (Button) findViewById(R.id.pregnantButton);
        diary = (Button) findViewById(R.id.diary);


        nameInput.setOnClickListener(this);
        heightInput.setOnClickListener(this);
        weightInput.setOnClickListener(this);
        pictureFrame.setOnClickListener(this);
        pregnantButton.setOnClickListener(this);
        diary.setOnClickListener(this);

        SharedPreferences sp = getSharedPreferences("profile", Context.MODE_PRIVATE);
        heightInput.setText(sp.getString("height", "height") + " cm");
        weightInput.setText(sp.getString("weight", "weight") + " kg");
        nameInput.setText(sp.getString("name", ""));

        int year = sp.getInt("year", 0);
        int month = sp.getInt("month", 0);
        int day = sp.getInt("day", 0);
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
                progressBar = (ProgressBarFragment) getSupportFragmentManager().findFragmentById(R.id.progressBar);
                progressBar.update();

            }
        };
    }

    @Override
    public void onClick(View view) {
        final SharedPreferences sp = getSharedPreferences("profile", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sp.edit();

        switch (view.getId()){
            case R.id.nameInput:
                editor.putString("name", nameInput.getText().toString());
                editor.apply();
                break;

            case R.id.heightInput:
                inputDialog = new Dialog(this);
                inputDialog.setContentView(R.layout.input_popup);

                final NumberPicker heightPicker = inputDialog.findViewById(R.id.heightPicker);
                heightPicker.setMinValue(0);
                heightPicker.setMaxValue(100);

                inputDialog.show();

                Button saveHeightButton = (Button) inputDialog.findViewById(R.id.saveHeightButton);

                saveHeightButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editor.putString("height", String.valueOf(heightPicker.getValue()));
                    editor.apply();
                    heightInput.setText(String.valueOf(heightPicker.getValue()) + " cm");

                    Toast.makeText(getApplication().getBaseContext(),"Saved",Toast.LENGTH_LONG).show();
                    inputDialog.cancel();
                    }
                });
                break;

            case R.id.weightInput:
                inputDialog = new Dialog(this);
                inputDialog.setContentView(R.layout.weight_input_popup);

                final NumberPicker weightPickerKG = inputDialog.findViewById(R.id.weightPickerKG);
                weightPickerKG.setMinValue(0);
                weightPickerKG.setMaxValue(9);

                final NumberPicker weightPickerG = inputDialog.findViewById(R.id.weightPickerG);
                weightPickerG.setMinValue(0);
                weightPickerG.setMaxValue(9);

                inputDialog.show();

                Button saveWeightButton = (Button) inputDialog.findViewById(R.id.saveWeightButton);



                saveWeightButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        editor.putString("weight", String.valueOf(weightPickerKG.getValue() +
                                "." + String.valueOf(weightPickerG.getValue())));
                        editor.apply();
                        weightInput.setText(String.valueOf(weightPickerKG.getValue() +
                                "." + String.valueOf(weightPickerG.getValue())) + " kg");

                        Toast.makeText(getApplication().getBaseContext(),"Saved",Toast.LENGTH_LONG).show();
                        inputDialog.cancel();
                    }
                });
                break;

            case R.id.pregnantButton:
                Intent intent = new Intent(ProfileActivity.this, Profile2Activity.class);
                startActivity(intent);
                this.finish();
                break;

            case R.id.diary:
                Intent intent1 = new Intent(ProfileActivity.this, PhotoDiaryActivity.class);
                startActivity(intent1);
                break;
        }
    }
};

