package com.example.madproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class day_care_register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_day_care_register);


        DayCareDatabaseHelper dbHelper = new DayCareDatabaseHelper(this);

        EditText petName = findViewById(R.id.et_pet_name);
        EditText petAge = findViewById(R.id.et_pet_age);
        EditText petBreed = findViewById(R.id.et_pet_breed);
        EditText ownerName = findViewById(R.id.et_owner_name);
        EditText ownerContact = findViewById(R.id.et_owner_contact);
        Button registerButton = findViewById(R.id.btn_register_pet);


        registerButton.setOnClickListener(v -> {
            String name = petName.getText().toString();
            String age = petAge.getText().toString();
            String breed = petBreed.getText().toString();
            String owner = ownerName.getText().toString();
            String contact = ownerContact.getText().toString();

            if (name.isEmpty() || age.isEmpty() || breed.isEmpty() || owner.isEmpty() || contact.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                boolean result = dbHelper.insertPet(name, age, breed, owner, contact);
                if (result) {
                    Toast.makeText(this, "Pet Registered Successfully", Toast.LENGTH_SHORT).show();
                    // Navigate back to the main page
                    Intent intent = new Intent(day_care_register.this, day_care_page.class);
                    startActivity(intent);
                    finish(); // Optional: Close this activity
                } else {
                    Toast.makeText(this, "Error Registering Pet", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}