package edu.ucsb.cs.cs184.oddjobs;

import com.google.firebase.database.IgnoreExtraProperties;


@IgnoreExtraProperties
public class UserClass {
    public String uname;
    public String phone;
    public String pass;
    public Boolean hunter;

    public UserClass() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public UserClass(String uname, String phone, String pass, Boolean hunter) {
        this.uname = uname;
        this.phone = phone;
        this.pass = pass;
        this.hunter = hunter;

    }
}
