package edu.ucsb.cs.cs184.oddjobs;

import com.google.firebase.database.IgnoreExtraProperties;


@IgnoreExtraProperties
public class Listing {
    public String name;
    public String phone;
    public String title;
    public String descrip;
    public String location;
    public String payment;
    public String acceptedby;
    public String lat;
    public String longitude;
    public Boolean hunter;
    public Boolean urgent;
    public String status;

    public Listing() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Listing(String name, String phone, String title, String descrip, String location, String payment,
    String lat, String longitude, Boolean urgent) {
        this.name = name;
        this.phone = phone;
        this.title = title;
        this.descrip = descrip;
        this.location = location;
        this.payment = payment;
        this.lat = lat;
        this.longitude = longitude;
        this.urgent = urgent;
        this.acceptedby = "none";
        this.status = "incomplete";
    }
}
