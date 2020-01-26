package com.example.nepalappgroupb2.Homepage;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;

import com.crashlytics.android.Crashlytics;
import com.example.nepalappgroupb2.Calendar.CalenderActivity;
import com.example.nepalappgroupb2.Domain.Afspilning;
import com.example.nepalappgroupb2.Widget.CalendarWidgetLogic;
import com.example.nepalappgroupb2.Comic.ComicActivity;
import com.example.nepalappgroupb2.Domain.DataFromSheets;
import com.example.nepalappgroupb2.Profile.ProfileActivity;
import com.example.nepalappgroupb2.Progress.ProgressBarFragment;
import com.example.nepalappgroupb2.Quiz.QuizActivity;
import com.example.nepalappgroupb2.R;
import com.example.nepalappgroupb2.Recipe.RecipeActivity;

import java.util.concurrent.ExecutionException;

import io.fabric.sdk.android.Fabric;


public class HomepageMainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String CHANNEL_ID = "channel";
    Button calenderButton;
    Button recipesButton;
    Button comicsButton;
    Button quizButton;
    private ProgressBarFragment progressBar;
    private NotificationManagerCompat notiManager;
    Button profileButton;
    TextView pregnancyText;
    CalendarWidgetLogic widgetLogic = new CalendarWidgetLogic();
    DataFromSheets db = new DataFromSheets();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences sp = getSharedPreferences("profile", Context.MODE_PRIVATE);
        //HVIS I GERNE VIL HAVE PROFILE DIALOGGEN TIL AT KOMME FREM HVER GANG; SÅ SKAL NEDESTÅENDE KØRES - A
        SharedPreferences mPrefs = getSharedPreferences("popupscreen", Context.MODE_PRIVATE);
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
        pregnancyText = findViewById(R.id.homepageTitel);
        progressBar = (ProgressBarFragment) getSupportFragmentManager().findFragmentById(R.id.progressBar);

        findViewById(R.id.højtlæsning).setOnClickListener(this);

        calenderButton.setOnClickListener(this);
        recipesButton.setOnClickListener(this);
        comicsButton.setOnClickListener(this);
        quizButton.setOnClickListener(this);
        profileButton.setOnClickListener(this);



        try {
            new AsyncTask() {
                @Override
                protected Object doInBackground(Object[] objects) {
                    try {
                        db.fromSheets(getBaseContext());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return 0;
                }
            }.execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    int[] knapLyde = {
            R.raw.button_health_development,
            R.raw.button_recipes,
            R.raw.button_usefull_information,
            R.raw.button_questionanswer,
            R.raw.button_profile,
    };

    int[] knapper = {
            R.id.btnCalendar,
            R.id.btnRecipe,
            R.id.btnComics,
            R.id.btnQuiz,
            R.id.btnProfile,
    };

    int hjælpKnapNummer;

    @Override
    protected void onPause() {
        super.onPause();
        Afspilning.stop();
        findViewById(knapper[hjælpKnapNummer]).animate().scaleX(1).scaleY(1).setDuration(1);
        findViewById(R.id.højtlæsning).setEnabled(true);
    }

    private void visNæsteHjælp() {
        hjælpKnapNummer++;
        if (hjælpKnapNummer>=knapLyde.length) {
            findViewById(R.id.højtlæsning).setEnabled(true);
            return;
        }
        findViewById(knapper[hjælpKnapNummer]).animate().scaleX(1.2f).scaleY(1.2f).setDuration(500);
        Afspilning.start(MediaPlayer.create(this, knapLyde[hjælpKnapNummer]), new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (isDestroyed()) return;
                findViewById(knapper[hjælpKnapNummer]).animate().scaleX(1).scaleY(1).setDuration(500).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        visNæsteHjælp();
                    }
                });
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.højtlæsning) {
            view.setEnabled(false);
            hjælpKnapNummer = -1;
            visNæsteHjælp();
        }
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
        if (view == profileButton) {
            Intent i = new Intent(this, ProfileActivity.class);
            startActivity(i);
        }
    }

    private void setCrashReporting() {

        boolean EMULATOR = Build.PRODUCT.contains("sdk") || Build.MODEL.contains("Emulator");
        System.out.println("this is an emulator: " + EMULATOR);
        if (EMULATOR) {

            Crashlytics.setBool("emulator", true);
        }
        Fabric.with(this, new Crashlytics());
        //Crashlytics.getInstance().crash(); // forcer et crash

        // hvis emulatoren ikke har adgang til internettet så den ikke kan sende crash-rapporter,
        // så prøv at lave en 'Cold Boot Now' på emulatoren
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        //sets the top text on the homepage
        pregnancyText.setText(String.format(getString(R.string.progressbar_text), progressBar.monthsOld(this)));
        progressBar = (ProgressBarFragment) getSupportFragmentManager().findFragmentById(R.id.progressBar);
        progressBar.update();

        //updating the widget with possible new text
        int month = progressBar.monthsOld(this);
        System.out.println("month er her: " + month);
        try {
            widgetLogic.updateWidget(this, month);
        } catch (IndexOutOfBoundsException e) {
            //no internet
        }

    }

}
