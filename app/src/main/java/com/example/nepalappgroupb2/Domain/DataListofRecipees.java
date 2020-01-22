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
                        new String[]{"Daal","Rice", " Vegetables"}
                },
                new String[][]{
                        new String[]{"Nimki", "nikmi.pdf"},
                        new String[]{ "Wheat", "Flour", "Maida", "Ajwain", "Onion Seeds", "Baking Soda", "Oil", "Ghee", "Frying" }
                },
                new String[][]{
                        new String[]{"Sarbottam Pitho", "sarbottam_pitho.pdf"},
                        new String[]{ "Soybean", "Wheat", "Maize", "milk" }
                },
                new String[][]{
                        new String[]{"recipe 4", "recipenepal_pdf.pdf"},
                        new String[]{ "Daal","Rice", " Vegetables", "Wheat", "Flour", "Maida", "Ajwain", "Onion Seeds", "Baking Soda", "Oil", "Ghee", "Frying","Soybean", "Wheat", "Maize", "milk"  }
                },
                new String[][]{
                        new String[]{"recipe 5", "recipenepal_pdf.pdf"},
                        new String[]{ "Daal","Rice", " Vegetables", "Wheat", "Flour", "Maida", "Ajwain", "Onion Seeds", "Baking Soda", "Oil", "Ghee", "Frying","Soybean", "Wheat", "Maize", "milk" }
                }
        };

        //creating cardElements for the data
        for (int i = 0; i < test.length; i++) {
            data.add(new RecipeCardElement(test[i][0][0].toLowerCase(), test[i][0][1].toLowerCase(), test[i][1]));
        }

    }
}
