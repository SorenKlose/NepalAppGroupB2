/*
Den her klasse startede som en kopi af BenytRecyclerviewEkspanderbar klassen fra android elementer,
og er siden blevet ændret i, for at tilpasse vores behov.
*/

package com.example.nepalappgroupb2;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
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
import com.example.nepalappgroupb2.Recipe.RecipeCardElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;


public class BenytRecyclerviewEkspanderbar extends Fragment {

    DataFromSheets db = new DataFromSheets();
    RecipeCardElement calendarCardElement = new RecipeCardElement();
    List<String> months = new ArrayList<>(); // List of the titles for every months underview in calendar.
    List<Integer> tempMonths = CalendarLoading.monthList; //List of every month that has at least one message.
    ViewGroup vg;
    MediaPlayer month6sound;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //adding titles to all the indices in the recyclerview
        for (int i = 0; i < tempMonths.size(); i++) {
            if (tempMonths.get(i) < 0) {
                months.add("" + (tempMonths.get(i) + 10) + " months pregnant");
            } else {
                months.add("" + tempMonths.get(i) + " months old");
            }
        }

//        for (int i = (-7); i < 19; i++) {
//            if (i < 0) {
//                months.add("" + (i + 10) + " months pregnant");
//            } else if (i < 13) {
//                months.add("" + i + " months old");
//            } else {
//                months.add("" + (((i - 12) * 2) + 12) + " months old");
//            }
//        }

        View layout = inflater.inflate(R.layout.calendar_recyclerview, container, false);

        recyclerView = layout.findViewById(R.id.calendar_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        month6sound = MediaPlayer.create(getContext(), R.raw.six_month_1);

        // Understøttelse for skærmvending - kan evt udelades
        if (savedInstanceState != null) {
            openMonths = (HashSet<Integer>) savedInstanceState.getSerializable("openMonths");
            recyclerView.getLayoutManager().onRestoreInstanceState(savedInstanceState.getParcelable("liste"));
        }
        return layout;
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
        public void onBindViewHolder(EkspanderbartListeelemViewholder vh, int position) {
            boolean isOpen = openMonths.contains(position);
            vh.title.setText(months.get(position));
            //background for elements in recyclerview
            //vh.calendarImage.setImageResource(calendarCardElement.getBgImgIDFromTitle("" + (position + 1) + " month old", getContext()));

            //switching the background colors of the cards
            // TODO: 15-01-2020 måske lave så pregnant har en farve og efter fødslen har det en anden?
            switch (position % 4) {
                case 0: vh.titleBackgroundColor.setBackgroundColor(getResources().getColor(R.color.calendar_red)); break;
                case 1: vh.titleBackgroundColor.setBackgroundColor(getResources().getColor(R.color.recipe_blue)); break;
                case 2: vh.titleBackgroundColor.setBackgroundColor(getResources().getColor(R.color.comic_orange)); break;
                case 3: vh.titleBackgroundColor.setBackgroundColor(getResources().getColor(R.color.quiz_green)); break;
            }
            System.out.println("" + (position + 1) + "month old");

            if (!isOpen) {
                for (View underview : vh.underviews)
                    underview.setVisibility(View.GONE); // skjul underelementer
            } else {

                List<String> infoList = db.getWithMonth(DataFromSheets.Headers.MsgEng, tempMonths.get(position));

                while (vh.underviews.size() < infoList.size()) { // sørg for at der er nok underviews
                    View underView = getLayoutInflater().inflate(R.layout.calendar_info_card, vg, false);
                    underView.setBackgroundResource(android.R.drawable.list_selector_background);
                    underView.setOnClickListener(vh);      // lad viewholderen håndtere evt klik
                    underView.setId(vh.underviews.size()); // unik ID så vi senere kan se hvilket af underviewne der klikkes på
                    vh.rodLayout.addView(underView);
                    vh.underviews.add(underView);
                }

                for (int i = 0; i < vh.underviews.size(); i++) { // sæt underviews til at vise det rigtige indhold
                    View underView = vh.underviews.get(i);
                    if (i < infoList.size()) {
                        TextView tv = underView.findViewById(R.id.descText);
                        tv.setText(infoList.get(i) + "\n");
                        underView.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    };


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
                if (åben) openMonths.remove(position); // luk
                else openMonths.add(position); // åbn
                adapter.notifyItemChanged(position);
            } else {
                int id = v.getId();
                Toast.makeText(v.getContext(), "Klik på by nummer " + id + " i " + months.get(position), Toast.LENGTH_SHORT).show();
//        month6sound.start();

                String soundName = db.getMediaPlayer(tempMonths.get(position), id);

                try {
                    MediaPlayer mp = new MediaPlayer();
                    Uri uri = Uri.parse("android.resource://" + getContext().getPackageName() + "/raw/" + soundName);
                    mp.setDataSource(getContext(), uri);
                    mp.prepare();
                    mp.start();
                    System.out.println("spiller: " + soundName);
                    System.out.println(mp.getDuration());
                    //SystemClock.sleep(mp.getDuration());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
