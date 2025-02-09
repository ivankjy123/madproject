package com.example.madproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class day_care_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_day_care_page);
    }
    public void btnRegisterP(View view) {
        // Navigate back to LoginPage activity
        Intent intent = new Intent(day_care_page.this, day_care_register.class);
        startActivity(intent);

    }
    public void btnModifyP(View view) {
        // Navigate back to LoginPage activity
        Intent intent = new Intent(day_care_page.this, day_care_modify.class);
        startActivity(intent);

    }

    public void btnPetList(View view) {
        // Navigate back to LoginPage activity
        Intent intent = new Intent(day_care_page.this, PetListActivity.class);
        startActivity(intent);

    }
}