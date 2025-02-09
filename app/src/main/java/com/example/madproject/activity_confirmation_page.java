package com.example.madproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class activity_confirmation_page extends AppCompatActivity {

    private PetDatabaseHelper petDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation_page);

        // Initialize views
        TextView petInformationTextView = findViewById(R.id.text_pet_information);
        TextView ownerInformationTextView = findViewById(R.id.text_owner_information);
        TextView checkInTextView = findViewById(R.id.text_check_in_date);
        TextView checkOutTextView = findViewById(R.id.text_check_out_date);
        Button editButton = findViewById(R.id.btn_edit_information);
        Button submitButton = findViewById(R.id.btn_submit_to_database);

        // Retrieve data passed from the previous activity
        Intent intent = getIntent();
        String petInfo = intent.getStringExtra("PET_INFO");
        String ownerInfo = intent.getStringExtra("OWNER_INFO");
        String checkInDate = intent.getStringExtra("CHECK_IN_DATE");
        String checkOutDate = intent.getStringExtra("CHECK_OUT_DATE");

        // Display the pet and owner information
        petInformationTextView.setText("Pet Information: \n" + petInfo);
        ownerInformationTextView.setText("Owner Information: \n" + ownerInfo);
        checkInTextView.setText("Check-in Date: " + checkInDate);
        checkOutTextView.setText("Check-out Date: " + checkOutDate);

        // Initialize database helper
        petDatabaseHelper = new PetDatabaseHelper(this);

        // Handle Edit Information button click (redirect to PetInformationActivity)
        editButton.setOnClickListener(v -> {
            // Navigate back to PetInformationActivity to allow edits
            Intent editIntent = new Intent(activity_confirmation_page.this, activity_pet_information.class);
            startActivity(editIntent);
        });

        // Handle Submit Information button click (save to database)
        submitButton.setOnClickListener(v -> {
            try {
                // Validate input to ensure all fields are not null or empty
                if (petInfo == null || ownerInfo == null || checkInDate == null || checkOutDate == null ||
                        petInfo.isEmpty() || ownerInfo.isEmpty() || checkInDate.isEmpty() || checkOutDate.isEmpty()) {
                    Toast.makeText(this, "Missing information. Please try again.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Parse pet and owner information with error handling in case the format is incorrect
                String[] petData = petInfo.split("\n");
                if (petData.length < 7) {
                    Toast.makeText(this, "Pet information is incomplete. Please check the data.", Toast.LENGTH_SHORT).show();
                    return;
                }

                String petName = extractValue(petData[0]);
                String petType = extractValue(petData[1]);
                String petBreed = extractValue(petData[2]);
                String petAge = extractValue(petData[3]);
                String petGender = extractValue(petData[4]);
                String petColor = extractValue(petData[5]);
                String petAggression = extractValue(petData[6]);

                String[] ownerData = ownerInfo.split("\n");
                if (ownerData.length < 4) {
                    Toast.makeText(this, "Owner information is incomplete. Please check the data.", Toast.LENGTH_SHORT).show();
                    return;
                }

                String ownerName = extractValue(ownerData[0]);
                String ownerContact = extractValue(ownerData[1]);
                String ownerEmail = extractValue(ownerData[2]);
                String ownerEmergency = extractValue(ownerData[3]);

                // Insert data into the database
                petDatabaseHelper.insertPetInformation(petName, petType, petBreed, petAge, petGender, petColor, petAggression);
                petDatabaseHelper.insertOwnerInformation(ownerName, ownerContact, ownerEmail, ownerEmergency);
                petDatabaseHelper.insertBookingInformation(petName, checkInDate, checkOutDate);

                // Display success message
                Toast.makeText(activity_confirmation_page.this, "Information saved successfully!", Toast.LENGTH_SHORT).show();

                // Navigate to BroadingPage and finish this activity
                Intent finishIntent = new Intent(activity_confirmation_page.this, boarding_page.class);
                startActivity(finishIntent);
                finish(); // Close current activity
            } catch (Exception e) {
                // Handle errors gracefully
                Toast.makeText(activity_confirmation_page.this, "An error occurred: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Helper method to extract value from formatted data
    private String extractValue(String data) {
        if (data.contains(": ")) {
            return data.split(": ")[1].trim();
        }
        return data.trim(); // Return the whole value if the expected format is not found
    }
}