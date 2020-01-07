package com.example.nepalappgroupb2.Homepage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.nepalappgroupb2.Comic.ComicActivity;
import com.example.nepalappgroupb2.Calendar.*;
import com.example.nepalappgroupb2.Profile.ProfileActivity;
import com.example.nepalappgroupb2.Quiz.QuizActivity;
import com.example.nepalappgroupb2.R;
import com.example.nepalappgroupb2.Recipe.RecipeActivity;

public class HompageMainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView calenderButton;
    ImageView recipesButton;
    ImageView comicsButton;
    ImageView quizButton;

    ImageView profileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calenderButton = (ImageView) findViewById(R.id.calender);
        recipesButton = (ImageView) findViewById(R.id.recipes);
        comicsButton = (ImageView) findViewById(R.id.comics);
        quizButton = (ImageView) findViewById(R.id.quiz);
        profileButton = (ImageView) findViewById(R.id.profile);

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
}