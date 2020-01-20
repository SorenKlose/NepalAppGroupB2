package com.example.nepalappgroupb2.Homepage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.crashlytics.android.Crashlytics;
import com.example.nepalappgroupb2.Comic.ComicActivity;
import com.example.nepalappgroupb2.Calendar.*;
import com.example.nepalappgroupb2.Domain.NotificationReciever;
import com.example.nepalappgroupb2.Profile.ProfileActivity;
import com.example.nepalappgroupb2.Progress.ProgressBarFragment;
import com.example.nepalappgroupb2.Quiz.QuizActivity;
import com.example.nepalappgroupb2.R;
import com.example.nepalappgroupb2.Recipe.RecipeActivity;

import java.util.Calendar;
import java.util.Date;

import java.sql.SQLOutput;

import io.fabric.sdk.android.Fabric;

public class HompageMainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String CHANNEL_ID = "channel";
    Button calenderButton;
    Button recipesButton;
    Button comicsButton;
    Button quizButton;
    private ProgressBarFragment progressBar;
    private NotificationManagerCompat notiManager;
    Button profileButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences sp = getSharedPreferences("profile", Context.MODE_PRIVATE);
        int month = sp.getInt("month", -1);
        System.out.println("her er måned: " + month);
        Date hej = new Date(System.currentTimeMillis());
        System.out.println("måneder siden 1970: ");

        //HVIS I GERNE VIL HAVE PROFILE DIALOGGEN TIL AT KOMME FREM HVER GANG; SÅ SKAL NEDESTÅENDE KØRES - A
        SharedPreferences mPrefs = getSharedPreferences("popupscreen",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mPrefs.edit();
        //editor.clear();
        //editor.commit();



        super.onCreate(savedInstanceState);
        setCrashReporting();

        setContentView(R.layout.activity_main);

        notiManager = NotificationManagerCompat.from(this);
        calenderButton = (Button) findViewById(R.id.btnCalendar);
        recipesButton = (Button) findViewById(R.id.btnRecipe);
        comicsButton = (Button) findViewById(R.id.btnComics);
        quizButton = (Button) findViewById(R.id.btnQuiz);
        profileButton = (Button) findViewById(R.id.btnProfile);

        calenderButton.setOnClickListener(this);
        recipesButton.setOnClickListener(this);
        comicsButton.setOnClickListener(this);
        quizButton.setOnClickListener(this);
        profileButton.setOnClickListener(this);

        //Notifikation hvert minut, selvom det måske kommer lidt random?? MEN KØR METODEN NEDENFOR HVIS DET SKAL TESTES.
       // sendNoti();



    }

    @Override
    public void onClick(View view) {
        if (view == calenderButton) {
            Intent i = new Intent(this, CalenderActivity.class);
            startActivity(i);
        }
        if (view == recipesButton) {
            Intent i = new Intent(this, RecipeActivity.class);
            startActivity(i);
        }
        if (view == comicsButton) {
            Intent i = new Intent(this, ComicActivity.class);
            startActivity(i);
        }
        if (view == quizButton) {
            Intent i = new Intent(this, QuizActivity.class);
            startActivity(i);
        }
        if(view == profileButton){
            Intent i = new Intent(this, ProfileActivity.class);
            startActivity(i);
        }
    }

    private void setCrashReporting(){

        boolean EMULATOR = Build.PRODUCT.contains("sdk") || Build.MODEL.contains("Emulator");
        System.out.println("this is an emulator: "+EMULATOR);
        if (EMULATOR) {
            Fabric.with(this, new Crashlytics());
        }

        // Crashlytics.getInstance().crash(); // forcer et crash

        // hvis emulatoren ikke har adgang til internettet så den ikke kan sende crash-rapporter,
        // så prøv at lave en 'Cold Boot Now' på emulatoren


    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        progressBar = (ProgressBarFragment) getSupportFragmentManager().findFragmentById(R.id.progressBar);
        progressBar.update();

    }

    //Metode til at køre notifikationer i gennem en enkelt channel med høj priotet
    public void sendNoti() {
        java.util.Calendar calendar = Calendar.getInstance();

       //  calendar.set(Calendar.HOUR_OF_DAY,16);
       //  calendar.set(Calendar.MINUTE,(int) minuteFromNow);

        Intent intent = new Intent(getApplicationContext(), NotificationReciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),60000L,pendingIntent);
          }
}