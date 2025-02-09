package com.example.madproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import io.kommunicate.KmConversationBuilder;
import io.kommunicate.Kommunicate;
import io.kommunicate.callbacks.KmCallback;


import com.applozic.mobicommons.commons.core.utils.Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;


public class menu_page extends AppCompatActivity {

    private DrawerLayout drawerLayout;

    private NavigationView navigationView;  // Declare NavigationView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_page);

        ViewFlipper viewFlipper = findViewById(R.id.photoFlipper);
        // Find the button by its ID
        Button btnBoarding = findViewById(R.id.btnBoarding);

        // Start flipping automatically
        viewFlipper.startFlipping();

        // Initialize Kommunicate
        Kommunicate.init(this, "3726186dbebb65f581c2fe644720d138"); // Replace "your_app_id" with your actual App ID
        String botId = "aiyh-bot-2r9rz";
        FloatingActionButton fabChat = findViewById(R.id.fab_chat);

        fabChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Creating a new conversation builder
                new KmConversationBuilder(menu_page.this)
                        .setWithPreChat(true)  // Show pre-chat form before starting the conversation
                        .launchConversation(new KmCallback() {
                            @Override
                            public void onSuccess(Object message) {
                                // Log success and handle message if needed
                                Log.d("Conversation", "Success: " + message);
                                Toast.makeText(menu_page.this, "Conversation Started Successfully", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Object error) {
                                // Log failure and handle error if needed
                                Log.d("Conversation", "Failure: " + error);
                                Toast.makeText(menu_page.this, "Failed to start conversation", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });



        // Setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize DrawerLayout
        drawerLayout = findViewById(R.id.drawer_layout);

        // Initialize the NavigationView
        navigationView = findViewById(R.id.navigation_view);
        // Find the TextView in the header layout
        View headerView = navigationView.getHeaderView(0);  // Get the first header
        TextView txtWelcomeUser = headerView.findViewById(R.id.txtWelcomeuser);

        String username = getIntent().getStringExtra("USERNAME"); // Get the username from the intent
        txtWelcomeUser.setText("Welcome, " + username + " !");

        // Setup hamburger menu action
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size); // The hamburger icon
        toolbar.setNavigationOnClickListener(v -> drawerLayout.openDrawer(Gravity.LEFT));

        // Optional: Handle navigation item click
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            if (menuItem.getItemId() == R.id.nav_logout) {
                // Handle logout here
                startActivity(new Intent(menu_page.this, LoginPage.class));
                finish();
                return true;
            }

            if (menuItem.getItemId() == R.id.nav_aboutus) {
                // Handle logout here
                startActivity(new Intent(menu_page.this, AboutUs.class));
                return true;
            }

            return false;

        });
    }

    public void btnBoarding(View view) {
        // Navigate back to LoginPage activity
        Intent intent = new Intent(menu_page.this, boarding_page.class);
        startActivity(intent);

    }
    public void btnDayCare(View view) {
        // Navigate back to LoginPage activity
        Intent intent = new Intent(menu_page.this, day_care_page.class);
        startActivity(intent);
    }

    public void btnpetgal(View view) {
        // Navigate back to LoginPage activity
        Intent intent = new Intent(menu_page.this, CardList.class);
        startActivity(intent);
    }
}