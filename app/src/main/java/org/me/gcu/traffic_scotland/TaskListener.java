package org.me.gcu.traffic_scotland;

import java.util.ArrayList;

/**
 * @author Euan Penman S1821916
 */
public interface TaskListener {
    void newEvents(ArrayList<EventDescription> eventDescriptions);
}
