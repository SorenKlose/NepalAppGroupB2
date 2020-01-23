package com.example.nepalappgroupb2.Profile;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nepalappgroupb2.Domain.NotificationReciever;
import com.example.nepalappgroupb2.Homepage.HomepageMainActivity;
import com.example.nepalappgroupb2.Progress.ProgressBarFragment;
import com.example.nepalappgroupb2.R;

import java.util.Calendar;

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
                Intent intent = new Intent(Profile2Activity.this, HomepageMainActivity.class);
                startActivity(intent);
                finish();


                // Notifikation hvert minut i 6 minutter bliver kørt her. Bruger shared så den kun gør det første gang appen bliver åbnet.
                SharedPreferences prefs = getSharedPreferences("noti", Context.MODE_PRIVATE);
                Boolean notifikation = prefs.getBoolean("noti", false);
                if (!notifikation) {
                    sendNoti();

                }
                SharedPreferences.Editor editorNoti = prefs.edit();
                editorNoti.putBoolean("noti", true);
                editorNoti.apply();

            }
        });
    }

    //Metode til at køre notifikationer i gennem en enkelt channel med høj priotet
    public void sendNoti() {
        java.util.Calendar calendar = Calendar.getInstance();
        String[] textIndhold = {" ","Welcome to 100days! Remember to check your calender regularly for new information. Tap here to check it out! ",
                "Have you remembered to visit the health facility for anenatal care? Tap to learn more",
                "Have you remembered to take your IFAs? Tap to learn more!",
                "Eating good and nutritious food, is important for you and your baby. Click to learn more!"
                ,"FCHV led Health Mothers Group meetings can be helpfull for you and your child! Tap to learn more.",
                "Tune in to Bhanchhin Aama radio program for free education about you and your cild. Tap to learn when!"};



        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(getApplicationContext(), NotificationReciever.class);
        for(int i=1; i<=6; i++){

            intent.putExtra("indholdText",textIndhold[i]);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),i, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() + (60000L * i),pendingIntent);
        }
    }
}
