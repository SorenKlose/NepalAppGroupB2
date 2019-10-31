package com.example.nepalappgroupb2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RecipeActivity extends AppCompatActivity implements View.OnClickListener {


    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_card_layout);


        getSupportFragmentManager().beginTransaction()
                .replace(R.id.recipe_framelayout, new Recipe())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onClick(View view) {
        if(view == btn) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.recipe_framelayout, new Recipe())
                    .addToBackStack(null)
                    .commit();
        }
    }
}