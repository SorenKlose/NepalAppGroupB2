package com.example.nepalappgroupb2.Domain;

import android.os.AsyncTask;

import com.crashlytics.android.Crashlytics;
import com.example.nepalappgroupb2.Recipe.RecipeCardElement;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

/* Denne Class er tiltængt at skulle agere kobling mellem Frontend Class'  (Activitys og Fragments )
 og så Class' der håndterer kommunikationen med backenden f.eks. "DataFromSheets".
 Den bør ikke indeholde andet end getter-funktioner, der hendter data fra Domain
 og funktioner der sender data til Domain */

public class DataService {
    private static DataFromSheets dataFromSheets = new DataFromSheets();
    private static DataListofRecipees dataRecipees = new DataListofRecipees();

    private DataService() {
    }


    public static List<Integer> getMonthsFromData() {
        // TODO: Find how to get some data from DataFromSheets instead of:
        final List<Integer> returnList = new ArrayList<>();
        if (dataFromSheets.getMapSize() > 0) {
            returnList.addAll(dataFromSheets.getMonths());
        } else {

            new AsyncTask() {
                @Override
                protected Object doInBackground(Object[] objects) {
                    try {
                        dataFromSheets.fromSheets();
                    } catch (Exception e) {
                        e.printStackTrace();
                        //sendes til 'non-fatal issues' i Crashlytics-console
                        Crashlytics.logException(e);

                    }
                    return null;
                }
                @Override
                protected void onPostExecute(Object o) {
                    returnList.addAll(dataFromSheets.getMonths());
                }
            }.execute();

        }
        return returnList;
    }

    public static List<String> getMessageOfMonth(String language, int monthnumber) {
        List<String> list = new ArrayList<String>();
        switch (language) {

            case "english":
                list.addAll(dataFromSheets.getWithMonth(DataFromSheets.Headers.MsgEng, monthnumber));
                break;
            case "1337":
            default:
                list.addAll(dataFromSheets.getWithMonth(DataFromSheets.Headers.MsgNep, monthnumber));
                break;
        }
        return list;
    }

    public static List<RecipeCardElement> getListOfRecipes() {
        return dataRecipees.getData();
    }
}
