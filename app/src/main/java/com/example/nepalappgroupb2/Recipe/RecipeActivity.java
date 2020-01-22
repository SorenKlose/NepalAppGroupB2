package com.example.nepalappgroupb2.Recipe;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nepalappgroupb2.R;

public class RecipeActivity extends AppCompatActivity {

    RecipeFrag recipeListFrag;
    RecipePdfViewFrag recipePdfFrag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_act);

        if(savedInstanceState == null){
            recipeListFrag = new RecipeFrag();
            recipePdfFrag = new RecipePdfViewFrag();
        }

        getSupportFragmentManager().beginTransaction()
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
}