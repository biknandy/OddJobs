package edu.ucsb.cs.cs184.oddjobs;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AcceptPostActivity extends AppCompatActivity {
    public Intent currentIntent;
    public String postDetails;

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
                String loc = splitar[0].split(": ")[1];
                String name = splitar[3].split(": ")[1];
                Log.d("SPLITAR",name);
                Log.d("SPLITAR",loc);
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
