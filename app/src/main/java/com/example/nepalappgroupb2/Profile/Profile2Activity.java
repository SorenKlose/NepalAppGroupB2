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

import com.example.nepalappgroupb2.Progress.ProgressBarFragment;
import com.example.nepalappgroupb2.R;

public class Profile2Activity extends AppCompatActivity {

    private Button momButton;
    SharedPreferences sp;
    private ProgressBarFragment progressBar;
    private Button saveButton;
    private NumberPicker numberPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_2);


        saveButton = (Button) findViewById(R.id.saveButton);
        momButton = (Button) findViewById(R.id.momButton);
        sp = getSharedPreferences("profile", Context.MODE_PRIVATE);

        numberPicker = findViewById(R.id.numberPicker);
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(9);


        numberPicker.setValue(sp.getInt("monthsPregnant", 0));
// fjern her
        momButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile2Activity.this, ProfileActivity.class);
                startActivity(intent);
                finish();
            }});

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp = getSharedPreferences("profile", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();

                editor.putInt("monthsPregnant", numberPicker.getValue());
                editor.apply();
                System.out.println(numberPicker.getValue());
                progressBar = (ProgressBarFragment) getSupportFragmentManager().findFragmentById(R.id.progressBar);
                progressBar.update();
                Intent intent = new Intent(Profile2Activity.this, ProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
