package org.me.gcu.traffic_scotland;
//Adds imports to the Java file

import android.os.AsyncTask;
import android.util.Log;

import androidx.fragment.app.Fragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Euan Penman S1821916
 */
public class FeedFetchRSS extends AsyncTask<String, Void, String> {
    //Sets up priavte varibales for this class
    private static final String TAG = "FetchRSSFeed";
    private URL url;
    private EventParser eventParser;
    private SpinnerOptionType type;
    private TaskListener listener;


    public FeedFetchRSS(Fragment fragment, String feedURL, SpinnerOptionType type) {
        setURL(feedURL);
        this.eventParser = new EventParser();
        this.type = type;
        listener = (TaskListener) fragment;
    }

    private void setURL(String feedURL) {
        try {
            url = new URL(feedURL);
        } catch (MalformedURLException e) {
            Log.e(TAG, "setURL: Error creating URL obj", e);
        }
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
            listener.newEvents(eventParser.getEvents());
        }
    }

    private String downloadXML() {
        StringBuilder xmlResult = new StringBuilder();

        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            int response = connection.getResponseCode();
            Log.d(TAG, "downloadXML: Response code " + response);
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
}