package com.example.nepalappgroupb2.Profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.example.nepalappgroupb2.R;

public class Profile2Activity extends AppCompatActivity {

    private Button momButton;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_2);


        momButton = (Button) findViewById(R.id.momButton);
        sp = getSharedPreferences("profile", Context.MODE_PRIVATE);

        NumberPicker numberPicker = findViewById(R.id.numberPicker);
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(9);


        numberPicker.setValue(sp.getInt("monthsPregnant", 0));

        numberPicker.setOnValueChangedListener(onValueChangeListener);

        momButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile2Activity.this, ProfileActivity.class);
                startActivity(intent);
                finish();
            }});

    }

    NumberPicker.OnValueChangeListener onValueChangeListener = new NumberPicker.OnValueChangeListener(){
        @Override
        public void onValueChange(NumberPicker numberPicker, int i, int i1) {
            Toast.makeText(Profile2Activity.this,
                    "selected number "+numberPicker.getValue(), Toast.LENGTH_SHORT);
            sp = getSharedPreferences("profile", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();

            editor.putInt("monthsPregnant", i1);
            editor.apply();
            System.out.println(i1);
        }
    };
}
