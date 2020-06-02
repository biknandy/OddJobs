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

import java.util.List;

public class FormActivity extends AppCompatActivity {

    private EditText nameField;

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_form);

        //name
        nameField = findViewById(R.id.nameField);

        //title field (heading of post)

        //job description

        //payment or payment

        //money stuff

        FloatingActionButton fab = findViewById(R.id.sendForm);
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v){
                Intent i = getIntent();
                List<String> pos = i.getStringArrayListExtra("position");
                Log.d("position", pos.toString());


                String nameText = nameField.getText().toString();
                Log.d("nameText", nameText);
                Intent personIntent = new Intent();
                personIntent.putExtra("name", nameText);
                setResult(Activity.RESULT_OK, personIntent);
                finish();
            }
        });
    }

}
