package com.example.nepalappgroupb2.Recipe;

import android.content.Context;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class RecipeCardElement {
    private String defaultImg = "madret";

    //searchable
    private String cardTitle;

    private HashSet<String> allSearchableStrings;
    private String allSearchablesStringsAsASingleString;
    private String pdfName;

    public RecipeCardElement(String cardTitle, String pdfName, String[] searchables ){
        this.allSearchableStrings = new HashSet<String>();
        //hvis søgeordene indeholder duplikater så vil HashSet reducere disse til en.
        this.allSearchableStrings.addAll(new ArrayList<String>(Arrays.asList(searchables)));
        this.allSearchableStrings.add(cardTitle);
        this.allSearchablesStringsAsASingleString = TextUtils.join("", allSearchableStrings).toLowerCase();
        this.cardTitle = cardTitle;
        this.pdfName = pdfName;
    }

    public RecipeCardElement(String cardTitle) {
        this.cardTitle = cardTitle;
        //this.backgroundImage = findImage(view, cardTitle);
    }
    public RecipeCardElement() {}

    /**
     * This method takes a string and finds the correct image in drawable.
     * Example:
     * imgName = "Dal Bhat"
     * The method search for "dal_bhat" (underscore insted of space and toLowerCase) in drawable folder.
     * @param imgName is the title for the card
     * @param context is the context for the card. Simply write "getContex()" when using. A View always knows its contex.
     * @return the id for the resource. An int.
     */
    public int getImgIdFromString(String imgName, Context context) {
        String imgToFind;
        if(isDigit(imgName)) {
            imgName = "_" + imgName;
        }
        imgToFind = imgName.replaceAll(" ", "_").toLowerCase();
        return context.getResources().getIdentifier(imgToFind, "drawable", context.getPackageName());
    }

    private boolean isDigit(String title) {
        char c = title.charAt(0);
        return  (c >= '0' && c <= '9');
    }

    public String getCardTitle() {
        return cardTitle;
    }
    public String getDefaultImg() {
        return defaultImg;
    }
    public ArrayList<String> getAllSearchableStrings(){
        return new ArrayList<>( allSearchableStrings);
    }
    public String getPdfName() {
        return pdfName;
    }

    public boolean containsSearchword(CharSequence cs){
        return allSearchablesStringsAsASingleString.contains(cs.toString().toLowerCase());
    }
}
