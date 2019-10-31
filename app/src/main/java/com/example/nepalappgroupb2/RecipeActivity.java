package com.example.nepalappgroupb2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RecipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_act);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.recipe_framelayout, new Recipe())
                .commit();
    }


}