package org.me.gcu.traffic_scotland.ui.road_search;
//Adds imports to the Java file

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * @author Euan Penman S1821916
 */
public class road_searchViewModel extends ViewModel {
    //Sets up priavte varibales for this class
    private final MutableLiveData<String> mText;

    public road_searchViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}