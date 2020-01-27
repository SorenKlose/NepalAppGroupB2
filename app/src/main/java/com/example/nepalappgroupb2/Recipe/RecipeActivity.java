package com.example.nepalappgroupb2.Recipe;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.nepalappgroupb2.Domain.SearchFilterFragment;
import com.example.nepalappgroupb2.Domain.searchWordProvider;
import com.example.nepalappgroupb2.R;

public class RecipeActivity extends AppCompatActivity
        implements View.OnFocusChangeListener,
                    Observer<String>,
                    searchWordProvider {

    private final MutableLiveData<String> searchWord = new MutableLiveData<>();

    RecipeFrag recipeListFrag;
    RecipePdfViewFrag recipePdfFrag;
    SearchFilterFragment searchFrag;
    TextView titleView;
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
        getSearchWord().observe(this,this);
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
                .remove(searchFrag)
                .commit();
        searchFrag.getSearchEditText().clearFocus();
    }
    @Override
    public void onStart(){
        super.onStart();searchFrag.getSearchEditText().setOnFocusChangeListener(this);

    }
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(hasFocus){
            titleView.setVisibility(View.GONE);
        }
    }

    @Override
    public MutableLiveData<String> getSearchWord() {
        return searchWord;
    }

    @Override
    public void onChanged(String s) {
        recipeListFrag.filterList(s);
    }
}