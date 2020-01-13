package com.example.nepalappgroupb2.Domain;

import android.util.ArrayMap;

import com.example.nepalappgroupb2.Recipe.RecipeCardElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataListofRecipees {
    private ArrayList<RecipeCardElement> data;

    public DataListofRecipees() {
        this.data = new ArrayList<RecipeCardElement>();
    }

    public ArrayList<RecipeCardElement> getData() {
        System.out.println("hreellloo:          "+data.size());
        if(data.size() < 1 ){
            setData();
        }
        return data;
    }

    private void setData() {
        // TODO: implement fetching of remote Recipe data

        String[] test = {"Dal Bhat", "Food", "madopskrift", "Peda", "god mad"};

        //creating cardElements for the data
        for(int i = 0; i < test.length; i++) {
            data.add(new RecipeCardElement(test[i], new String[]{test[i].toLowerCase()} ) );
        }

    }
}
