package org.me.gcu.traffic_scotland.ui.journey_planner;
//Adds imports to the Java file

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.SupportMapFragment;

import org.me.gcu.traffic_scotland.R;
import org.me.gcu.traffic_scotland.databinding.FragmentJourneyPlannerBinding;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Euan Penman S1821916
 */
public class journey_plannerFragment extends Fragment {
    //Sets up priavte varibales for this class
    private FragmentJourneyPlannerBinding binding;

    private Calendar cal;
    private Date date;
    private EditText eText;
    private Button btnSearch;
    private DatePickerDialog datepicker;
    private Button showResult;
    private SupportMapFragment mapFragment;


    //When the page is created the following code is carried out
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        journey_plannerViewModel galleryViewModel =
                new ViewModelProvider(this).get(journey_plannerViewModel.class);

        binding = FragmentJourneyPlannerBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//The below allows for a calender view to be created for the user to select a date
        this.cal = Calendar.getInstance();
        eText = root.findViewById(R.id.eventDate);
        eText.setInputType(InputType.TYPE_NULL);
        eText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int day = cal.get(Calendar.DAY_OF_MONTH);
                int month = cal.get(Calendar.MONTH);
                int year = cal.get(Calendar.YEAR);

                datepicker = new DatePickerDialog(root.getContext(),
                        new DatePickerDialog.OnDateSetListener() {

                            //On date select it take a year month and day

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                eText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                cal.set(Calendar.DAY_OF_MONTH, view.getDayOfMonth());
                                cal.set(Calendar.MONTH, view.getMonth());
                                cal.set(Calendar.YEAR, view.getYear());
                                date = new Date(cal.getTimeInMillis());
                            }
                        }, year, month, day);
                datepicker.show();
            }
        });

        return root;
    }
//When the page is exited the page is destoryed

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}