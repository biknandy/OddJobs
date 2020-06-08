package edu.ucsb.cs.cs184.oddjobs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.ucsb.cs.cs184.oddjobs.Listing;
import edu.ucsb.cs.cs184.oddjobs.R;

public class BountyListingAdapter extends ArrayAdapter<Listing> {
    public BountyListingAdapter(Context context, List<Listing> listings) {
        super(context, 0, listings);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Listing l = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_listing_bounty, parent, false);
        }
        // Lookup view for data population
        TextView listingTitle = (TextView) convertView.findViewById(R.id.listingTitle);
        TextView payout = (TextView) convertView.findViewById(R.id.listingPayout);
        // Populate the data into the template view using the data object
        listingTitle.setText(l.title);
        payout.setText("$" + l.payment + "0");
        // Return the completed view to render on screen
        return convertView;
    }
}
