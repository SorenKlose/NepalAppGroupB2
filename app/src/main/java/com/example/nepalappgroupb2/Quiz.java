package com.example.nepalappgroupb2;

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

import java.util.ArrayList;
import java.util.List;

public class Quiz extends Fragment {
    CardElement cardElement = new CardElement();

    private RecyclerView recyclerView;

    List<CardElement> cardArray = new ArrayList<>();
    String[] quizliste = {"Month 1 Quiz", "Month 2 Quiz", "Month 3 Quiz", "Month 4 Quiz", "Month 5 Quiz"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout =inflater.inflate(R.layout.recycler_view_fragment, container, false);

        for (int i = 0; i < quizliste.length; i++){
            cardArray.add(new CardElement(quizliste[i]));
        }
        recyclerView = layout.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        return layout;
    }
    RecyclerView.Adapter adapter = new RecyclerView.Adapter() {
        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = getLayoutInflater().inflate(R.layout.card_layout, parent, false);
            return new RecyclerView.ViewHolder(itemView) {
            };
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            TextView title = holder.itemView.findViewById(R.id.card_title);

            title.setText(cardArray.get(position).getCardTitle());
        }

        @Override
        public int getItemCount() {
            return quizliste.length;
        }
    };
}
