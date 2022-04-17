package org.me.gcu.traffic_scotland.ui.results_work_out;
//Adds imports to the Java file

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import org.me.gcu.traffic_scotland.EventDescription;
import org.me.gcu.traffic_scotland.R;
import org.me.gcu.traffic_scotland.ResultsEventAdapter;

import java.util.ArrayList;
import java.util.Objects;


/**
 * @author Euan Penman S1821916
 */
public class PlaceholderFragment extends Fragment {
    //Sets up priavte varibales for this class
    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;
    private ListView listEvents;


    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
//    Sets page for creation with events/events inforamtion
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_results, container, false);
        listEvents = root.findViewById(R.id.eventListView);

        switch (pageViewModel.getIndex()) {
            case 1:
                pageViewModel.getCurrentEvents().observe(getViewLifecycleOwner(), new Observer<ArrayList<EventDescription>>() {
                    @Override
                    public void onChanged(ArrayList<EventDescription> eventDescriptions) {
                        //TODO: filter events
                        ResultsEventAdapter resultsEventAdapter = new ResultsEventAdapter(
                                Objects.requireNonNull(getContext()), R.layout.event_for_traffic_information, eventDescriptions);
                        listEvents.setAdapter(resultsEventAdapter);
                    }
                });
                break;
            case 2:
                pageViewModel.getOnGoingRoadworks().observe(getViewLifecycleOwner(), new Observer<ArrayList<EventDescription>>() {
                    @Override
                    public void onChanged(ArrayList<EventDescription> eventDescriptions) {
                        ResultsEventAdapter resultsEventAdapter = new ResultsEventAdapter(
                                Objects.requireNonNull(getContext()), R.layout.event_for_traffic_information, eventDescriptions);
                        listEvents.setAdapter(resultsEventAdapter);
                    }
                });
                break;
            case 3:
                pageViewModel.getPlannedRoadworks().observe(getViewLifecycleOwner(), new Observer<ArrayList<EventDescription>>() {
                    @Override
                    public void onChanged(ArrayList<EventDescription> eventDescriptions) {
                        ResultsEventAdapter resultsEventAdapter = new ResultsEventAdapter(
                                Objects.requireNonNull(getContext()), R.layout.event_for_traffic_information, eventDescriptions);
                        listEvents.setAdapter(resultsEventAdapter);
                    }
                });
                break;
        }
        return root;
    }
}