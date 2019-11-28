package com.example.nepalappgroupb2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;


public class BenytRecyclerviewEkspanderbar extends Fragment {

  RecipeCardElement calendarCardElement = new RecipeCardElement();

  static class CalendarInfoData {
    List<String> months = Arrays.asList("1 month old", "2 month old", "3 month old", "4 month old");

    List<List<String>> byer = Arrays.asList(
            Arrays.asList("København", "Århus", "Odense", "Aalborg", "Ballerup"),
            Arrays.asList("Oslo", "Trondheim"),
            Arrays.asList("Stockholm", "Malmø", "Lund"),
            Arrays.asList("Reykjavík", "Kópavogur", "Hafnarfjörður", "Dalvík"));
  }

  CalendarInfoData data = new CalendarInfoData();

  HashSet<Integer> åbneLande = new HashSet<>(); // hvilke lande der lige nu er åbne

  RecyclerView recyclerView;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    View layout = inflater.inflate(R.layout.calendar_recyclerview, container, false);

    recyclerView = layout.findViewById(R.id.calendar_recyclerView);
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    recyclerView.setAdapter(adapter);

    // Understøttelse for skærmvending - kan evt udelades
    if (savedInstanceState!=null) {
      åbneLande = (HashSet<Integer>) savedInstanceState.getSerializable("åbneLande");
      recyclerView.getLayoutManager().onRestoreInstanceState(savedInstanceState.getParcelable("liste"));
    }
    return layout;
  }

  @Override
  public void onSaveInstanceState(Bundle outState) { // Understøttelse for skærmvending - kan evt udelades
    super.onSaveInstanceState(outState);
    outState.putSerializable("åbneLande", åbneLande);
    outState.putParcelable("liste", recyclerView.getLayoutManager().onSaveInstanceState());
  }

  RecyclerView.Adapter adapter = new RecyclerView.Adapter<EkspanderbartListeelemViewholder>() {

    @Override
    public int getItemCount()  {
      return data.months.size();
    }

    @Override
    public EkspanderbartListeelemViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
      LinearLayout rodLayout = new LinearLayout(parent.getContext());
      rodLayout.setOrientation(LinearLayout.VERTICAL);
      LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
      rodLayout.setLayoutParams(lp);
      EkspanderbartListeelemViewholder vh = new EkspanderbartListeelemViewholder(rodLayout);
      vh.rodLayout = rodLayout;
      vh.landeview = getLayoutInflater().inflate(R.layout.calender_card_element, parent, false);
      vh.title = vh.landeview.findViewById(R.id.calender_card_title);
      vh.calendarImage = vh.landeview.findViewById(R.id.image_calender);
      vh.landeview.setOnClickListener(vh);
      //vh.landeview.setBackgroundResource(android.R.drawable.list_selector_background); // giv visuelt feedback når der trykkes på baggrunden
      vh.calendarImage.setOnClickListener(vh);
//      vh.calendarImage.setBackgroundResource(android.R.drawable.btn_default);
      vh.rodLayout.addView(vh.landeview);
      return vh;
    }

    @Override
    public void onBindViewHolder(EkspanderbartListeelemViewholder vh, int position) {
      boolean åben = åbneLande.contains(position);
      vh.title.setText(data.months.get(position));
      vh.calendarImage.setImageResource(calendarCardElement.getBgImgIDFromTitle(""+(position + 1) + " month old", getContext()));
      System.out.println(""+(position + 1) + "month old");

      if (!åben) {
        for (View underview : vh.underviews) underview.setVisibility(View.GONE); // skjul underelementer
      } else {

        List<String> byerILandet = data.byer.get(position);

        while (vh.underviews.size()<byerILandet.size()) { // sørg for at der er nok underviews
          TextView underView = new TextView(vh.rodLayout.getContext());
          //underView.setPadding(0, 20, 0, 20);
          underView.setBackgroundResource(android.R.drawable.list_selector_background);
          underView.setOnClickListener(vh);      // lad viewholderen håndtere evt klik
          underView.setId(vh.underviews.size()); // unik ID så vi senere kan se hvilket af underviewne der klikkes på
          vh.rodLayout.addView(underView);
          vh.underviews.add(underView);
        }

        for (int i=0; i<vh.underviews.size(); i++) { // sæt underviews til at vise det rigtige indhold
          TextView underView = vh.underviews.get(i);
          if (i<byerILandet.size()) {
            underView.setText(byerILandet.get(i));
            underView.setVisibility(View.VISIBLE);
          } else {
            underView.setVisibility(View.GONE);      // for underviewet skal ikke bruges
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
    TextView title;
    ImageView calendarImage;
    View landeview;
    ArrayList<TextView> underviews = new ArrayList<>();

    public EkspanderbartListeelemViewholder(View itemView) {
      super(itemView);
    }

    @Override
    public void onClick(View v) {
      final int position = getAdapterPosition();

      if (v == calendarImage || v==landeview) { // Klik på billede åbner/lukker for listen af byer i dette land
        boolean åben = åbneLande.contains(position);
        if (åben) åbneLande.remove(position); // luk
        else åbneLande.add(position); // åbn
        adapter.notifyItemChanged(position);
      } else {
        int id = v.getId();
        Toast.makeText(v.getContext(), "Klik på by nummer " + id + " i "+data.months.get(position), Toast.LENGTH_SHORT).show();
      }
    }
  }
}
