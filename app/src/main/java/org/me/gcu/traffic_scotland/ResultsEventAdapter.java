package org.me.gcu.traffic_scotland;
//Adds imports to the Java file

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.List;

/**
 * @author Euan Penman S1821916
 */
public class ResultsEventAdapter extends ArrayAdapter {
    //Sets up priavte varibales for this class
    private static final String TAG = "FeedAdapter";
    private final int layoutResource;
    private final LayoutInflater layoutInflater;
    private List<EventDescription> eventDescriptions;
    private Context context;

    public ResultsEventAdapter(@NonNull Context context, int resource, List<EventDescription> eventDescriptions) {
        super(context, resource);
        this.layoutResource = resource;
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.eventDescriptions = eventDescriptions;
    }

    @Override
    public int getCount() {
        return eventDescriptions.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView == null) {
            convertView = layoutInflater.inflate(layoutResource, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final EventDescription currentApp = eventDescriptions.get(position);

        viewHolder.EventTitle.setText(currentApp.getTitle());
        viewHolder.EventPubDate.setText(currentApp.getPublishedDate());
//The below displays the message to the user depending on the length of the event under the diretoed headings
        if (currentApp.getEventLength() == LengthofEvent.TEMPORALLY) {
            viewHolder.EventMessage.setText("This event should last less than a week.");
//            Displayed in Green colour
            viewHolder.EventMessage.setTextColor(Color.GREEN);
        } else if (currentApp.getEventLength() == LengthofEvent.IN_BETWEEN) {
            viewHolder.EventMessage.setText("This event should last less than a month.");
//            Displays in Yellow colour
            viewHolder.EventMessage.setTextColor(ContextCompat.getColor(context, R.color.purple_200));
        } else {
            viewHolder.EventMessage.setText("This event could last more than several months.");
//            Displys in Red colour
            viewHolder.EventMessage.setTextColor(Color.RED);
        }


        return convertView;
    }

    private class ViewHolder {
        final TextView EventTitle;
        final TextView EventPubDate;
        final TextView EventMessage;

        ViewHolder(View v) {
            this.EventTitle = v.findViewById(R.id.EventTitle);
            this.EventPubDate = v.findViewById(R.id.EventPubDate);
            this.EventMessage = v.findViewById(R.id.lengthMessage);
        }
    }
}
