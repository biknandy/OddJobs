package edu.ucsb.cs.cs184.oddjobs.ui.listings;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import edu.ucsb.cs.cs184.oddjobs.BountyListingAdapter;
import edu.ucsb.cs.cs184.oddjobs.ContractListingAdapter;
import edu.ucsb.cs.cs184.oddjobs.Listing;
import edu.ucsb.cs.cs184.oddjobs.MainActivity;
import edu.ucsb.cs.cs184.oddjobs.R;

public class ListingFragment extends Fragment {

    private ListingViewModel listingViewModel;
    private List<Listing> listings;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        listingViewModel =
                ViewModelProviders.of(this).get(ListingViewModel.class);
        View root = inflater.inflate(R.layout.fragment_listings, container, false);

        listings = new ArrayList<>();
        //final TextView textView = root.findViewById(R.id.text_gallery);
//        listingViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Listings");


        if (!MainActivity.utype){
            //CONTRACTOR

            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("listings").child(MainActivity.uname);
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                        Listing l = postSnapshot.getValue(Listing.class);
                        listings.add(l);
                    }

                    // Create the adapter to convert the array to views
                    ContractListingAdapter adapter = new ContractListingAdapter(getActivity(), listings);
                    //adapter.addAll(listings);
                    // Attach the adapter to a ListView
                    ListView lView = (ListView) getActivity().findViewById(R.id.listingList);
                    lView.setAdapter(adapter);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } else {
            //BOUNTY HUNTER
            //load all markers onto map from database BOUNTY HUNTER
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("listings");
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                        Iterable<DataSnapshot> postChildren = postSnapshot.getChildren();

                        for (DataSnapshot listing : postChildren) {
                            Listing l = listing.getValue(Listing.class);
                            //Log.d("hello", l.toString());
                            listings.add(l);
                        }

                    }

                    Log.d("Test", listings.toString());

                    // Create the adapter to convert the array to views
                    BountyListingAdapter adapter = new BountyListingAdapter(getActivity(), listings);
                    //adapter.addAll(listings);
                    // Attach the adapter to a ListView
                    ListView lView = (ListView) getActivity().findViewById(R.id.listingList);
                    lView.setAdapter(adapter);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });



        }
        return root;
    }
}
