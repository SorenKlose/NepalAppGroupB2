package com.example.nepalappgroupb2.Quiz;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nepalappgroupb2.R;

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
