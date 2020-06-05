package edu.ucsb.cs.cs184.oddjobs.ui;

import java.util.ArrayList;

public class listingClass {
    public String names;
    public String title;
    public String location;
    public String description;
    public String payment;
    public String locX;
    public String locY;

    public listingClass(String names, String title, String location, String description, String payment, String locX, String locY){
        this.names = names;
        this.title = title;
        this.location = location;
        this.description = description;
        this.payment = payment;
        this.locX = locX;
        this.locY = locY;
    }
}
