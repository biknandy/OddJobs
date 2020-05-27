package edu.ucsb.cs.cs184.oddjobs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class UserTypeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_type);
        Button giver = findViewById(R.id.giver);
        Button hunter = findViewById(R.id.hunter);
        giver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserTypeActivity.this, MainActivity_Giver.class);
                startActivity(intent);
            }
        });

        hunter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserTypeActivity.this, MainActivity_Hunter.class);
                startActivity(intent);
            }
        });
    }
}
