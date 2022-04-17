package org.me.gcu.traffic_scotland.ui.home;
//Adds imports to the Java file

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.SupportMapFragment;

import net.danlew.android.joda.JodaTimeAndroid;

import org.me.gcu.traffic_scotland.EventDescription;
import org.me.gcu.traffic_scotland.SpinnerOptionType;
import org.me.gcu.traffic_scotland.FeedFetchRSS;
import org.me.gcu.traffic_scotland.MapsActivity;
import org.me.gcu.traffic_scotland.R;
import org.me.gcu.traffic_scotland.ResponesURLfeed;
import org.me.gcu.traffic_scotland.TaskListener;
import org.me.gcu.traffic_scotland.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.Objects;

/**
 * @author Euan Penman S1821916
 */
public class HomeFragment extends Fragment implements AdapterView.OnItemSelectedListener, TaskListener {
    //Sets up priavte varibales for this class
    private FragmentHomeBinding binding;
    private HomeViewModel homeViewModel;
    private Spinner spinner;
    private MapsActivity GoogleMaps;
    private TextView tvEventTotal;

    //When the page is created the following code is carried out
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
//        Sets the spinner option up for the user
        spinner = root.findViewById(R.id.spinneroption);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(Objects.requireNonNull(getContext()), R.array.event_choices, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        tvEventTotal = root.findViewById(R.id.totalEvents);
        JodaTimeAndroid.init(getContext());

        return root;
    }

    //When the page is exited the page is destoryed
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    //    Allows the user to select different items from the spinner
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        //varibale setup to be null
        FeedFetchRSS feedFetchRSS = null;
        //If the current incidents is selected recive all current incidents
        if (text.equalsIgnoreCase("current incidents")) {
            feedFetchRSS = new FeedFetchRSS(this, ResponesURLfeed.currentIncidents, SpinnerOptionType.CURRENT_INCIDENT);
        }
        //If the ongoing roadworks is selected recive all current incidents

        if (text.equalsIgnoreCase("ongoing roadworks")) {
            feedFetchRSS = new FeedFetchRSS(this, ResponesURLfeed.ongoingRoadworks, SpinnerOptionType.ONGOING_ROADWORK);
        }
        //If the planned roadworks is selected recive all current incidents

        if (text.equalsIgnoreCase("planned roadworks")) {
            feedFetchRSS = new FeedFetchRSS(this, ResponesURLfeed.plannedRoadworks, SpinnerOptionType.PLANNED_ROADWORK);
        }
//        Runs the fetchRSSFeed for the item pushed
        feedFetchRSS.execute();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        System.out.println("Nothing");
    }

    @Override
//    Allows the user to see all events displayed on the map
    public void newEvents(ArrayList<EventDescription> eventDescriptions) {
        tvEventTotal.setText("Number of events: " + eventDescriptions.size());
        GoogleMaps = new MapsActivity(eventDescriptions);

//        Displays on the map
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        if (mapFragment != null) {
            mapFragment.getMapAsync(GoogleMaps);
        }
    }


}
