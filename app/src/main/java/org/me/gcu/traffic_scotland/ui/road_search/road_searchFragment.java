package org.me.gcu.traffic_scotland.ui.road_search;
//Adds imports to the Java file

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import org.me.gcu.traffic_scotland.R;
import org.me.gcu.traffic_scotland.ResultsforRoadandDateViews;
import org.me.gcu.traffic_scotland.SearchTypeofRoad;
import org.me.gcu.traffic_scotland.databinding.FragmentRoadSearchBinding;

import java.util.Objects;

/**
 * @author Euan Penman S1821916
 */
public class road_searchFragment extends Fragment {
    //Sets up priavte varibales for this class
    private RadioGroup roadChoice;
    private FragmentRoadSearchBinding binding;
    private Spinner spinner;
    private String selectedRoad;
    private Button search;


    //When the page is create the follwoing is carried out below
    private AdapterView.OnItemSelectedListener onItemSelectedListener =
            new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String text = parent.getItemAtPosition(position).toString();
                    selectedRoad = text;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            };

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        road_searchViewModel galleryViewModel =
                new ViewModelProvider(this).get(road_searchViewModel.class);

        binding = FragmentRoadSearchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//Sets he spinner view option up for the user
        final Spinner spinner = root.findViewById(R.id.options);
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(Objects.requireNonNull(getContext()), R.array.motorways, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(onItemSelectedListener);
//Sets up the radio groupbutton
        roadChoice = root.findViewById(R.id.selection);
        roadChoice.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId) {
                    case R.id.motorway:
//                        Looks for array motorway and layout simple spinner
                        ArrayAdapter<CharSequence> motorwayAdapter = ArrayAdapter.createFromResource(root.getContext(), R.array.motorways, android.R.layout.simple_spinner_item);
                        spinner.setAdapter(motorwayAdapter);
                        adapter.notifyDataSetChanged();
                        break;
                    case R.id.aroad:
                        ArrayAdapter<CharSequence> aRoadAdapter = ArrayAdapter.createFromResource(root.getContext(), R.array.a_roads, android.R.layout.simple_spinner_item);
                        spinner.setAdapter(aRoadAdapter);
                        adapter.notifyDataSetChanged();
                        break;
                }
            }
        });


        search = root.findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {

            @Override
//The button is setup to take the results from the road_type and Road to then search and display reuslts
            public void onClick(View v) {
                RadioButton checkedButton = root.findViewById(roadChoice.getCheckedRadioButtonId());
                String text = (String) checkedButton.getText();
                SearchTypeofRoad searchTypeofRoad = getRoadType(text);

                Intent intent = new Intent(root.getContext(), ResultsforRoadandDateViews.class);

                intent.putExtra("ROAD_TYPE", searchTypeofRoad);
                intent.putExtra("ROAD", selectedRoad);
                startActivity(intent);
            }
        });

        return root;
    }

    //Gets the road the user clicked
    private SearchTypeofRoad getRoadType(String road) {
        SearchTypeofRoad rt;
        if (road.equalsIgnoreCase("motorway")) {
            rt = SearchTypeofRoad.MOTORWAY;
        } else {
            rt = SearchTypeofRoad.A_ROAD;
        }
        return rt;
    }

//When the page is exited the page is destoryed

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}