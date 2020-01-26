/*
Den her klasse startede som en kopi af CalendarRcView klassen fra android elementer,
og er siden blevet ændret i, for at tilpasse vores behov.
*/

package com.example.nepalappgroupb2.Calendar;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
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

import com.example.nepalappgroupb2.Domain.Afspilning;
import com.example.nepalappgroupb2.Domain.DataFromSheets;
import com.example.nepalappgroupb2.Domain.DataService;
import com.example.nepalappgroupb2.Progress.ProgressBarFragment;
import com.example.nepalappgroupb2.R;
import com.example.nepalappgroupb2.Recipe.RecipeCardElement;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;


public class CalendarRcView extends Fragment {

    DataFromSheets db = new DataFromSheets();
    RecipeCardElement calendarCardElement = new RecipeCardElement();
    ProgressBarFragment progressBar = new ProgressBarFragment();
    CalendarLogic calendarLogic = new CalendarLogic();

    List<String> months = new ArrayList<>(); // List of the titles for every months underview in calendar.
    List<Integer> tempMonths; //List of every month that has at least one message.
    ViewGroup vg;

    HashSet<Integer> openMonths = new HashSet<>(); // Which months are currently open

    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tempMonths = DataService.getMonthsFromData(getContext());

        //setting the language for the views
        String language = Locale.getDefault().getDisplayLanguage();
        for(int i = 0; i < tempMonths.size(); i++) {
            int curMonth = tempMonths.get(i);

            //if pregnant
            if(curMonth < 0)
                months.add(calendarLogic.getNumForCurLanguage(getContext(), curMonth+10, language) + " " + getString(R.string.month_preg));
            //if born
            else
                months.add((calendarLogic.getNumForCurLanguage(getContext(), curMonth, language)) + " " + getString(R.string.month));
        }

        View layout = inflater.inflate(R.layout.calendar_recyclerview, container, false);

        recyclerView = layout.findViewById(R.id.calendar_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        //scrolling to correct month text
        int scrollToIndex = calendarLogic.scrollToMonth(progressBar.monthsOld(getContext()), tempMonths);

        recyclerView.getLayoutManager().scrollToPosition(scrollToIndex);
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
            vh.monthview = getLayoutInflater().inflate(R.layout.calender_card_element, parent, false);
            vh.title = vh.monthview.findViewById(R.id.calender_card_title);
            vh.calendarImage = vh.monthview.findViewById(R.id.image_calendar);
            vh.titleBackgroundColor = vh.monthview.findViewById(R.id.month_title_backgroundcolor);
            vh.monthview.setOnClickListener(vh);
            vh.monthview.setBackgroundResource(android.R.drawable.list_selector_background); // giv visuelt feedback når der trykkes på baggrunden
            vh.titleBackgroundColor.setOnClickListener(vh);
            vh.rodLayout.addView(vh.monthview);
            vg = parent;
            return vh;
        }

        @Override
        public void onBindViewHolder(EkspanderbartListeelemViewholder vh, final int position) {
            boolean isOpen = openMonths.contains(position);
            vh.title.setText(months.get(position));

            /**
             * setting images and background colors on the months
             */
            String imgToFind;
            //for pregnant
            if(tempMonths.get(position) < 0) {
                imgToFind = (tempMonths.get(position) + 10) + " month preg";
            //child is born
            } else {
                imgToFind = "" + tempMonths.get(position) + " month old";
            }
            //getting the resID for the img
            int imgId = calendarCardElement.getImgIdFromString(imgToFind, getContext());
            //method returning 0 if it does not find anything
            if(imgId != 0) {
                //setting the img and background color to white (otherwise we get red as it reuses the views)
                vh.calendarImage.setImageResource(imgId);
                vh.titleBackgroundColor.setBackgroundColor(getResources().getColor(R.color.white));
            } else {
                //clearing imageview and setting the background color to red
                vh.calendarImage.setImageResource(0);
                vh.titleBackgroundColor.setBackgroundColor(getResources().getColor(R.color.calendar_red));
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
                        //if there is more underviews than info in infolist then we remove the views
                        if (vh.underviews.size() > infoList.size()) {
                            for (View underview : vh.underviews) {
                                underview.setVisibility(View.GONE); // skjul underelementer
                            }
                        }
                        View underView = vh.underviews.get(i);
                        TextView tv = underView.findViewById(R.id.descText);
                        tv.setText(infoList.get(i));
                        underView.setVisibility(View.VISIBLE);

                        String soundName = db.getMediaPlayer(tempMonths.get(position), i);
                        underView.findViewById(R.id.speakerImage).setVisibility(soundName.isEmpty()? View.GONE : View.VISIBLE);
                    }
                }
            }
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        Afspilning.stop();
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
        View monthview;
        ArrayList<View> underviews = new ArrayList<>();

        public EkspanderbartListeelemViewholder(View itemView) {
            super(itemView);
        }

        @Override
        public void onClick(View v) {
            final int position = getAdapterPosition();

            if (v == titleBackgroundColor || v == monthview) { // Clicking on a month, opens/closes the list of info for that motnh.
                boolean åben = openMonths.contains(position);
                if (åben) {
                    openMonths.remove(position); // close
                    Afspilning.stop();
                } else openMonths.add(position); // open
                adapter.notifyItemChanged(position);
            } else {
                int id = v.getId();
                Toast.makeText(v.getContext(), "Reading text " + (id+1), Toast.LENGTH_SHORT).show();

                String soundName = db.getMediaPlayer(tempMonths.get(position), id);
                Uri uri = Uri.parse("android.resource://" + getContext().getPackageName() + "/raw/" + soundName);
                Afspilning.start(MediaPlayer.create(getActivity(), uri));
            }
        }
    }
}
