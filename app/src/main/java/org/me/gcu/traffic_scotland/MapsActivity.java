package org.me.gcu.traffic_scotland;
//Adds imports to the Java file

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.me.gcu.traffic_scotland.databinding.ActivityMapsBinding;

import java.util.ArrayList;

/**
 * @author Euan Penman S1821916
 */
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    //Sets up priavte varibales for this class
    private ActivityMapsBinding binding;
    private GoogleMap GoogleMaps;
    private ArrayList<EventDescription> eventDescriptions;
    private EventDescription eventDescription = null;

    public MapsActivity(EventDescription eventDescription) {
        this.eventDescription = eventDescription;
    }

    public MapsActivity(ArrayList<EventDescription> eventDescriptions) {
        this.eventDescriptions = eventDescriptions;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    public void onMapReady(GoogleMap googleMap) {
        GoogleMaps = googleMap;
        GoogleMaps.clear();
        LatLng location = new LatLng(56.4907, -4.2026);

        if (eventDescription != null) {
            createMarker(eventDescription.getLatitude(), eventDescription.getLongitude(),
                    eventDescription.getTitle(), eventDescription.getDescription());
            location = new LatLng(eventDescription.getLatitude(), eventDescription.getLongitude());
        } else {
            for (int i = 0; i < eventDescriptions.size(); i++) {
                createMarker(eventDescriptions.get(i).getLatitude(), eventDescriptions.get(i).getLongitude(),
                        eventDescriptions.get(i).getTitle(), eventDescriptions.get(i).getDescription());
            }
        }
        GoogleMaps.moveCamera(CameraUpdateFactory.newLatLng(location));
    }

    private Marker createMarker(double latitude, double longitude,
                                String title, String description) {

        return GoogleMaps.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .anchor(0.5f, 0.5f)
                .title(title)
                .snippet(description));
    }
}