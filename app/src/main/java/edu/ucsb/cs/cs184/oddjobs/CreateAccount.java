package edu.ucsb.cs.cs184.oddjobs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CreateAccount extends AppCompatActivity {
    private EditText unameField;
    private EditText passField;
    private EditText phoneField;
    private EditText ageField;
    private Switch hunterBox;
    private Button createButton;
    private Button signinButton;

    private DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        unameField = findViewById(R.id.createName);
        passField = findViewById(R.id.createPass);
        phoneField = findViewById(R.id.phone);
        ageField = findViewById(R.id.ageField);
        hunterBox = findViewById(R.id.hunter);

        createButton = findViewById(R.id.createAccount);
        signinButton = findViewById(R.id.login);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db = FirebaseDatabase.getInstance().getReference();

                UserClass user = new UserClass(unameField.getText().toString(),
                        phoneField.getText().toString(),
                        passField.getText().toString(),
                        ageField.getText().toString(),
                        hunterBox.isChecked(), "0.00");

                db.child("users").child(unameField.getText().toString()).setValue(user);
                Intent intent =  new Intent(CreateAccount.this,SignInActivity.class);
                startActivity(intent);
            }

        });



    }


}
