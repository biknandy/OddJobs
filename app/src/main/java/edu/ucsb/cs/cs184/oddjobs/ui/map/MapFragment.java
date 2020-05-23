package edu.ucsb.cs.cs184.oddjobs.ui.map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.*;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import edu.ucsb.cs.cs184.oddjobs.R;

public class MapFragment extends Fragment implements OnMapReadyCallback{

    private MapViewModel mapViewModel;
    private SupportMapFragment mMapView;
    private GoogleMap googleMap;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mapViewModel = ViewModelProviders.of(this).get(MapViewModel.class);


        View root = inflater.inflate(R.layout.fragment_home, container, false);

        //Map fragment stuff
        mMapView = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        //hide FAB
        FloatingActionButton fab = getActivity().findViewById(R.id.fab);
        fab.hide();

        mMapView.getMapAsync(this);

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }
    
    @Override
    public void onMapReady(GoogleMap gmap) {
        googleMap = gmap;
        final float zoomLevel = (float) 15.0;
        LatLng ucsb = new LatLng(34.412936, -119.847863);
        googleMap.addMarker(new MarkerOptions().position(ucsb)
                .title("Marker in IV"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ucsb, zoomLevel));


    }
}
