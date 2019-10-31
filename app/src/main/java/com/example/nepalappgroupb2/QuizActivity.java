package com.example.nepalappgroupb2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class QuizActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.quiz_frameLayout, new Quiz())
                .commit();
    }
}
