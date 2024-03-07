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

public class Home extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageButton navButton, chatbotButton;
    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        auth = FirebaseAuth.getInstance();
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        chatbotButton = findViewById(R.id.chatbotbutton);
        navButton = findViewById(R.id.navigation_button);
        user = auth.getCurrentUser();


        // Set up the navigation view item click listener
        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.collection:
                    goToCollectionPage();
                    return true;
                case R.id.history:
                    goToHistoryPage();
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

        chatbotButton.setOnClickListener(view -> new ChatBot());
    }

    private void goToCollectionPage() {
        Intent intent = new Intent(this, Collection.class);
        startActivity(intent);
    }
    private void goToHistoryPage() {
        Intent intent = new Intent(this, History.class);
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