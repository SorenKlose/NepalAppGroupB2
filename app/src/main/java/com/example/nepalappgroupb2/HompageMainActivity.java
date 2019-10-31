package com.example.nepalappgroupb2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

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
            Intent i = new Intent(this, Calender.class);
            startActivity(i);
        }
        if (view == recipesButton) {
            Intent i = new Intent(this, RecipeCardElement.class);
            startActivity(i);
        }
        if (view == comicsButton) {
            Intent i = new Intent(this, ComicActivity.class);
            startActivity(i);
        }
        if (view == quizButton) {
            Intent i = new Intent(this, ProfileActivity.class);
            startActivity(i);
        }
    }
}