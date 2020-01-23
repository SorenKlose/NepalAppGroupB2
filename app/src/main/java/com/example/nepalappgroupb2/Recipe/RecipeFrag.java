package com.example.nepalappgroupb2.Recipe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nepalappgroupb2.Domain.DataService;
import com.example.nepalappgroupb2.Domain.searchWordProvider;
import com.example.nepalappgroupb2.R;

import java.util.ArrayList;
import java.util.List;

public class RecipeFrag extends Fragment implements Observer<String>, searchWordProvider {
    RecipeCardElement recipeCardElement = new RecipeCardElement();
    private final MutableLiveData<String> searchWord = new MutableLiveData<>();
    private RecyclerView recyclerView;

    List<RecipeCardElement> cardArray = DataService.getListOfRecipes();
    final List<RecipeCardElement> originalCardArray = cardArray;

    EditText searcField;
    TextView titleView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState) {
        View layout = inflater.inflate(R.layout.fragment_recipe_list, container, false);

        getSearchWord().observe( getActivity(), this);

        titleView = layout.findViewById(R.id.textView6);
        recyclerView = layout.findViewById(R.id.quiz_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        return layout;
    }

    FilterAdapter adapter = new FilterAdapter();

    class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.ViewHolderWithClickListener> implements Filterable{

        @NonNull
        @Override

        public ViewHolderWithClickListener onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = getLayoutInflater().inflate(R.layout.card_element, parent, false);
            return new ViewHolderWithClickListener(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolderWithClickListener holder, int position) {

            //setting title and background image for each of the cards
            holder.title.setText(cardArray.get(position).getCardTitle());

            int resID = recipeCardElement.getImgIdFromString(cardArray.get(position).getCardTitle(), getContext());
            if(resID != 0) {
                holder.bgImage.setImageResource(resID);
            } else {
                //if the image does not exist in "drawable" we assign a default image
                holder.bgImage.setImageResource(recipeCardElement.getImgIdFromString(recipeCardElement.getDefaultImg(), getContext()));
            }
        }

        public class ViewHolderWithClickListener extends RecyclerView.ViewHolder implements View.OnClickListener {
            TextView title;
            ImageView bgImage;
            public ViewHolderWithClickListener(@NonNull View itemView) {
                super(itemView);
                //finding the views
                title = itemView.findViewById(R.id.card_title);
                bgImage = itemView.findViewById(R.id.image_recipe);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                System.out.println(v+": I Was Clicked");
                if(getActivity().getClass() == RecipeActivity.class){
                    ((RecipeActivity)getActivity()).openPdf(cardArray.get(super.getAdapterPosition()).getPdfName());
                }
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
                                filteredList.add(originalCardArray.get(i));
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
    public MutableLiveData<String> getSearchWord() {
        return searchWord;
    }

    @Override
    public void onChanged(String s) {
        adapter.getFilter().filter(s);
    }


}
