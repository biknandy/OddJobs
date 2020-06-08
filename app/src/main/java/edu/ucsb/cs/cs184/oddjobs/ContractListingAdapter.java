package edu.ucsb.cs.cs184.oddjobs;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.ucsb.cs.cs184.oddjobs.Listing;
import edu.ucsb.cs.cs184.oddjobs.R;

public class ContractListingAdapter extends ArrayAdapter<Listing> {
    public ContractListingAdapter(Context context, List<Listing> listings) {
        super(context, 0, listings);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Listing l = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_listing_contractor, parent, false);
        }
        // Lookup view for data population
        TextView listingTitle = (TextView) convertView.findViewById(R.id.listingTitleCont);
        TextView status = (TextView) convertView.findViewById(R.id.listingStatus);
        TextView payment = (TextView) convertView.findViewById(R.id.listingPayCon);
        TextView descrip = (TextView) convertView.findViewById(R.id.listingDes);
        Button paymentButton = (Button) convertView.findViewById(R.id.paymentButton);
        // Populate the data into the template view using the data object
        listingTitle.setText(l.title);
        descrip.setText(l.descrip);
        payment.setText("$" + l.payment + "0");

        if (l.status.equals("inprogress")){
            status.setText("In Progress");
            status.setTextColor(Color.parseColor("#28a745"));
            paymentButton.setVisibility(View.VISIBLE);
        } else if (l.status.equals("incomplete")) {
            status.setText("Looking for Hunter");
            status.setTextColor(Color.parseColor("#dc3545"));
            paymentButton.setVisibility(View.GONE);
        } else {
            status.setText("Completed");
        }

        // Return the completed view to render on screen
        return convertView;
    }
}
