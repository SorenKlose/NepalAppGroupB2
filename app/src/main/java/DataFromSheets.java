import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class DataFromSheets {
    private Map<String, String> map;

    public static void main(String[] args) {
        DataFromSheets data = new DataFromSheets();
        try {
            data.fromSheets();
            data.getMsgEngWithWeek(24);
            System.out.println(data.getMsgNum(34));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String dataFromURL(String url) throws IOException {
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

    public int getAmountOfHeaders(String line) {
        int counter = 0;
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == '\t') counter++;
            if (line.charAt(i) == '\t' && line.charAt(i + 1) == '\t') return counter;
        }
        return counter;
    }

    public void fromSheets() throws Exception {
        String id = "1XV2U8gztCXR4BmPuLT1eQBxYUx0GFPDJp9S8YqRn92A";

        System.out.println("Henter ord");

        String data = dataFromURL("https://docs.google.com/spreadsheets/d/" + id + "/export?format=tsv&id=" + id);
        System.out.println("hej:\n" + data);
        int lineNum = 0;
        String[] headerNames = new String[0];
        String[] dataList = data.split("\n");

        map = new HashMap<>();

        for (String line : dataList) {
            String[] lineElements = line.split("\t");
            System.out.println("test: " + Arrays.toString(lineElements));
            if (lineNum++ == 0) {
                int indexLimit = getAmountOfHeaders(line);
                System.out.println("index er: " + indexLimit);
                headerNames = line.split("\t", indexLimit); //first line in sheets become headernames
                String lastWord = headerNames[headerNames.length - 1];
                headerNames[headerNames.length - 1] = lastWord.substring(0, lastWord.indexOf('\t')); //remove end of last word
            }

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

        System.out.println("hej: " + map.get("MsgEng0"));
        System.out.println();
        System.out.println(map);
    }

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

    public String getMsgEngWithNum(int msgNum) {
        String key = "MsgEng" + msgNum;
        System.out.println(map.get(key));
        return map.get(key);
    }

    public String getMsgEngWithWeek(int week) {
        int msgNum = getMsgNum(week);
        try {
            return getMsgEngWithNum(msgNum);
        } catch (NullPointerException e) {}

        return null;
    }


}
