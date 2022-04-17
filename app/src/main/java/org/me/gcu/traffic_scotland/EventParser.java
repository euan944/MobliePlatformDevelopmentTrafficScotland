package org.me.gcu.traffic_scotland;
//Adds imports to the Java file

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;

/**
 * @author Euan Penman S1821916
 */
public class EventParser {
    //Sets up priavte varibales for this class
    private static final String TAG = "TrafficEventParser";
    private ArrayList<EventDescription> eventDescriptions;

    public EventParser() {
        this.eventDescriptions = new ArrayList<>();
    }

    public ArrayList<EventDescription> getEvents() {
        return eventDescriptions;
    }

    public boolean parse(String xmlData, SpinnerOptionType type) {
        boolean status = true;
        EventDescription currentRecord = null;
        boolean inEntry = false;
        String textValue = "";

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(xmlData));
            int eventType = xpp.getEventType();
//When called the data from the website is stripped and set into variables till the end
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagName = xpp.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if ("item".equalsIgnoreCase(tagName)) {
                            inEntry = true;
                            currentRecord = new EventDescription();
                        }
                        break;
                    case XmlPullParser.TEXT:
                        textValue = xpp.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if (inEntry) {
                            if ("item".equalsIgnoreCase(tagName)) {
                                currentRecord.setEventType(type);
//                                currentRecord.parseAdditionalFeatures();
                                eventDescriptions.add(currentRecord);
                                inEntry = false;
                            } else if ("title".equalsIgnoreCase(tagName)) {
                                currentRecord.setTitle(textValue);
                            } else if ("description".equalsIgnoreCase(tagName)) {
                                currentRecord.setDescription(textValue);
                            } else if ("point".equalsIgnoreCase(tagName)) {
                                currentRecord.setLocation(textValue);
                            } else if ("pubDate".equalsIgnoreCase(tagName)) {
                                currentRecord.setPublishedDate(textValue);
                            }
                        }
                        break;
                }
                eventType = xpp.next();
            }
            Log.d(TAG, "parse: number of events " + eventDescriptions.size());
        } catch (Exception e) {
            status = false;
            e.printStackTrace();
        }

        return status;
    }
}
