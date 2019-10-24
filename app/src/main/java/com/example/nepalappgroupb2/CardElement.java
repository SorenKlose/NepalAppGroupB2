package com.example.nepalappgroupb2;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

public class CardElement {
    private String defaultImg = "madret";
    private String cardTitle;
    //private ImageView backgroundImage;

    public CardElement(String cardTitle) {
        this.cardTitle = cardTitle;
        //this.backgroundImage = findImage(view, cardTitle);
    }
    public CardElement() {}

    public int getBgImgIDFromTitle(String cardTitle, Context context) {
        String imgToFind = cardTitle.replaceAll(" ", "_").toLowerCase();
        return context.getResources().getIdentifier(imgToFind, "drawable", context.getPackageName());
    }

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
}
