package com.example.nepalappgroupb2.Recipe;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

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
        this.allSearchablesStringsAsASingleString = TextUtils.join("", allSearchableStrings);
        this.cardTitle = cardTitle;
        this.pdfName = pdfName;
    }

    public RecipeCardElement(String cardTitle) {
        this.cardTitle = cardTitle;
        //this.backgroundImage = findImage(view, cardTitle);
    }
    public RecipeCardElement() {}

    /**
     * This method takes a title and finds the correct image in drawable.
     * Example:
     * cardTitle = "Dal Bhat"
     * The method search for "dal_bhat" (underscore insted of space and toLowerCase) in drawable folder.
     * @param cardTitle is the title for the card
     * @param context is the context for the card. Simply write "getContex()" when using. A View always knows its contex.
     * @return the id for the resource. An int.
     */
    public int getBgImgIDFromTitle(String cardTitle, Context context) {
        String imgToFind;
        if(isDigit(cardTitle)) {
            cardTitle = "_" + cardTitle;
        }
        imgToFind = cardTitle.replaceAll(" ", "_").toLowerCase();
        return context.getResources().getIdentifier(imgToFind, "drawable", context.getPackageName());
    }

    private boolean isDigit(String title) {
        char c = title.charAt(0);
        return  (c >= '0' && c <= '9');
    }

    // TODO: 24-10-2019 fix denne, hvis den overhoved skal bruges?
    public ImageView findImage(View view, String cardTitle) {
        ImageView imgToReturn;
        try {
            imgToReturn  = view.findViewById(getBgImgIDFromTitle(cardTitle, view.getContext()));
        } catch (NullPointerException e) {
            imgToReturn = view.findViewById(getBgImgIDFromTitle("madret", view.getContext()));
        }
        return imgToReturn;
    }

    // TODO: 24-10-2019 vi kan også slå de to metoder sammen. Ved ikke som vi kommer til at bruge dem hver især


//    public ImageView getBackgroundImage() {
//        return backgroundImage;
//    }
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
