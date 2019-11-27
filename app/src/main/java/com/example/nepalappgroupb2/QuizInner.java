package com.example.nepalappgroupb2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class QuizInner extends AppCompatActivity implements View.OnClickListener {

    Button knap1, knap2, knap3, knap4;
    TextView questions;

    private QuizQuestionsOne question = new QuizQuestionsOne();
    private String choise;
    private int questionLength = question.questions.length;

    Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inner_quiz_layout);

        knap1 = findViewById(R.id.knap1);
        knap1.setOnClickListener(this);
        knap2 = findViewById(R.id.knap2);
        knap2.setOnClickListener(this);
        knap3 = findViewById(R.id.knap3);
        knap3.setOnClickListener(this);
        knap4 = findViewById(R.id.knap4);
        knap4.setOnClickListener(this);

        questions = findViewById(R.id.question);

        random = new Random();
        NextRandomQuestion(random.nextInt(questionLength));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.knap1:
                if (knap1.getText() == choise){
                    Toast.makeText(QuizInner.this, "Correct!", Toast.LENGTH_SHORT).show();
                    NextRandomQuestion(random.nextInt(questionLength));
                }else {
                    Toast.makeText(QuizInner.this, "Wrong!", Toast.LENGTH_SHORT).show();
                    NextRandomQuestion(random.nextInt(questionLength));
                }
                break;

            case R.id.knap2:
                if (knap2.getText() == choise){
                    Toast.makeText(QuizInner.this, "Correct!", Toast.LENGTH_SHORT).show();
                    NextRandomQuestion(random.nextInt(questionLength));
                }else {
                    Toast.makeText(QuizInner.this, "Wrong!", Toast.LENGTH_SHORT).show();
                    NextRandomQuestion(random.nextInt(questionLength));
                }
                break;

            case R.id.knap3:
                if (knap3.getText() == choise){
                    Toast.makeText(QuizInner.this, "Correct!", Toast.LENGTH_SHORT).show();
                    NextRandomQuestion(random.nextInt(questionLength));
                }else {
                    Toast.makeText(QuizInner.this, "Wrong!", Toast.LENGTH_SHORT).show();
                    NextRandomQuestion(random.nextInt(questionLength));
                }
                break;

            case R.id.knap4:
                if (knap4.getText() == choise){
                    Toast.makeText(QuizInner.this, "Correct!", Toast.LENGTH_SHORT).show();
                    NextRandomQuestion(random.nextInt(questionLength));
                }else {
                    Toast.makeText(QuizInner.this, "Wrong!", Toast.LENGTH_SHORT).show();
                    NextRandomQuestion(random.nextInt(questionLength));
                }
                break;
        }
    }
    public void NextRandomQuestion(int number){
        questions.setText(question.getQuestions(number));
        knap1.setText(question.getChoises1(number));
        knap2.setText(question.getChoises2(number));
        knap3.setText(question.getChoises3(number));
        knap4.setText(question.getChoises4(number));
        choise = question.getCorrectChoise(number);
    }
}
