package com.example.nepalappgroupb2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class Calendar extends Fragment {
    RecipeCardElement recipeCardElement = new RecipeCardElement();

    private RecyclerView recyclerView;

    //test arrays for cardElements and their title
    List<RecipeCardElement> cardArray = new ArrayList<>();
    String[] test = {"1 month old", "2 month old", "3 month old", "4 month old"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState) {
        View layout = inflater.inflate(R.layout.calendar_recyclerview, container, false);

        //creating cardElements for the array
        for(int i = 0; i < test.length; i++) {
            cardArray.add(new RecipeCardElement(test[i]));
        }

        recyclerView = layout.findViewById(R.id.calendar_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        return layout;
    }

    RecyclerView.Adapter adapter = new RecyclerView.Adapter() {
        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = getLayoutInflater().inflate(R.layout.calender_card_element, parent, false);
            return new RecyclerView.ViewHolder(itemView) {};
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            //finding the views
            TextView title = holder.itemView.findViewById(R.id.calender_card_title);
            ImageView bgImage = holder.itemView.findViewById(R.id.image_calender);

            //setting title and background image for each of the cards
            title.setText(cardArray.get(position).getCardTitle());

            int resID = recipeCardElement.getBgImgIDFromTitle(cardArray.get(position).getCardTitle(), getContext());
            if(resID != 0) {
                bgImage.setImageResource(resID);
            } else {
                //if the image does not exist in "drawable" we assign a default image
                bgImage.setImageResource(recipeCardElement.getBgImgIDFromTitle(recipeCardElement.getDefaultImg(), getContext()));
            }
        }

        @Override
        public int getItemCount() {
            return test.length;
        }
    };

}
