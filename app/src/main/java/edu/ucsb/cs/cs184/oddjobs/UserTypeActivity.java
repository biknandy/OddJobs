package edu.ucsb.cs.cs184.oddjobs;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class UserTypeActivity extends AppCompatActivity {
    public static String userType = "contractor";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usertype);

        Button contractor = findViewById(R.id.contractor);
        contractor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userType = "contractor";
                Intent intent =  new Intent(UserTypeActivity.this,SignInActivity.class);
                startActivity(intent);
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
