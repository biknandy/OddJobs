package edu.ucsb.cs.cs184.oddjobs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AcceptPostActivity extends AppCompatActivity {
    public Intent currentIntent;
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_acceptance);
        currentIntent = getIntent();
        String postList =  currentIntent.getStringExtra("posting");
        TextView tv = (TextView) findViewById(R.id.PostView);
        tv.setText(postList);

        Button accept= (Button)findViewById(R.id.AcceptButton);

        accept.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
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
