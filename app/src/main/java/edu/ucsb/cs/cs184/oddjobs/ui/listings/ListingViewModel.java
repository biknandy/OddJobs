package edu.ucsb.cs.cs184.oddjobs.ui.listings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ListingViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ListingViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}