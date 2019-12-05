package com.example.nepalappgroupb2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class DataFromSheets {
    private Map<String, String> map;

    /**
     * We are using this Google Sheet: https://docs.google.com/spreadsheets/d/1XV2U8gztCXR4BmPuLT1eQBxYUx0GFPDJp9S8YqRn92A/edit#gid=0
     */

    public static void main(String[] args) {
        DataFromSheets data = new DataFromSheets();
        try {
            data.fromSheets();
            data.getMsgEngWithWeek(24);
            System.out.println(data.getMsgNum(34));

            System.out.println(data.getRadioTxtWithWeek(16));
            System.out.println(data.getRadioUrlWithNum(1));
            System.out.println(data.getRadioUrlWithWeek(12));
            System.out.println(data.getGoalWithNum(36));
            System.out.println(data.getGoalWithWeek(12));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Taken from "Galgelogik" by Jacob Nordfalk
     */
    private static String dataFromURL(String url) throws IOException {
        System.out.println("Henter data fra " + url);
        BufferedReader br = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
        StringBuilder sb = new StringBuilder();
        String linje = br.readLine();
        while (linje != null) {
            sb.append(linje + "\n");
            linje = br.readLine();
        }
        return sb.toString();
    }

    /**
     * Method used to find amount of headers in the Google Sheet.
     * Fx "MsgNum", "Week", "MsgEng" are headers.
     * @param line a single row from the Google Sheet
     * @return the amount of headers in the Google Sheet as an int
     */
    private int getAmountOfHeaders(String line) {
        int counter = 0;
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == '\t') counter++;
            //if 2 tabs occur side by side we return
            if (line.charAt(i) == '\t' && line.charAt(i + 1) == '\t') return counter;
        }
        return counter;
    }

    /**
     * The method that save the data from Google Sheet into a HashMap
     * @throws Exception
     */
    public void fromSheets() throws Exception {
        String id = "1XV2U8gztCXR4BmPuLT1eQBxYUx0GFPDJp9S8YqRn92A";

        System.out.println("Henter ord");

        //export as tsv file (tab separated values)
        String data = dataFromURL("https://docs.google.com/spreadsheets/d/" + id + "/export?format=tsv&id=" + id);
        System.out.println("hej:\n" + data);
        int lineNum = 0;
        String[] headerNames = new String[0];
        String[] dataList = data.split("\n");

        map = new HashMap<>();

        for (String line : dataList) {
            String[] lineElements = line.split("\t");
            System.out.println("test: " + Arrays.toString(lineElements));
            //saving the first row/line from Google Sheet as headerNames in a String[]
            if (lineNum++ == 0) {
                int indexLimit = getAmountOfHeaders(line);
                headerNames = line.split("\t", indexLimit);
                String lastWord = headerNames[headerNames.length - 1];
                //remove end of last word
                headerNames[headerNames.length - 1] = lastWord.substring(0, lastWord.indexOf('\t'));
            }

            //provide each cell in a row a headerName + lineIndex as key
            //Fx "MsgNum0", "MsgNum5", MsgEng29".
            int rowNum = 0;
            for (String s : headerNames) {
                try {
                    String key = s + Integer.parseInt(lineElements[0]);
                    map.put(key, lineElements[rowNum++]);
                    System.out.println("key: " + key);
                    //don't put if it's not a "normal" row (if it does not have a MsgNum)
                } catch (NumberFormatException e) {
                }
            }
        }

        System.out.println(map);
    }

    /**
     * To get the MsgNum when you only know the week
     * @param week the week number from Google Sheet
     * @return the MsgNum corresponding to the "week" parameter or -1 if not found
     */
    public int getMsgNum(int week) {
        String searchKey = "Week";
        String key;
        try {
            for (int i = 0; i < map.size(); i++) {
                key = searchKey + i;
                if (map.get(key).equals(String.valueOf(week))) {
                    return i;
                }
            }
        } catch (NullPointerException e) {}

        return -1;
    }

    /**
     * The best method to get the english message. Running as fast as the Hashmap.get function.
     * @param msgNum the MsgNum from the Google Sheet
     * @return The english message as a String for the given msgNum
     */
    public String getMsgEngWithNum(int msgNum) {
        String key = "MsgEng" + msgNum;
        return map.get(key);
    }

    /**
     * If you know the msgNum use the function "getMsgEngWithNum()" instead.
     * This is O(n) to find the msgNum for the specific week.
     * @param week the week number from Google Sheet
     * @return the english message as a String or null if not found
     */
    public String getMsgEngWithWeek(int week) {
        int msgNum = getMsgNum(week);
        try {
            return getMsgEngWithNum(msgNum);
        } catch (NullPointerException e) {}

        return null;
    }

    /**
     * The best method to get the nepali message. Running as fast as the Hashmap.get function.
     * @param msgNum the MsgNum from the Google Sheet
     * @return The nepali message as a String for the given msgNum
     */
    public String getMsgNepWithNum(int msgNum) {
        String key = "MsgNep" + msgNum;
        return map.get(key);
    }

    /**
     * If you know the msgNum use the function "getMsgNepWithNum()" instead.
     * This is O(n) to find the msgNum for the specific week.
     * @param week the week number from Google Sheet
     * @return the nepali message as a String or null if not found
     */
    public String getMsgNepWithWeek(int week) {
        int msgNum = getMsgNum(week);
        try {
            return getMsgNepWithNum(msgNum);
        } catch (NullPointerException e) {}

        return null;
    }

    /**
     * The best method to get the RadioTxt. Running as fast as the Hashmap.get function.
     * @param msgNum the MsgNum from the Google Sheet
     * @return The RadioTxt as a String for the given msgNum
     */
    public String getRadioTxtWithNum(int msgNum) {
        String key = "RadioTxt" + msgNum;
        String radioTxt = map.get(key);
        return radioTxt.equals("") ? null : radioTxt;
    }

    /**
     * If you know the msgNum use the function "getRadioTxtWithNum()" instead.
     * This is O(n) to find the msgNum for the specific week.
     * @param week the week number from Google Sheet
     * @return the RadioTxt as a String or null if not found
     */
    public String getRadioTxtWithWeek(int week) {
        int msgNum = getMsgNum(week);
        try {
            return getRadioTxtWithNum(msgNum);
        } catch (NullPointerException e) {}

        return null;
    }

    /**
     * The best method to get the RadioUrl. Running as fast as the Hashmap.get function.
     * @param msgNum the MsgNum from the Google Sheet
     * @return The RadioUrl as a String for the given msgNum
     */
    public String getRadioUrlWithNum(int msgNum) {
        String key = "RadioUrl" + msgNum;
        String radioTxt = map.get(key);
        return radioTxt.equals("") ? null : radioTxt;
    }

    /**
     * If you know the msgNum use the function "getRadioUrlWithNum()" instead.
     * This is O(n) to find the msgNum for the specific week.
     * @param week the week number from Google Sheet
     * @return the RadioUrl as a String or null if not found
     */
    public String getRadioUrlWithWeek(int week) {
        int msgNum = getMsgNum(week);
        try {
            return getRadioUrlWithNum(msgNum);
        } catch (NullPointerException e) {}

        return null;
    }

    /**
     * The best method to get the Goal message. Running as fast as the Hashmap.get function.
     * @param msgNum the MsgNum from the Google Sheet
     * @return The Goal message as a String for the given msgNum
     */
    public String getGoalWithNum(int msgNum) {
        String key = "Goal" + msgNum;
        return map.get(key);
    }

    /**
     * If you know the msgNum use the function "getGoalWithNum()" instead.
     * This is O(n) to find the msgNum for the specific week.
     * @param week the week number from Google Sheet
     * @return the Goal of the message as a String or null if not found
     */
    public String getGoalWithWeek(int week) {
        int msgNum = getMsgNum(week);
        try {
            return getGoalWithNum(msgNum);
        } catch (NullPointerException e) {}

        return null;
    }

    /**
     * To get the size of the HashMap
     * @return size of HashMap
     */
    public int getMapSize() {
        return map.size();
    }

}
