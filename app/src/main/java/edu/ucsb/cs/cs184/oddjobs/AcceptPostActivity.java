package edu.ucsb.cs.cs184.oddjobs;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AcceptPostActivity extends AppCompatActivity {
    public Intent currentIntent;
    public String postDetails;
    public String loc ;
    public String name;
    public String phone;
    public String reward;
    public String textMessage;
    private Double lat;
    private Double longitude;
    private Listing l;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;
    private DatabaseReference ref;
    private DatabaseReference ref2;
    private String age;
    
    private TextView titleView;
    private TextView desView;
    private TextView locView;
    private TextView payView;
    private TextView nameView;
    private TextView ageView;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_acceptance);
        currentIntent = getIntent();
        postDetails =  currentIntent.getStringExtra("posting");
        lat = currentIntent.getDoubleExtra("lat", 0.0);
        longitude = currentIntent.getDoubleExtra("long", 0.0);
        
        titleView = (TextView) findViewById(R.id.titleAccept);
        desView = (TextView) findViewById(R.id.descripAccept);
        locView = (TextView) findViewById(R.id.locAccept);
        payView = (TextView) findViewById(R.id.payAccept);
        nameView = (TextView) findViewById(R.id.nameAccept);
        ageView = (TextView) findViewById(R.id.ageAccept);
        //tv.setText(postDetails);
        
        
        String[] splitar = postDetails.split(",");
        loc = (splitar[0].split(": ")[1]).replaceAll("\\s", "");
        name = splitar[3].split(": ")[1];
        
        ref = FirebaseDatabase.getInstance()
                .getReference("listings").child(name).child(loc);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                l = snapshot.getValue(Listing.class);
                titleView.setText(l.title);
                desView.setText(l.descrip);
                locView.setText(l.location);
                payView.setText("$" + l.payment + "0");
                payView.setTextColor(Color.parseColor("#28a745"));
                nameView.setText(l.name);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
        
        
        ref2 = FirebaseDatabase.getInstance()
                .getReference("users").child(name).child("age");
        
        ref2.addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot ageShot) {
                age = ageShot.getValue().toString();
                ageView.setText(age);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        }); 

        Button accept= (Button)findViewById(R.id.AcceptButton);

        accept.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                
                ref.child("acceptedby").setValue(MainActivity.uname);
                ref.child("status").setValue("inprogress");

                finish();
            }
        });


        Button reject = (Button)findViewById(R.id.RejectButton);
        reject.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                finish();
            }
        });
        
        Button contact = (Button)findViewById(R.id.contactButton);
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textMessage = "Hi, my name is " + MainActivity.uname +", a bounty hunter, I have accepted your posting at: " + l.location + " for the reward amount: " + l.payment;
                sendSMS(l, textMessage);
            }
        });

    }

    protected void sendSMS(Listing l, String message) {
        Log.i("Send SMS", "");
        Intent smsIntent = new Intent(Intent.ACTION_VIEW);
        //phone = "";
        smsIntent.setDataAndType(Uri.parse("smsto:"), "vnd.android-dir/mms-sms");
        //smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.putExtra("address"  , l.phone);
        smsIntent.putExtra("sms_body"  , message);

        try{
            startActivity(smsIntent);
            finish();
            Log.i("Finished sending SMS...", "");
        } catch (android.content.ActivityNotFoundException ex) {

        }
    }


}
