package org.me.gcu.traffic_scotland.ui.journey_planner;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * @author Euan Penman S1821916
 */
public class journey_plannerViewModel extends ViewModel {
    //Sets up priavte varibales for this class
    private final MutableLiveData<String> mText;

    public journey_plannerViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}