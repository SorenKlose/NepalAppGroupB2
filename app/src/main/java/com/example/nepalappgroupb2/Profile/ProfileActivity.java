package com.example.nepalappgroupb2.Profile;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nepalappgroupb2.Progress.ProgressBarFragment;
import com.example.nepalappgroupb2.R;

import java.util.Calendar;


public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

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

    public static String imagePath = null;

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
        pregnantButton.setOnClickListener(this);
        diary.setOnClickListener(this);

        SharedPreferences sp = getSharedPreferences("profile", Context.MODE_PRIVATE);
        String heightAsString = sp.getString("height", "height");
        heightInput.setText(String.format(getString(R.string.height), heightAsString));
        String weightAsString = sp.getString("weight", "weight");
        weightInput.setText(String.format(getString(R.string.weight), weightAsString));

        nameInput.setText(sp.getString("name", ""));

        int year = sp.getInt("year", 0);
        int month = sp.getInt("month", 0);
        int day = sp.getInt("day", 0);

        if(year == 0 || month == 0 || day == 0){
            Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH) + 1;
            day = c.get(Calendar.DAY_OF_MONTH);
        }
        String date = day + "/" + month + "/" + year;
        mDisplayDate.setText(date);



  //FORSØG PÅ POPUP DER KUN KOMMER EN GANG
        SharedPreferences mPrefs = getSharedPreferences("popupscreen",Context.MODE_PRIVATE);
        Boolean popUpScreenShown = mPrefs.getBoolean("popupscreen", false);

        if (!popUpScreenShown) {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(ProfileActivity.this);
            View mView = getLayoutInflater().inflate(R.layout.profile_preg_or_mom, null);
            Button mom = (Button) mView.findViewById(R.id.btnMom);
            Button pregnant = (Button) mView.findViewById(R.id.btnPreg);
            mBuilder.setView(mView);
            final AlertDialog dialog = mBuilder.create();
            dialog.show();
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            mom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }});
            pregnant.setOnClickListener(new View.OnClickListener(){
                public void onClick (View view){
                    Intent myIntent = new Intent(ProfileActivity.this, Profile2Activity.class);
                    ProfileActivity.this.startActivity(myIntent);
                    finish();
                }
            });
        }

        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putBoolean("popupscreen", true);
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
                System.out.println(day + ", " + month + ", " + year);

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
                inputDialog.setContentView(R.layout.profile_heightinput_popup);

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
    // for at fjerne fokus fra navn. https://stackoverflow.com/questions/4828636/edittext-clear-focus-on-touch-outside/8766475
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent( event );
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(imagePath != null){
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            pictureFrame.setImageBitmap(bitmap);
        }
    }
};

