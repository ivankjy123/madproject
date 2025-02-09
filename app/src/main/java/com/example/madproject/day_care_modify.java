package com.example.madproject;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class day_care_modify extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_day_care_modify);

        DayCareDatabaseHelper dbHelper = new DayCareDatabaseHelper(this);

        EditText searchPet = findViewById(R.id.et_search_pet);
        EditText petName = findViewById(R.id.et_pet_name);
        EditText petAge = findViewById(R.id.et_pet_age);
        EditText petBreed = findViewById(R.id.et_pet_breed);
        EditText ownerName = findViewById(R.id.et_owner_name);
        EditText ownerContact = findViewById(R.id.et_owner_contact);
        Button fetchButton = findViewById(R.id.btn_fetch_pet);
        Button modifyButton = findViewById(R.id.btn_modify_pet);

        fetchButton.setOnClickListener(v -> {
            String searchQuery = searchPet.getText().toString();
            if (searchQuery.isEmpty()) {
                Toast.makeText(this, "Enter pet name to search", Toast.LENGTH_SHORT).show();
            } else {
                Cursor cursor = dbHelper.getPetDetails(searchQuery);
                if (cursor != null && cursor.moveToFirst()) {
                    searchPet.setTag(cursor.getString(0)); // Store the ID in the tag
                    petName.setText(cursor.getString(1));
                    petAge.setText(cursor.getString(2));
                    petBreed.setText(cursor.getString(3));
                    ownerName.setText(cursor.getString(4));
                    ownerContact.setText(cursor.getString(5));
                    cursor.close();
                } else {
                    Toast.makeText(this, "No pet found", Toast.LENGTH_SHORT).show();
                }
            }
        });

        modifyButton.setOnClickListener(v -> {
            String id = (String) searchPet.getTag(); // Retrieve the ID
            String name = petName.getText().toString();
            String age = petAge.getText().toString();
            String breed = petBreed.getText().toString();
            String owner = ownerName.getText().toString();
            String contact = ownerContact.getText().toString();

            if (id == null || name.isEmpty() || age.isEmpty() || breed.isEmpty() || owner.isEmpty() || contact.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Validate numeric fields
            try {
                Integer.parseInt(age);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid age format", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!contact.matches("\\d{10,}")) {
                Toast.makeText(this, "Invalid contact number", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean result = dbHelper.updatePetDetails(name, age, breed, owner, contact, id);
            if (result) {
                Toast.makeText(this, "Pet Details Modified Successfully", Toast.LENGTH_SHORT).show();

                // Clear all fields
                searchPet.setText("");
                searchPet.setTag(null);
                petName.setText("");
                petAge.setText("");
                petBreed.setText("");
                ownerName.setText("");
                ownerContact.setText("");
            } else {
                Toast.makeText(this, "Error Modifying Pet Details", Toast.LENGTH_SHORT).show();
            }
        });



    }
}