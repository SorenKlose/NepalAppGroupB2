package com.example.nepalappgroupb2.Recipe;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nepalappgroupb2.Domain.SearchFilterFragment;
import com.example.nepalappgroupb2.R;

public class RecipeActivity extends AppCompatActivity implements View.OnFocusChangeListener {

    RecipeFrag recipeListFrag;
    RecipePdfViewFrag recipePdfFrag;
    SearchFilterFragment searchFrag;
    TextView titleView;
    EditText input;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        if(savedInstanceState == null){
            recipeListFrag = new RecipeFrag();
            recipePdfFrag = new RecipePdfViewFrag();
            searchFrag = new SearchFilterFragment();
            titleView = findViewById(R.id.textView6);

        }

        getSupportFragmentManager().beginTransaction()
                .add(R.id.recipe_searchbar, searchFrag)
                .add(R.id.recipe_framelayout, recipeListFrag)
                .commit();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    public void openPdf(String pdfName){
        Bundle pdfInfo = new Bundle();
        pdfInfo.putString("filename", pdfName);
        recipePdfFrag.setArguments(pdfInfo);
        getSupportFragmentManager().beginTransaction().addToBackStack(null)
                .add(R.id.recipe_framelayout, recipePdfFrag)
                .commit();
    }
    @Override
    public void onStart(){
        super.onStart();searchFrag.getSearchEditText().setOnFocusChangeListener(this);

    }
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        titleView.setVisibility(View.GONE);
    }
}