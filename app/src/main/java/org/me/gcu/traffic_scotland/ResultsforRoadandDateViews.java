package org.me.gcu.traffic_scotland;
//Adds imports to the Java file

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import org.me.gcu.traffic_scotland.ui.results_work_out.PagerAdapterforResults;

import java.util.Date;

/**
 * @author Euan Penman S1821916
 */
public class ResultsforRoadandDateViews extends AppCompatActivity {
    public Date date;
    public String road;
    ViewPager viewPager;

    @Override
//    The code below runs the date/road picker after the page has been excused when a user has selected a date/road
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Loads the layout
        setContentView(R.layout.activity_results_date_and_road_picker);
        PagerAdapterforResults pagerAdapterforResults = new PagerAdapterforResults(this, getSupportFragmentManager());
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(pagerAdapterforResults);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    // this event will enable the back
    // function to the button on press
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }




    }
