package com.example.madproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Delay for 3 seconds (3000 ms) before navigating to LoginPage
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(MainActivity.this, LoginPage.class);
            startActivity(intent);
            finish(); // Close SplashActivity
        }, 2000); // 3000 ms delay
    }
}