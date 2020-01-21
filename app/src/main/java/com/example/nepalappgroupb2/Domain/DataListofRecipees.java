package com.example.nepalappgroupb2.Domain;

import com.example.nepalappgroupb2.Recipe.RecipeCardElement;

import java.util.ArrayList;

public class DataListofRecipees {
    private ArrayList<RecipeCardElement> data;

    public DataListofRecipees() {
        this.data = new ArrayList<RecipeCardElement>();
    }

    public ArrayList<RecipeCardElement> getData() {
        if (data.size() < 1) {
            setData();
        }
        return data;
    }

    private void setData() {
        // TODO: implement fetching of remote Recipe data

        String[][][] test = {
                new String[][]{
                        new String[]{"Jaulo", "jaulo.pdf"},
                        new String[]{"banan"}
                },
                new String[][]{
                        new String[]{"Nimki", "nikmi.pdf"},
                        new String[]{ "Ingredienses" }
                },
                new String[][]{
                        new String[]{"Sarbottam Pitho", "sarbottam_pitho.pdf"},
                        new String[]{ "salt"}
                },
                new String[][]{
                        new String[]{"recipe 4", "recipenepal_pdf.pdf"},
                        new String[]{ "nødder"}
                },
                new String[][]{
                        new String[]{"recipe 5", "recipenepal_pdf.pdf"},
                        new String[]{"sovs"}
                }
        };

        //creating cardElements for the data
        for (int i = 0; i < test.length; i++) {
            data.add(new RecipeCardElement(test[i][0][0].toLowerCase(), test[i][0][1].toLowerCase(), test[i][1]));
        }

    }
}
