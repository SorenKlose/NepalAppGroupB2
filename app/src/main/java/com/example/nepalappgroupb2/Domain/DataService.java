package com.example.nepalappgroupb2.Domain;

import com.example.nepalappgroupb2.Recipe.RecipeCardElement;

import java.util.ArrayList;
import java.util.List;

/* Denne Class er tiltængt at skulle agere kobling mellem Frontend Class'  (Activitys og Fragments )
 og så Class' der håndterer kommunikationen med backenden f.eks. "DataFromSheets".
 Den bør ikke indeholde andet end getter-funktioner, der hendter data fra Domain
 og funktioner der sender data til Domain */

public class DataService {
    static DataFromSheets dataFromSheets = new DataFromSheets();
    static DataListofRecipees dataRecipees = new DataListofRecipees();

    private DataService() { }


    public static List<String> getMessages(){
        // TODO: Find how to get some data from DataFromSheets instead of:
        return new ArrayList<String>();
    }
    public static List<RecipeCardElement> getListOfRecipes(){
        return dataRecipees.getData();
    }
}
