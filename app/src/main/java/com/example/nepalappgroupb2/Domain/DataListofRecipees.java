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
                        new String[]{"Dal Bhat", "recipenepal_pdf.pdf"},
                        new String[]{"banan"}
                },
                new String[][]{
                        new String[]{"Food", "recipenepal_pdf.pdf"},
                        new String[]{ "Ingredienses" }
                },
                new String[][]{
                        new String[]{"madopskrift", "recipenepal_pdf.pdf"},
                        new String[]{ "salt"}
                },
                new String[][]{
                        new String[]{"Peda", "recipenepal_pdf.pdf"},
                        new String[]{ "nødder"}
                },
                new String[][]{
                        new String[]{"god mad", "recipenepal_pdf.pdf"},
                        new String[]{"sovs"}
                }
        };

        //creating cardElements for the data
        for (int i = 0; i < test.length; i++) {
            data.add(new RecipeCardElement(test[i][0][0].toLowerCase(), test[i][0][1].toLowerCase(), test[i][1]));
        }

    }
}
