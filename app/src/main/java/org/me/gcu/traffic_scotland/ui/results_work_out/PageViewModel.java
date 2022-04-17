package org.me.gcu.traffic_scotland.ui.results_work_out;
//Adds imports to the Java file

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.me.gcu.traffic_scotland.EventDescription;
import org.me.gcu.traffic_scotland.LiveSaveRepository;

import java.util.ArrayList;


/**
 * @author Euan Penman S1821916
 */
public class PageViewModel extends AndroidViewModel {
    //Sets up priavte varibales for this class
    private LiveSaveRepository repo = LiveSaveRepository.getInstance();
    private LiveData<ArrayList<EventDescription>> events;
    private LiveData<ArrayList<EventDescription>> onGoingRoadworks;
    private LiveData<ArrayList<EventDescription>> plannedRoadworks;
    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();

    public PageViewModel(Application application) {
        super(application);
        events = repo.getCurrentEvents();
        onGoingRoadworks = repo.getOnGoingRoadworks();
        plannedRoadworks = repo.getPlannedRoadworks();
    }

    public int getIndex() {
        return mIndex.getValue();
    }

    public void setIndex(int index) {
        mIndex.setValue(index);
    }

    public LiveData<ArrayList<EventDescription>> getCurrentEvents() {
        return events;
    }

    public LiveData<ArrayList<EventDescription>> getOnGoingRoadworks() {
        return onGoingRoadworks;
    }

    public LiveData<ArrayList<EventDescription>> getPlannedRoadworks() {
        return plannedRoadworks;
    }
}