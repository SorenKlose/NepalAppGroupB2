package com.example.nepalappgroupb2.Quiz;

import android.animation.Animator;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.example.nepalappgroupb2.R;

import java.util.Random;

public class QuizInner extends AppCompatActivity implements View.OnClickListener {

    Button knap1, knap2, knap3, knap4;
    TextView questions;
    LottieAnimationView checkmark, errorcross;

    private QuizQuestionsOne question = new QuizQuestionsOne();
    private String choice;
    private int questionLength = question.questions.length;
    private int questionNumber = 0;
    private int correctChoice = 0;

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

        checkmark = findViewById(R.id.lottieCheckMark);
        errorcross = findViewById(R.id.lottieErrorCross);

        NextQuestion();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.knap1:
                if (knap1.getText() == choice){
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
                        }
                        @Override
                        public void onAnimationRepeat(Animator animation) {
                        }
                    });

                    correctChoice++;
                    NextQuestion();
                }else {
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

                    NextQuestion();
                }
                break;

            case R.id.knap2:
                if (knap2.getText() == choice){
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
                        }
                        @Override
                        public void onAnimationRepeat(Animator animation) {
                        }
                    });

                    correctChoice++;
                    NextQuestion();
                }else {
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

                    NextQuestion();
                }
                break;

            case R.id.knap3:
                if (knap3.getText() == choice){
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
                        }
                        @Override
                        public void onAnimationRepeat(Animator animation) {
                        }
                    });

                    correctChoice++;
                    NextQuestion();
                }else {
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

                    NextQuestion();
                }
                break;

            case R.id.knap4:
                if (knap4.getText() == choice){
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
                        }
                        @Override
                        public void onAnimationRepeat(Animator animation) {
                        }
                    });

                    correctChoice++;
                    NextQuestion();
                }else {
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

                    NextQuestion();
                }
                break;
        }
    }

    public void NextQuestion(){
        questions.setText(question.getQuestions(questionNumber));
        knap1.setText(question.getChoices1(questionNumber));
        knap2.setText(question.getChoices2(questionNumber));
        knap3.setText(question.getChoices3(questionNumber));
        knap4.setText(question.getChoices4(questionNumber));
        choice = question.getCorrectChoice(questionNumber);

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
                }
            });

            AlertDialog alert = alertDialog.create();
            alert.setCanceledOnTouchOutside(false);
            alert.show();
        }
    }
}
