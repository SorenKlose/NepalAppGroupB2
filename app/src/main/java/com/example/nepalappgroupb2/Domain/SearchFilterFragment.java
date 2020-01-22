package com.example.nepalappgroupb2.Domain;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.nepalappgroupb2.R;

public class SearchFilterFragment extends Fragment {
    private EditText searchEditText;

    public static SearchFilterFragment newInstance() {
        return new SearchFilterFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.search_filter_fragment, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        searchEditText = getView().findViewById(R.id.searchWordEditText);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                //enten er parent en activity eller en fragment
                if(getParentFragment() == null) {
                    ((searchWordProvider)getActivity()).getSearchWord().setValue(s.toString());

                }else{
                    ((searchWordProvider)getParentFragment()).getSearchWord().setValue(s.toString());

                }


            }
        });
    }

}
