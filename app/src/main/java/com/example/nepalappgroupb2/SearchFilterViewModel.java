package com.example.nepalappgroupb2;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SearchFilterViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private final MutableLiveData<String> searchWord = new MutableLiveData<>();

    public LiveData<String> getSearchWord() {
        System.out.println("getting searchword");
        return searchWord;
    }

    public SearchFilterViewModel() {
        // trigger user load.
    }

    void setSearchWord(String s) {
        searchWord.setValue(s);
    }
}
