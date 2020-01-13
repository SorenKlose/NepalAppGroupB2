package com.example.nepalappgroupb2.Quiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nepalappgroupb2.R;
import com.example.nepalappgroupb2.Recipe.RecipeCardElement;

import java.util.ArrayList;
import java.util.List;

public class Quiz extends Fragment {
    RecipeCardElement cardElement = new RecipeCardElement();

    private RecyclerView recyclerView;

    List<RecipeCardElement> cardArray = new ArrayList<>();
    String[] quizliste = {"Quiz 1", "Quiz 2", "Quiz 3", "Quiz 4", "Quiz 5"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout =inflater.inflate(R.layout.recycler_view_fragment, container, false);

        for (int i = 0; i < quizliste.length; i++){
            cardArray.add(new RecipeCardElement(quizliste[i]));
        }
        recyclerView = layout.findViewById(R.id.quiz_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        return layout;
    }
    RecyclerView.Adapter adapter = new RecyclerView.Adapter() {
        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = getLayoutInflater().inflate(R.layout.quiz_card_layout, parent, false);
            return new RecyclerView.ViewHolder(itemView) {
            };
        }

        @Override
        public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
            TextView title = holder.itemView.findViewById(R.id.card_title);

            title.setText(cardArray.get(position).getCardTitle());



            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), QuizInner.class);
                    v.getContext().startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return quizliste.length;
        }
    };
}
