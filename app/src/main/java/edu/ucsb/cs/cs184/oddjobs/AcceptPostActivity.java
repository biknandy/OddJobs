package edu.ucsb.cs.cs184.oddjobs;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AcceptPostActivity extends AppCompatActivity {
    public Intent currentIntent;
    public String postDetails;
    public String loc ;
    public String name;
    public String phone;
    public String reward;
    public String textMessage;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_acceptance);
        currentIntent = getIntent();
        postDetails =  currentIntent.getStringExtra("posting");
        TextView tv = (TextView) findViewById(R.id.PostView);
        tv.setText(postDetails);

        Button accept= (Button)findViewById(R.id.AcceptButton);

        accept.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                String[] splitar = postDetails.split(",");
                loc = splitar[0].split(": ")[1];
                name = splitar[3].split(": ")[1];
                phone = splitar[4].split(": ")[1];
                reward = splitar[2].split(": ")[1];
                textMessage = "Hi, my name is " + MainActivity.uname +", a bounty hunter, I have accepted your posting at: " + loc + " for the reward amount: " + reward;

                phone = "+17145124682";
                Log.d("SPLITAR",name);
                Log.d("SPLITAR",loc);
                Log.d("SPLITAR",phone);
                Log.d("SPLITAR",reward);
                Log.d("SPLITAR",textMessage);



                //sendSMS();
                SmsManager sms = SmsManager.getDefault();
                sms.sendTextMessage(phone, null, textMessage, null, null);
                ref.child("listings").child(name).child(loc).child("acceptedby").setValue(MainActivity.uname);

                finish();
            }
        });


        Button reject = (Button)findViewById(R.id.RejectButton);
        reject.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                finish();
            }
        });

    }


}
