package org.me.gcu.traffic_scotland;
//Adds imports to the Java file

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * @author Euan Penman S1821916
 */
public class LiveSaveRepository {
    //Sets up priavte varibales for this class
    private static final String TAG = "gcu.mpd.mtq2020.Repository";

    private static LiveSaveRepository instance;
    private MutableLiveData<ArrayList<EventDescription>> events = new MutableLiveData<>();
    private MutableLiveData<ArrayList<EventDescription>> onGoingRoadworks = new MutableLiveData<>();
    private MutableLiveData<ArrayList<EventDescription>> plannedRoadworks = new MutableLiveData<>();


    public static LiveSaveRepository getInstance() {
        if (instance == null) {
            synchronized (LiveSaveRepository.class) {
                if (instance == null) {
                    instance = new LiveSaveRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<ArrayList<EventDescription>> getAllEvents() {
        getCurrentEvents();
        getOnGoingRoadworks();
        getPlannedRoadworks();

        final MediatorLiveData liveData = new MediatorLiveData();
        liveData.addSource(events, new Observer() {
            @Override
            public void onChanged(Object value) {
                liveData.setValue(value);
            }
        });
        liveData.addSource(onGoingRoadworks, new Observer() {
            @Override
            public void onChanged(Object value) {
                liveData.setValue(value);
            }
        });
        liveData.addSource(plannedRoadworks, new Observer() {
            @Override
            public void onChanged(Object value) {
                liveData.setValue(value);
            }
        });

        return liveData;
    }

    public LiveData<ArrayList<EventDescription>> getCurrentEvents() {
        GetData data = new GetData(ResponesURLfeed.currentIncidents, SpinnerOptionType.CURRENT_INCIDENT);
        data.execute();
        return events;
    }

    public LiveData<ArrayList<EventDescription>> getOnGoingRoadworks() {
        GetData data = new GetData(ResponesURLfeed.ongoingRoadworks, SpinnerOptionType.ONGOING_ROADWORK);
        data.execute();
        return onGoingRoadworks;
    }

    public LiveData<ArrayList<EventDescription>> getPlannedRoadworks() {
        GetData data = new GetData(ResponesURLfeed.plannedRoadworks, SpinnerOptionType.PLANNED_ROADWORK);
        data.execute();
        return plannedRoadworks;
    }

    private class GetData extends AsyncTask<String, Void, String> {
        private static final String TAG = "GetData";
        private URL url;
        private EventParser eventParser;
        private SpinnerOptionType type;

        public GetData(String ResponesURLfeed, SpinnerOptionType type) {
            setURL(ResponesURLfeed);
            this.eventParser = new EventParser();
            this.type = type;
        }

        @Override
        protected String doInBackground(String... strings) {
            String rssFeed = downloadXML();

            if (rssFeed == null) {
                Log.e(TAG, "doInBackground: Error downloading");
            }
            return rssFeed;
        }

        @Override
        protected void onPostExecute(String rawFeed) {
            super.onPostExecute(rawFeed);
            boolean result = eventParser.parse(rawFeed, type);

            if (result) {
                switch (type) {
                    case CURRENT_INCIDENT:
                        events.postValue(eventParser.getEvents());
                        break;
                    case ONGOING_ROADWORK:
                        onGoingRoadworks.postValue(eventParser.getEvents());
                        break;
                    case PLANNED_ROADWORK:
                        plannedRoadworks.postValue(eventParser.getEvents());
                        break;
                }
            }
        }

        private String downloadXML() {
            StringBuilder xmlResult = new StringBuilder();

            try {
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                int charsRead;
                char[] inputBuffer = new char[500];
                while (true) {
                    charsRead = reader.read(inputBuffer);
                    if (charsRead < 0) {
                        break;
                    }
                    if (charsRead > 0) {
                        xmlResult.append(String.copyValueOf(inputBuffer, 0, charsRead));
                    }
                }
                reader.close();

                return xmlResult.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        private void setURL(String feedURL) {
            try {
                url = new URL(feedURL);
            } catch (MalformedURLException e) {
                Log.e(TAG, "setURL: Error creating URL obj", e);
            }
        }
    }
}
