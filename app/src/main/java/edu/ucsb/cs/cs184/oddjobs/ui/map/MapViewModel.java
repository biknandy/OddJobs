package edu.ucsb.cs.cs184.oddjobs.ui.map;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MapViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MapViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Test");
    }

    public LiveData<String> getText() {
        return mText;
    }
}