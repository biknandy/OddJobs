package edu.ucsb.cs.cs184.oddjobs;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignInActivity extends AppCompatActivity {
    public static String username = "";
    public static String userType = UserTypeActivity.getUserType();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        Button signin = findViewById(R.id.signin);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Users");
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        EditText user = findViewById(R.id.textView4);
                        EditText pass = findViewById(R.id.editTextTextPassword);
                        if (snapshot.hasChild(user.getText().toString()) ) {
                            Log.d("SUCESS",snapshot.child(user.getText().toString()).getValue(String.class));
                            Log.d("SUCESS",pass.getText().toString());
                            if(pass.getText().toString().equals(snapshot.child(user.getText().toString()).getValue(String.class))){
                                Log.d("SUCESS",snapshot.child(user.getText().toString()).getValue(String.class));
                                username = user.getText().toString();
                                Intent intent =  new Intent(SignInActivity.this,MainActivity.class);
                                startActivity(intent);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }

                });


            }

        });

    }

    public static String getUserType(){
        return userType;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}