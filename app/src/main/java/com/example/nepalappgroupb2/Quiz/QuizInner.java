package com.example.nepalappgroupb2.Quiz;

import android.animation.Animator;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.example.nepalappgroupb2.R;

import java.util.Locale;

public class QuizInner extends AppCompatActivity implements View.OnClickListener {

    Button button1, button2, button3, button4;
    TextView questions;
    LottieAnimationView checkmark, errorcross;
    TextToSpeech textToSpeech;

    private QuizQuestionsOne question = new QuizQuestionsOne();
    private String choice;
    private int questionLength = question.questions.length;
    private int questionNumber = 0;
    private int correctChoice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inner_quiz_layout);

        button1 = findViewById(R.id.button1);
        button1.setOnClickListener(this);
        button2 = findViewById(R.id.button2);
        button2.setOnClickListener(this);
        button3 = findViewById(R.id.button3);
        button3.setOnClickListener(this);
        button4 = findViewById(R.id.button4);
        button4.setOnClickListener(this);

        questions = findViewById(R.id.question);

        checkmark = findViewById(R.id.lottieCheckMark);
        errorcross = findViewById(R.id.lottieErrorCross);

        nextQuestion();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button1:
                if (button1.getText() == choice){
                 answerCorrect();
                } else {
                    answerIncorrect();
                } break;

            case R.id.button2:
                if (button2.getText() == choice){
                   answerCorrect();
                }else {
                    answerIncorrect();
                } break;

            case R.id.button3:
                if (button3.getText() == choice){
                    answerCorrect();
                }else {
                    answerIncorrect();
                } break;

            case R.id.button4:
                if (button4.getText() == choice){
                    answerCorrect();
                }else {
                    answerIncorrect();
                } break;
        }
    }

    public void nextQuestion(){
        questions.setText(question.getQuestions(questionNumber));
        button1.setText(question.getChoices1(questionNumber));
        button2.setText(question.getChoices2(questionNumber));
        button3.setText(question.getChoices3(questionNumber));
        button4.setText(question.getChoices4(questionNumber));
        choice = question.getCorrectChoice(questionNumber);

        TTSstop();
        TTS(question.getQuestions(questionNumber));

        questionNumber++;

        if (questionNumber == questionLength){
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("Congratulations!")
            .setMessage("You have " +correctChoice+ " out of 10 correct answers!")
            .setNegativeButton("Back", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(getBaseContext(), QuizActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
            AlertDialog alert = alertDialog.create();
            alert.setCanceledOnTouchOutside(false);
            alert.show();
        }
    }

    public void answerCorrect(){
        errorStop();

        checkmark.setVisibility(View.VISIBLE);
        checkmark.playAnimation();

        checkmark.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                checkmark.setVisibility(View.VISIBLE);
            }
            @Override
            public void onAnimationEnd(Animator animation) {
                checkmark.setVisibility(View.GONE);
            }
            @Override
            public void onAnimationCancel(Animator animation) {
                checkmark.clearAnimation();
            }
            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });

        correctChoice++;
        nextQuestion();
    }

    public void answerIncorrect(){
        checkStop();

        errorcross.setVisibility(View.VISIBLE);
        errorcross.playAnimation();

        errorcross.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                errorcross.setVisibility(View.VISIBLE);
            }
            @Override
            public void onAnimationEnd(Animator animation) {
                errorcross.setVisibility(View.GONE);
            }
            @Override
            public void onAnimationCancel(Animator animation) {

            }
            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        nextQuestion();
    }

    public void TTS(final String text){
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR){
                    textToSpeech.setLanguage(Locale.ENGLISH);

                    textToSpeech.speak(text,TextToSpeech.QUEUE_FLUSH,null,"1");
                    }
                }
        });
    }
    public void TTSstop(){
        if (textToSpeech != null){
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }
    public void errorStop(){
        if (errorcross.isAnimating()){
            errorcross.cancelAnimation();
            errorcross.setVisibility(View.INVISIBLE);
        }
    }
    public void checkStop() {
        if (checkmark.isAnimating()) {
            checkmark.cancelAnimation();
            checkmark.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onPause() {
        if (textToSpeech != null){
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onPause();
    }
}
