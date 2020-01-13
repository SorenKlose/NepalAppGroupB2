package com.example.nepalappgroupb2.Recipe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import android.os.Bundle;
import android.view.WindowManager;

import com.example.nepalappgroupb2.R;
import com.example.nepalappgroupb2.Domain.searchWordProvider;

public class RecipeActivity extends AppCompatActivity implements searchWordProvider {

    private final MutableLiveData<String> searchWord = new MutableLiveData<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_act);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.recipe_framelayout, new RecipeListFragment())
                .commit();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    public MutableLiveData<String> getSearchWord() {
        return searchWord;
    }
}