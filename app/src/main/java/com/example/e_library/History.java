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

import javax.security.auth.callback.CallbackHandler;

public class History extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageButton navButton;
    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        auth = FirebaseAuth.getInstance();
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navButton = findViewById(R.id.navigation_button);
        user = auth.getCurrentUser();

        // Set up the navigation view item click listener
        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    goToHomePage();
                    return true;
                case R.id.collection:
                    goToCollectionPage();
                    return true;
                case R.id.about:
                    goToAboutPage();
                    return true;
                case R.id.logout:
                    signOut();
                    return true;
                default:
                    return false;
            }
        });
        //Navigation button click listener
        navButton.setOnClickListener(view -> drawerLayout.openDrawer(GravityCompat.START));
        View headerView = navigationView.getHeaderView(0);
        TextView userEmailTextView = headerView.findViewById(R.id.user_email);
        userEmailTextView.setText(user.getEmail());}

    private void goToHomePage() {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }
    private void goToCollectionPage() {
        Intent intent = new Intent(this, Collection.class);
        startActivity(intent);
    }

    private void goToAboutPage() {
        Intent intent = new Intent(this, About.class);
        startActivity(intent);
    }
    private void signOut() {
        auth.signOut();
        Intent intent = new Intent(getApplicationContext(), login.class);
        startActivity(intent);
        finish();
    }

}