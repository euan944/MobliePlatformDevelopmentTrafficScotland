package org.me.gcu.traffic_scotland;
//Adds imports to the Java file

import org.joda.time.Duration;
import org.joda.time.Interval;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author Euan Penman S1821916
 */
public class EventDescription implements Serializable {
    //Sets up priavte varibales for this class
    private static final String TAG = "Event";
    private String title;
    private String description;
    private double latitude;
    private double longitude;
    private String publishedDate;
    private SpinnerOptionType type;
    private Date startDate = null;
    private Date endDate = null;
    private LengthofEvent lengthofEvent = null;
//    private String delayInformation = null;
//    private String locationInfo = null;
//    private String laneClosures = null;
    private String workDetails = null;
    private String trafficManagement = null;
//    private String diversionInfo = null;

    //Getter for Title
    public String getTitle() {
        return title;
    }

    //Setter for Title
    public void setTitle(String title) {
        this.title = title;
    }

    //Getter for Description
    public String getDescription() {
        return description;
    }

    //Setter for Description
    public void setDescription(String description) {
        this.description = description;
    }

    //Getter for PublishedDate
    public String getPublishedDate() {
        return publishedDate;
    }

    //Setter for PublishedDate
    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    //Getter for Location
    public String getLocation() {
        return String.format(Locale.ENGLISH, "%f,%f", latitude, longitude);
    }

    //Getter for Location
    public void setLocation(String geoLocation) {
        String[] splitStr = geoLocation.split("\\s+");
        this.latitude = Double.parseDouble(splitStr[0]);
        this.longitude = Double.parseDouble(splitStr[1]);
    }

    //Getter for Latitude
    public double getLatitude() {
        return latitude;
    }

    //Getter for Longitude
    public double getLongitude() {
        return longitude;
    }

    //Getter for gStartDate
    public Date getStartDate() {
        return startDate;
    }

    //Getter for SimpleStartDate
    public String getSimpleStartDate() {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(startDate);
    }

    //Getter for SimpleDateRange
    public String getSimpleDateRange() {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(startDate) + " to " + df.format(endDate);
    }

    //Getter for EndDate
    public Date getEndDate() {
        return endDate;
    }

    //Setter for EventType
    public void setEventType(SpinnerOptionType type) {
        this.type = type;
    }

    //Getter for EventLength
    public LengthofEvent getEventLength() {
        return lengthofEvent;
    }

    //Setter for EventLength
    public void setEventLength() {
        Interval interval = new Interval(startDate.getTime(), endDate.getTime());
        Duration period = interval.toDuration();
        long days = period.getStandardDays();
//Does a check on each event to decide what event should be placed in lengthofevent
        if (days <= 7) {
            lengthofEvent = LengthofEvent.TEMPORALLY;
        } else if (days <= 31) {
            lengthofEvent = LengthofEvent.IN_BETWEEN;
        } else {
            lengthofEvent = LengthofEvent.LENGTHY;
        }
    }

    //Getter for TrafficManagement
    public String getTrafficManagement() {
        return trafficManagement;
    }

    //Getter for WorkDetails
    public String getWorkDetails() {
        return workDetails;
    }


}

