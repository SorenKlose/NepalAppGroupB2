package com.example.nepalappgroupb2.Homepage;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ProgressBar;
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
    private View højtlæsning;
    Handler handler = new Handler();

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

        højtlæsning = findViewById(R.id.højtlæsning);
        højtlæsning.setOnClickListener(this);

        calenderButton.setOnClickListener(this);
        recipesButton.setOnClickListener(this);
        comicsButton.setOnClickListener(this);
        quizButton.setOnClickListener(this);
        profileButton.setOnClickListener(this);

        // Trykker brugeren på volumen op/ned i denne aktivitet skal det altid styre lydstyrken af medieafspilleren
        // (kalder man ikke dette så vil volumen op/ned styre telefonen RINGETONEs lydstyrke
        // og kun lige mens der er en lyd der spiller vil det være lydens lydstyrke der justeres)
        setVolumeControlStream(AudioManager.STREAM_MUSIC);


        calenderButton.setEnabled(false);
        final ProgressBar kalenderProgressBar = (ProgressBar) findViewById(R.id.kalenderProgressBar);

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

          @Override
          protected void onPostExecute(Object o) {
            calenderButton.setEnabled(true);
            kalenderProgressBar.setVisibility(View.GONE);
          }
        }.execute();

        // De 3 første gange kommer der en intro til hvad knapperne gør
        final int højtlæsningSket = PreferenceManager.getDefaultSharedPreferences(this).getInt("højtlæsningSket", 0);
        if (højtlæsningSket<3) {
          startHøjtlæsningAfSigSelv = new Runnable() {
            @Override
            public void run() {
              PreferenceManager.getDefaultSharedPreferences(getApplication()).edit().putInt("højtlæsningSket", højtlæsningSket + 1).apply();
              startHøjtlæsning();
            }
          };
          handler.postDelayed(startHøjtlæsningAfSigSelv, 8000);
        }
    }

    Runnable startHøjtlæsningAfSigSelv = null;


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
        stopHøjtlæsning();
    }

    private void visNæsteHjælp() {
        hjælpKnapNummer++;
        if (hjælpKnapNummer>=knapLyde.length) {
            stopHøjtlæsning();
            return;
        }
        findViewById(knapper[hjælpKnapNummer]).animate().scaleX(1.2f).scaleY(1.2f).translationZ(100).rotation(-10).setDuration(500);
        Afspilning.start(MediaPlayer.create(this, knapLyde[hjælpKnapNummer]), new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (isDestroyed()) return;
                findViewById(knapper[hjælpKnapNummer]).animate().scaleX(1).scaleY(1).translationZ(0).rotation(0).setDuration(500).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        visNæsteHjælp();
                    }
                });
            }
        });
    }

  public void startRysteAnimation(View view) {
    Animation anim = new RotateAnimation(-2,2,view.getWidth()/2,view.getHeight()/2);
    anim.setDuration(100);
    anim.setRepeatMode(Animation.REVERSE);
    anim.setRepeatCount(Animation.INFINITE);
    view.startAnimation(anim);
  }


  private void startHøjtlæsning() {
    højtlæsning.setEnabled(false);
    hjælpKnapNummer = -1;
    Afspilning.tjekVolumenErMindst(this, 20);
    startRysteAnimation(højtlæsning);
    visNæsteHjælp();
  }

  private void stopHøjtlæsning() {
    Afspilning.stop();
    if (hjælpKnapNummer<knapLyde.length) findViewById(knapper[hjælpKnapNummer]).animate().scaleX(1).scaleY(1).translationZ(0).rotation(0).setDuration(1);
    højtlæsning.setEnabled(true);
    højtlæsning.setAnimation(null); // stop rysteanimation
  }

    @Override
    public void onClick(View view) {
        handler.removeCallbacks(startHøjtlæsningAfSigSelv);
        if (view.getId()==R.id.højtlæsning) {
            startHøjtlæsning();
            PreferenceManager.getDefaultSharedPreferences(this).edit().putInt("højtlæsningSket", 1000).apply();
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
