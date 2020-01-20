/*
Den her klasse startede som en kopi af BenytRecyclerviewEkspanderbar klassen fra android elementer,
og er siden blevet ændret i, for at tilpasse vores behov.
*/

package com.example.nepalappgroupb2;

import android.content.res.Resources;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nepalappgroupb2.Calendar.CalendarLoading;
import com.example.nepalappgroupb2.Domain.DataFromSheets;
import com.example.nepalappgroupb2.Domain.DataService;
import com.example.nepalappgroupb2.Recipe.RecipeCardElement;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;


public class BenytRecyclerviewEkspanderbar extends Fragment {

    DataFromSheets db = new DataFromSheets();
    RecipeCardElement calendarCardElement = new RecipeCardElement();
    List<String> months = new ArrayList<>(); // List of the titles for every months underview in calendar.
    List<Integer> tempMonths; //List of every month that has at least one message.
    ViewGroup vg;
    MediaPlayer month6sound;
    MediaPlayer mp = new MediaPlayer();
    int soundPlaying;


    static class CalendarInfoData {
        List<List<String>> info = Arrays.asList(
                Arrays.asList("Congratulations on your pregnancy! During the fourth month of pregnancy, " +
                                "visit the health facility for antenatal care, so that you learn about " +
                                "your and child’s health.\n",
                        "One IFA per day starting from the fourth " +
                                "month of pregnancy will reduce your risk for anemia. IFA is available " +
                                "free of cost at health facilities or from the FCHV.\n"),
                Arrays.asList("As the child in the womb also receives nutrition from mother's food, " +
                                "the pregnant woman should eat one more meal than usual daily and should " +
                                "eat nutritious foods including eggs, fish and meat.\n",
                        "During pregnancy, participating in FCHV led Health Mother's Group meetings is an opportunity to learn many things about your and your child’s health. Therefore, go every month.\n"),

                Arrays.asList("For further information on your and the child’s health, listen to Bhanchhin Aama radio program from your local FM every Sunday morning at 7.30 hrs, afternoon at 13:00 hrs and at night at 9:15pm.\n", "Please eat eggs, meat and milk products every day for health and nutrition of both you and your child.\n"),
                Arrays.asList("Always only drink water after boiling or filtering to prevent diarrheal diseases, typhoid and malnutrition.\n", "Hope you have not forgotten to take an IFA every day?\n"));
    }

    CalendarInfoData data = new CalendarInfoData();

    HashSet<Integer> openMonths = new HashSet<>(); // Which months are currently open

    RecyclerView recyclerView;

    List<String> hej;

    /**
     * combining nepali numbers to get bigger numbers. Assuming all nepali numbers can be found by
     * combining them. Fx if we have 123 then we find 1, 2, and 3 in nepali and combining them
     *
     * @param num the number to translate to nepali
     * @return the nepali number as a String
     */
    private String getNepaliNum(int num) {
        System.out.println("num er: " + num);
        StringBuilder valueName = new StringBuilder();
        String numbAsString = Integer.toString(num);
        int[] splitNum = new int[numbAsString.length()];
        for (int i = 0; i < numbAsString.length(); i++) {
            splitNum[i] = Character.getNumericValue(numbAsString.charAt(i));
        }
        System.out.println("mit array: " + Arrays.toString(splitNum));
        for (int i : splitNum) {
            valueName.append(getNepaliNumFromResource(i));
        }
        return valueName.toString();
    }

