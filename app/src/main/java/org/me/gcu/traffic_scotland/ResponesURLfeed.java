package org.me.gcu.traffic_scotland;

/**
 * @author Euan Penman S1821916
 */
public interface ResponesURLfeed {
    //    Sets up the Base URL
    String BASE_URL = "https://trafficscotland.org/rss/feeds";
    //    When currentIncidents called the following aspx is recived from the base URL plus the below
    String currentIncidents = BASE_URL + "/currentincidents.aspx";
    //    When ongoingRoadworks called the following aspx is recived from the base URL plus the below
    String ongoingRoadworks = BASE_URL + "/roadworks.aspx";
    //    When plannedRoadworks called the following aspx is recived from the base URL plus the below
    String plannedRoadworks = BASE_URL + "/plannedroadworks.aspx";
}
