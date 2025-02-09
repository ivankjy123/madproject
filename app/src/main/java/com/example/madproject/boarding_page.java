package com.example.madproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class boarding_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_boarding_page);

    }
    public void btnBook(View view) {
        // Navigate back to LoginPage activity
        Intent intent = new Intent(boarding_page.this, activity_pet_information.class);
        startActivity(intent);
        finish(); // Optional: Close SignUpActivity after navigating back
    }
}