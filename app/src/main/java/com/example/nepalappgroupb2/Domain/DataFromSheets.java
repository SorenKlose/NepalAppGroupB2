package com.example.nepalappgroupb2.Domain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataFromSheets {
    private Map<String, String> map;
    public enum Headers {
        MsgNum, Week, Month, Goal, MsgNep, MsgEng, RadioTxt, RadioUrl
    }

    /**
     * We are using this Google Sheet: https://docs.google.com/spreadsheets/d/1XV2U8gztCXR4BmPuLT1eQBxYUx0GFPDJp9S8YqRn92A/edit#gid=0
     */

    public static void main(String[] args) {
        DataFromSheets data = new DataFromSheets();
        try {
            data.fromSheets();

            for (String s : data.getWithMonth(Headers.MsgEng, 6)) {
                System.out.println("hej " + s);
            }

            System.out.println("hej: " + data.map.get("Month1"));

            System.out.println(data.getMonths());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Integer> getMonths() {
        String keyword = "Month";
        String key;
        List<Integer> monthList = new ArrayList<>();
        try {
            //traverse the map looking for the key. Fx then i=1 it is "Month1"
            for (int i = 1; i < map.size(); i++) {
                key = keyword + i;
                //if the key we find is not null, we add to the list
                if (map.get(key) != null) {
                    monthList.add((Integer.valueOf(map.get(key))));
                }
            }
        } catch (NullPointerException e) {}

        //removing duplicates from the monthList
        List<Integer> noDuplicates = new ArrayList<>();
        for (int i : monthList) {
            if (!noDuplicates.contains(i)) {
                noDuplicates.add(i);
            }
        }

        return noDuplicates;
    }

    /**
     * Finds the value of a header from msgNum. This is being used by "getWithMonth" method
     *
     * @param headerToGet the header of what value you want. Taken from the Headers enum
     * @param msgNum      at which msgNum it should retrieve the header value from
     * @return a String with the value. If you expect an int you have to parse it yourself
     */
    public String getWithMsgNum(Headers headerToGet, int msgNum) {
        String key = headerToGet.toString() + msgNum;
        return map.get(key);
    }

    /**
     * Get a list of all the header values from a specific month
     *
     * @param headerToGet the header of what value you want. Taken from the Headers enum
     * @param month       from which month do you want the data
     * @return a List of Strings with the values for that specific month. Returns null if no matches.
     */
    public List<String> getWithMonth(Headers headerToGet, int month) {
        List<String> headerList = new ArrayList<>();
        //we are searching for any of the months
        String keyWord = "Month";
        String key;
        try {
            //looping through the whole map
            for (int i = 0; i < map.size(); i++) {
                key = keyWord + i;

                //when this is true it means that we found the index of the month we were looking fore.
                //this is then send to "getWithMsgNum" where we parse the header and the index/msgNum. We add the return of this to the headerList
                if (map.get(key).equals(String.valueOf(month))) {
                    headerList.add(getWithMsgNum(headerToGet, i));
                }

            }
        } catch (NullPointerException e) {
            //return null if no match for headerToGet in any of the months
            if (headerList.isEmpty()) return null;
        }
        //if we found anything before running out of month, we return it
        return headerList;
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
     *
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
     *
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
     * To get the size of the HashMap
     *
     * @return size of HashMap
     */
    public int getMapSize() {
        try{
            return map.size();
        }catch(NullPointerException e){
            System.out.println("DataFromSheets.getMapSize() threw a nullpointerexception, " +
                    "proberly because data is not downloaded yet. \n " +
                    "download it first with DataFromSheets.fromSheets()");
            return -1;
        }

    }

}
