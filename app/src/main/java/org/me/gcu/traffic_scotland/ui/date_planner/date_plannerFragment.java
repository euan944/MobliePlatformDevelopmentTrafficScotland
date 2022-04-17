package org.me.gcu.traffic_scotland.ui.date_planner;
//Adds imports to the Java file

import android.app.DatePickerDialog;
import android.content.Intent;
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

import org.me.gcu.traffic_scotland.R;
import org.me.gcu.traffic_scotland.ResultsforRoadandDateViews;
import org.me.gcu.traffic_scotland.databinding.FragmentDatePlannerBinding;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Euan Penman S1821916
 */
public class date_plannerFragment extends Fragment {
    //Sets up priavte varibales for this class
    private FragmentDatePlannerBinding binding;
    private DatePickerDialog datepicker;
    private Calendar cal;
    private Date date;
    private EditText eText;
    private Button btnSearch;

    //When the page is created the following code is carried out
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        date_plannerViewModel slideshowViewModel =
                new ViewModelProvider(this).get(date_plannerViewModel.class);

        binding = FragmentDatePlannerBinding.inflate(inflater, container, false);
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

        //The button is set up
        btnSearch = root.findViewById(R.id.buttondate);
        btnSearch.setOnClickListener(new View.OnClickListener() {

            @Override
            // When the button is clicked the date will be used to provide results
            public void onClick(View v) {


                Intent intent = new Intent(root.getContext(), ResultsforRoadandDateViews.class);
                intent.putExtra("DATE", date);


                startActivity(intent);
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