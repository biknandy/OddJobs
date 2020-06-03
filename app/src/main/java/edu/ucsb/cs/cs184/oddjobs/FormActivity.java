package edu.ucsb.cs.cs184.oddjobs;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class FormActivity extends AppCompatActivity {

    private EditText nameField;
    private EditText titleField;
    private EditText description;
    private EditText payment;

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_form);

        //name
        nameField = findViewById(R.id.nameField);

        //title field (heading of post)
        titleField = findViewById(R.id.titleField);

        //job description
        description = findViewById(R.id.description);

        //payment
        payment = findViewById(R.id.paymentField);

        //money stuff

        FloatingActionButton fab = findViewById(R.id.sendForm);
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v){
                Intent i = getIntent();
                ArrayList<String> pos = i.getStringArrayListExtra("position");
                String loc = i.getStringExtra("loc");
                Log.d("position", pos.toString());


                String nameText = nameField.getText().toString();
                Log.d("nameText", nameText);
                String titleText = titleField.getText().toString();
                String descriptionText = description.getText().toString();
                Double paymentNum = Double.parseDouble(payment.getText().toString());

                Intent resultIntent = new Intent();
                resultIntent.putExtra("name", nameText);
                resultIntent.putExtra("title", titleText);
                resultIntent.putExtra("location", loc);
                resultIntent.putExtra("description", descriptionText);
                resultIntent.putExtra("payment", paymentNum);
                resultIntent.putStringArrayListExtra("position", pos);

                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });
    }

}
