package org.me.gcu.traffic_scotland;
//Adds imports to the Java file

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.SupportMapFragment;

/**
 * @author Euan Penman S1821916
 */
public class EventDetails extends AppCompatActivity {
    //Sets up priavte varibales for this class
    private EventDescription eventDescription;
    private TextView EventTitle;
    private TextView EventLocation;
    private TextView EventDesc;
    private TextView EventGPS;
    private TextView EventTrafficManagement;
    private TextView EventWorksInfo;
    private MapsActivity GoogleMaps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        eventDescription = (EventDescription) intent.getSerializableExtra("EVENT");

        //Each event must have details when set
        EventTitle.setText(eventDescription.getTitle());
        EventLocation.setText(eventDescription.getTitle());
        EventDesc.setText(eventDescription.getDescription());
        EventGPS.setText(eventDescription.getLocation());
        EventTrafficManagement.setText(eventDescription.getTrafficManagement());
        EventWorksInfo.setText(eventDescription.getWorkDetails());
        //Sends the data to be added to the map fragment
        GoogleMaps = new MapsActivity(eventDescription);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(GoogleMaps);
    }
}