    /**
     * finds the nepali num as a String
     *
     * @param num the number to translate to nepali
     * @return nepali number as a String
     */
    private String getNepaliNumFromResource(int num) {
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
        int idOfNum = getContext().getApplicationContext().getResources().getIdentifier(stringValueName, "string", getContext().getPackageName());
        //int idOfNum = getResources().getIdentifier(stringValueName, "string", getContext().getPackageName());
        return getString(idOfNum);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tempMonths = DataService.getMonthsFromData();


        for (int i = 0; i < tempMonths.size(); i++) {
            if (tempMonths.get(i) < 0) {
                try {
                    //check for default language (napali)
                    if (!Locale.getDefault().getDisplayLanguage().equals("dansk") && !Locale.getDefault().getDisplayLanguage().equals("English")) {
                        months.add(getNepaliNum(tempMonths.get(i + 10)) + " " + getString(R.string.month) + " " + getString(R.string.pregnant));
                        System.out.println("sprog: " + Locale.getDefault().getDisplayLanguage());
                    }
                    //if not nepali - show other supported language (with western numbers)
                    else {
                        months.add("" + (tempMonths.get(i) + 10) + " " + getString(R.string.month_preg));
                    }
                    //if the napali number is not found we show supported language text
                } catch (Resources.NotFoundException e) {
                    months.add("" + (tempMonths.get(i) + 10) + " " + getString(R.string.month_preg));
                }
            } else {
                try {
                    //check if the language is not danish or english (default is napali)
                    if (!Locale.getDefault().getDisplayLanguage().equals("dansk") && !Locale.getDefault().getDisplayLanguage().equals("English")) {
                        months.add(getNepaliNum(tempMonths.get(i)) + " " + getString(R.string.month));
                    } else {
                        //if not nepali - show other supported language (with western numbers)
                        months.add(tempMonths.get(i) + " " + getString(R.string.month));
                    }
                    //if the napali number is not found we show supported language text
                } catch (Resources.NotFoundException e) {
                    months.add(tempMonths.get(i) + " " + getString(R.string.month));
                }
            }
        }

        View layout = inflater.inflate(R.layout.calendar_recyclerview, container, false);

        recyclerView = layout.findViewById(R.id.calendar_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        month6sound = MediaPlayer.create(getContext(), R.raw.six_month_1);

        //scrolling to correct month text
        int scrollToIndex = scrollToMonth(5);
        recyclerView.getLayoutManager().scrollToPosition(scrollToIndex);

        // Understøttelse for skærmvending - kan evt udelades
        if (savedInstanceState != null) {
            openMonths = (HashSet<Integer>) savedInstanceState.getSerializable("openMonths");
            recyclerView.getLayoutManager().onRestoreInstanceState(savedInstanceState.getParcelable("liste"));
        }
        return layout;
    }

    /**
     * scroll to the text in calendar that is useful for the user
     * @param inputMonth the amount of month since the conception of the pregnancy
     * @return the index of which the calendar has to scroll to
     */
    private int scrollToMonth(int inputMonth) {
        int temp = 0; //to save index
        for(int i = 0; i < tempMonths.size(); i++) {
            int curMonth = tempMonths.get(i) + 10;
            //because of jumps in month in sheets we store the index if our input is >=
            if(inputMonth >= curMonth) {
                temp = i;
            //if our input is larger current month we return the previous index
            } else {
                return temp;
            }
        }
        return temp;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) { // Understøttelse for skærmvending - kan evt udelades
        super.onSaveInstanceState(outState);
        outState.putSerializable("openMonths", openMonths);
        outState.putParcelable("liste", recyclerView.getLayoutManager().onSaveInstanceState());
    }

    RecyclerView.Adapter adapter = new RecyclerView.Adapter<EkspanderbartListeelemViewholder>() {

        @Override
        public int getItemCount() {
            return months.size();
        }

        @Override
        public EkspanderbartListeelemViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
            LinearLayout rodLayout = new LinearLayout(parent.getContext());
            rodLayout.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            rodLayout.setLayoutParams(lp);
            EkspanderbartListeelemViewholder vh = new EkspanderbartListeelemViewholder(rodLayout);
            vh.rodLayout = rodLayout;
            vh.landeview = getLayoutInflater().inflate(R.layout.calender_card_element, parent, false);
            vh.title = vh.landeview.findViewById(R.id.calender_card_title);
            //vh.calendarImage = vh.landeview.findViewById(R.id.image_calender);
            vh.titleBackgroundColor = vh.landeview.findViewById(R.id.month_title_backgroundcolor);
            vh.landeview.setOnClickListener(vh);
            vh.landeview.setBackgroundResource(android.R.drawable.list_selector_background); // giv visuelt feedback når der trykkes på baggrunden
            //vh.calendarImage.setOnClickListener(vh);
            vh.titleBackgroundColor.setOnClickListener(vh);
//      vh.calendarImage.setBackgroundResource(android.R.drawable.btn_default);
            vh.rodLayout.addView(vh.landeview);
            vg = parent;
            return vh;
        }

        @Override
        public void onBindViewHolder(EkspanderbartListeelemViewholder vh, final int position) {
            boolean isOpen = openMonths.contains(position);
            vh.title.setText(months.get(position));
            //background for elements in recyclerview
            //vh.calendarImage.setImageResource(calendarCardElement.getBgImgIDFromTitle("" + (position + 1) + " month old", getContext()));

            //switching the background colors of the cards
            // TODO: 15-01-2020 måske lave så pregnant har en farve og efter fødslen har det en anden?
            switch (position % 4) {
                case 0:
                    vh.titleBackgroundColor.setBackgroundColor(getResources().getColor(R.color.calendar_red));
                    break;
                case 1:
                    vh.titleBackgroundColor.setBackgroundColor(getResources().getColor(R.color.recipe_blue));
                    break;
                case 2:
                    vh.titleBackgroundColor.setBackgroundColor(getResources().getColor(R.color.comic_orange));
                    break;
                case 3:
                    vh.titleBackgroundColor.setBackgroundColor(getResources().getColor(R.color.quiz_green));
                    break;
            }
            System.out.println("" + (position + 1) + "month old");

            if (!isOpen) {
                for (View underview : vh.underviews) {
                    underview.setVisibility(View.GONE); // skjul underelementer
                }
            } else {
                List<String> infoList = DataService.getMessageOfMonth(getString(R.string.chosen_language), tempMonths.get(position));

                while (vh.underviews.size() < infoList.size()) { // sørg for at der er nok underviews
                    View underView = getLayoutInflater().inflate(R.layout.calendar_info_card, vg, false);
                    underView.setBackgroundResource(android.R.drawable.list_selector_background);
                    underView.setOnClickListener(vh);      // lad viewholderen håndtere evt klik
                    underView.setId(vh.underviews.size()); // unik ID så vi senere kan se hvilket af underviewne der klikkes på
                    vh.rodLayout.addView(underView);
                    vh.underviews.add(underView);
                }

                for (int i = 0; i < vh.underviews.size(); i++) { // sæt underviews til at vise det rigtige indhold
                    if (i < infoList.size()) {
                        // TODO: 16-01-2020 lav et lidt bedre fix, så dette ikke sker
                        //hot fix: if there is more underviews than info in infolist then we remove the views
                        if (vh.underviews.size() > infoList.size()) {
                            for (View underview : vh.underviews) {
                                underview.setVisibility(View.GONE); // skjul underelementer
                            }
                        }
                        View underView = vh.underviews.get(i);
                        TextView tv = underView.findViewById(R.id.descText);
                        tv.setText(infoList.get(i) + "\n");
                        underView.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    };

    @Override
    public void onPause() {
        super.onPause();

        if (mp.isPlaying()) {
            mp.stop();
            mp.release();
            mp = new MediaPlayer();
        }
    }

    /**
     * En Viewholder husker forskellige views i et listeelement, sådan at søgninger i viewhierakiet
     * med findViewById() kun behøver at ske EN gang.
     * Se https://developer.android.com/training/material/lists-cards.html
     */
    class EkspanderbartListeelemViewholder extends RecyclerView.ViewHolder implements View.OnClickListener {
        LinearLayout rodLayout;
        TextView title, titleBackgroundColor;
        ImageView calendarImage;
        View landeview;
        ArrayList<View> underviews = new ArrayList<>();

        public EkspanderbartListeelemViewholder(View itemView) {
            super(itemView);
        }

        @Override
        public void onClick(View v) {
            final int position = getAdapterPosition();

            if (v == titleBackgroundColor || v == landeview) { // Klik på billede åbner/lukker for listen af byer i dette land
                boolean åben = openMonths.contains(position);
                if (åben) {
                    openMonths.remove(position); // luk
                    if (mp.isPlaying() && soundPlaying == position) {
                        mp.stop();
                        mp.release();
                        mp = new MediaPlayer();
                    }
                } else openMonths.add(position); // åbn
                adapter.notifyItemChanged(position);
            } else {
                int id = v.getId();
                Toast.makeText(v.getContext(), "Klik på by nummer " + id + " i " + months.get(position), Toast.LENGTH_SHORT).show();

                String soundName = db.getMediaPlayer(tempMonths.get(position), id);
                Uri uri = Uri.parse("android.resource://" + getContext().getPackageName() + "/raw/" + soundName);
                try {
                    if (!mp.isPlaying()) {
                        mp.setDataSource(getContext(), uri);
                        mp.prepare();
                        mp.start();
                        soundPlaying = position;
                        System.out.println("spiller: " + soundName);
                    } else {
                        mp.stop();
                        mp.release();
                        mp = new MediaPlayer();
                        mp.setDataSource(getContext(), uri);
                        mp.prepare();
                        mp.start();
                        soundPlaying = position;
                        System.out.println("spiller: " + soundName);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
