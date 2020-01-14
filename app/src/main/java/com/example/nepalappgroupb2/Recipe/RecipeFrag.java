package com.example.nepalappgroupb2.Recipe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nepalappgroupb2.R;
import com.example.nepalappgroupb2.Domain.searchWordProvider;

import java.util.ArrayList;
import java.util.List;

public class RecipeFrag extends Fragment implements Observer<String>{
    RecipeCardElement recipeCardElement = new RecipeCardElement();

    private RecyclerView recyclerView;

    List<RecipeCardElement> cardArray = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState) {
        View layout = inflater.inflate(R.layout.recipe_card_layout, container, false);


        ((searchWordProvider)getActivity()).getSearchWord().observe(getActivity(), this);
        recyclerView = layout.findViewById(R.id.quiz_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        return layout;
    }

    FilterAdapter adapter = new FilterAdapter();

    final List<RecipeCardElement> originalCardArray = cardArray;

    class FilterAdapter extends RecyclerView.Adapter implements Filterable{

        @NonNull
        @Override

        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = getLayoutInflater().inflate(R.layout.card_element, parent, false);
            return new RecyclerView.ViewHolder(itemView) {};
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            //finding the views
            TextView title = holder.itemView.findViewById(R.id.card_title);
            ImageView bgImage = holder.itemView.findViewById(R.id.image_recipe);

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
            return cardArray.size();
        }
        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {

                @Override
                protected FilterResults performFiltering(CharSequence input) {
                    FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                    ArrayList<RecipeCardElement> filteredList = new ArrayList<RecipeCardElement>();

                    if (input == null || input.length() == 0) {

                        // set the Original result to return
                        results.values = originalCardArray;
                        results.count = originalCardArray.size();

                    } else {
                        for (int i = 0; i < originalCardArray.size(); i++) {
                            if (originalCardArray.get(i).containsSearchword(input)) {
                                filteredList.add(new RecipeCardElement(originalCardArray.get(i).getCardTitle()));
                            }
                        }
                        // set the Filtered result to return
                        results.values = filteredList;
                        results.count = filteredList.size();

                    }

                    return results;
                }
                @SuppressWarnings("unchecked")
                @Override
                protected void publishResults(CharSequence constraint,FilterResults results) {
                    cardArray = (ArrayList<RecipeCardElement>) results.values; // has the filtered values
                    notifyDataSetChanged();  // notifies the data with new filtered values
                }
            };
            return filter;
        }
    }


    @Override
    public void onChanged(String s) {
        adapter.getFilter().filter(s);
    }
}
