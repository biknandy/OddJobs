package edu.ucsb.cs.cs184.oddjobs;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.EditText;
import android.widget.Switch;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class FormActivity extends AppCompatActivity {

    private EditText titleField;
    private EditText description;
    private EditText payment;
    private Switch urgent;

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_form);

        //title field (heading of post)
        titleField = findViewById(R.id.titleField);

        //job description
        description = findViewById(R.id.description);

        //payment
        payment = findViewById(R.id.paymentField);

        //urgent
        urgent = findViewById(R.id.urgent);

        //money stuff

        FloatingActionButton fab = findViewById(R.id.sendForm);
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v){
                Intent i = getIntent();

                String lat = i.getStringExtra("lat");
                String longitude = i.getStringExtra("long");
                String loc = i.getStringExtra("loc");
                String name = i.getStringExtra("name");
                String phone = i.getStringExtra("phone");
                Boolean utype = i.getBooleanExtra("utype", false);

                String titleText = titleField.getText().toString();
                String descriptionText = description.getText().toString();
                Double paymentNum = Double.parseDouble(payment.getText().toString());
                Boolean urg = urgent.isChecked();

                Log.d("urgent", urg.toString());



                Listing listing = new Listing(name,
                        phone,
                        titleText, descriptionText, loc, paymentNum.toString(), lat, longitude, urg);
//                Log.d("DEBUG",lat);
//                Log.d("DEBUG",longitude);

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

                ref.child("listings").child(name).child(loc.replaceAll("\\s", "")).setValue(listing);

                Intent intent = new Intent(FormActivity.this, MainActivity.class);
                intent.putExtra("uname", name);
                intent.putExtra("phone", phone);
                intent.putExtra("utype", utype);
                startActivity(intent);

//                Intent resultIntent = new Intent();
//                resultIntent.putExtra("title", titleText);
//                resultIntent.putExtra("location", loc);
//                resultIntent.putExtra("description", descriptionText);
//                resultIntent.putExtra("payment", paymentNum);
//                resultIntent.putExtra("lat", lat);
//                resultIntent.putExtra("long", longitude);
//                resultIntent.putExtra("urgent", urg);


                //setResult(Activity.RESULT_OK, resultIntent);
                //finish();
            }
        });
    }

}
