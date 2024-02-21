package com.example.e_library;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Home extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageButton navButton;
    FirebaseAuth auth;
    Button button;
    TextView userName;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        auth = FirebaseAuth.getInstance();
        button = findViewById(R.id.logout);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navButton = findViewById(R.id.navigation_button);
        user = auth.getCurrentUser();
   View headerView = navigationView.getHeaderView(0);
   userName = headerView.findViewById(R.id.user_name);
// Displaying Name in to the Header
        if (user != null) {
            userName.setText(user.getDisplayName());
        }
        // Set up the navigation view item click listener
        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.logout:
                    signOut();
                    return true;
                default:
                    return false;
            }
        });
        // Set up the navigation button click listener
        navButton.setOnClickListener(view -> drawerLayout.openDrawer(GravityCompat.START));
    }

    private void signOut() {
        auth.signOut();
        Intent intent = new Intent(getApplicationContext(), login.class);
        startActivity(intent);
        finish();
    }
}