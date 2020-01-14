package com.example.nepalappgroupb2.Homepage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.crashlytics.android.Crashlytics;
import com.example.nepalappgroupb2.Comic.ComicActivity;
import com.example.nepalappgroupb2.Calendar.*;
import com.example.nepalappgroupb2.Profile.ProfileActivity;
import com.example.nepalappgroupb2.Progress.ProgressBarFragment;
import com.example.nepalappgroupb2.Quiz.QuizActivity;
import com.example.nepalappgroupb2.R;
import com.example.nepalappgroupb2.Recipe.RecipeActivity;

import java.sql.SQLOutput;

import io.fabric.sdk.android.Fabric;

public class HompageMainActivity extends AppCompatActivity implements View.OnClickListener {

    Button calenderButton;
    Button recipesButton;
    Button comicsButton;
    Button quizButton;
    private ProgressBarFragment progressBar;
    Button profileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCrashReporting();

        setContentView(R.layout.activity_main);

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
        System.out.println("this this run on an emulator: "+EMULATOR);
        if (EMULATOR) {
            Fabric.with(this, new Crashlytics());
        }

        // Crashlytics.getInstance().crash(); // forcer et crash

        // hvis emulatoren ikke har adgang til internettet så den kan sende crash-rapporter,
        // så prøv at lave en 'Cold Boot Now' på emulatoren


    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        progressBar = (ProgressBarFragment) getSupportFragmentManager().findFragmentById(R.id.progressBar);
        progressBar.update();

    }
}