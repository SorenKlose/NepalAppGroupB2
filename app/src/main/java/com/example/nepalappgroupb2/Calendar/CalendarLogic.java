package com.example.nepalappgroupb2.Calendar;

import android.content.Context;

import java.util.List;

public class CalendarLogic {

    /**
     * A save check for numbers. Will return nepalise numbers if
     * language is NOT danish or endlish
     * @param num number you want to use
     * @param language language running on the phone (Locale.getDefault().getDisplayLanguage())
     * @return the number as a String either as western number or nepali number
     */
    public String getNumForCurLanguage(Context context, int num, String language) {
        if(!language.equals("dansk") && !language.equals("English")) {
            return getNepaliNum(context, num);
        } else {
            return String.valueOf(num);
        }
    }

    /**
     * combining nepali numbers to get bigger numbers. Assuming all nepali numbers can be found by
     * combining them. Fx if we have 123 then we find 1, 2, and 3 in nepali and combining them
     *
     * @param num the number to translate to nepali
     * @return the nepali number as a String
     */
    private String getNepaliNum(Context context, int num) {
        StringBuilder valueName = new StringBuilder();
        String numbAsString = Integer.toString(num);
        //splitting the number as a String into an array
        int[] splitNum = new int[numbAsString.length()];
        //getting each number from the string into an int array (if num was 14, I get [1,4] in my array)
        for (int i = 0; i < numbAsString.length(); i++) {
            splitNum[i] = Character.getNumericValue(numbAsString.charAt(i));
        }
        //translating each of the numbers to nepalese
        for (int i : splitNum) {
            valueName.append(getNepaliNumFromResource(context, i));
        }
        return valueName.toString();
    }

    /**
     * finds the nepali num as a String
     *
     * @param num the number to translate to nepali
     * @return nepali number as a String
     */
    private String getNepaliNumFromResource(Context context, int num) {
        String stringValueName = "";
        switch (num) {
            case 0:
                stringValueName = "zero_num";
                break;
            case 1:
                stringValueName = "one_num";
                break;
            case 2:
                stringValueName = "two_num";
                break;
            case 3:
                stringValueName = "three_num";
                break;
            case 4:
                stringValueName = "four_num";
                break;
            case 5:
                stringValueName = "five_num";
                break;
            case 6:
                stringValueName = "six_num";
                break;
            case 7:
                stringValueName = "seven_num";
                break;
            case 8:
                stringValueName = "eight_num";
                break;
            case 9:
                stringValueName = "nine_num";
                break;
        }
        int idOfNum = context.getApplicationContext().getResources().getIdentifier(stringValueName, "string", context.getPackageName());
        return context.getString(idOfNum);
    }

    /**
     * scroll to the text in calendar that is useful for the user
     * @param inputMonth the amount of month since the conception of the pregnancy
     * @return the index of which the calendar has to scroll to
     */
    public int scrollToMonth(int inputMonth, List<Integer> monthsWithText) {
        int lastIndex = 0; //to save index
        for(int i = 0; i < monthsWithText.size(); i++) {
            int curMonth = monthsWithText.get(i) + 10;
            //because of jumps in month in sheets we store the index if our input is >=
            if(inputMonth >= curMonth) {
                lastIndex = i;
                //if our input is larger current month we return the previous index
            } else {
                return lastIndex;
            }
        }
        return lastIndex;
    }

}
