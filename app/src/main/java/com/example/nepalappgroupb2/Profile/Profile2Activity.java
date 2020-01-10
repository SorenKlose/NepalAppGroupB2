package com.example.nepalappgroupb2.Profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.example.nepalappgroupb2.R;

public class Profile2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_2);




        ImageView imFaneP = (ImageView) findViewById(R.id.imFaneP);
        NumberPicker np = findViewById(R.id.numberPicker);
        np.setMinValue(1);
        np.setMaxValue(9);

        np.setOnValueChangedListener(onValueChangeListener);

        imFaneP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Profile2Activity.this, ProfileActivity.class);
                Profile2Activity.this.startActivity(myIntent);
                finish();
            }});

    }

    NumberPicker.OnValueChangeListener onValueChangeListener = new NumberPicker.OnValueChangeListener(){
        @Override
        public void onValueChange(NumberPicker numberPicker, int i, int i1) {
            Toast.makeText(Profile2Activity.this,
                    "selected number "+numberPicker.getValue(), Toast.LENGTH_SHORT);
            SharedPreferences sp = getSharedPreferences("profile2", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();

            editor.putInt("monthPreg", i1);
            editor.apply();
            System.out.println(i1);

        }
    };


}