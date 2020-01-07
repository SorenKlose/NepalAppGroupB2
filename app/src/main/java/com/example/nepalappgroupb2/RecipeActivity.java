package com.example.nepalappgroupb2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class RecipeActivity extends AppCompatActivity implements searchWordProvider{
    

    private final MutableLiveData<String> searchWord = new MutableLiveData<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_act);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.recipe_framelayout, new Recipe())
                .commit();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    public MutableLiveData<String> getSearchWord() {
        return searchWord;
    }
}