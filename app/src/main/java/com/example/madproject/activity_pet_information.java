package com.example.madproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class activity_pet_information extends AppCompatActivity {
    private EditText petName, petType, petBreed, petAge, petColor;
    private RadioGroup genderGroup;
    private RadioButton maleRadioButton, femaleRadioButton;
    private RadioGroup aggressionGroup;
    private Button submitButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pet_information);

        // Initialize views
        petName = findViewById(R.id.et_pet_name);
        petType = findViewById(R.id.edit_pet_type);
        petBreed = findViewById(R.id.edit_pet_breed);
        petAge = findViewById(R.id.edit_pet_age);
        genderGroup = findViewById(R.id.radio_group_gender);
        maleRadioButton = findViewById(R.id.male_radio_button);
        femaleRadioButton = findViewById(R.id.female_radio_button);
        petColor = findViewById(R.id.edit_pet_color);
        aggressionGroup = findViewById(R.id.radio_group_behavior);
        submitButton = findViewById(R.id.btn_submit);

        // Handle submit button click
        submitButton.setOnClickListener(v -> {
            String name = petName.getText().toString();
            String type = petType.getText().toString();
            String breed = petBreed.getText().toString();
            String age = petAge.getText().toString();
            String color = petColor.getText().toString();

            int selectedAggressionId = aggressionGroup.getCheckedRadioButtonId();
            String aggression = "Not Specified";
            if (selectedAggressionId != -1) {
                RadioButton selectedButton = findViewById(selectedAggressionId);
                aggression = selectedButton.getText().toString();
            }

            int selectedGenderId = genderGroup.getCheckedRadioButtonId();
            String gender = "Not Specified";
            if (selectedGenderId != -1) {
                RadioButton selectedButton = findViewById(selectedGenderId);
                gender = selectedButton.getText().toString();
            }


            // Log the data for debugging
            Log.d("PetInfo", "Sending pet info: " + name + "\n" + type + "\n" + breed + "\n" + age + "\n" + gender + "\n" + color + "\n" + aggression);

            if (name.isEmpty() || type.isEmpty() || breed.isEmpty() || age.isEmpty() || gender.isEmpty() || color.isEmpty()) {
                Toast.makeText(activity_pet_information.this, "Please fill in all fields!", Toast.LENGTH_SHORT).show();
            } else {
                // Send data to the next activity (Owner Information)
                Intent intent = new Intent(activity_pet_information.this, activity_owner_information.class);
                intent.putExtra("PET_INFO", name + "\n" + type + "\n" + breed + "\n" + age + "\n" + gender + "\n" + color + "\n" + aggression);
                startActivity(intent);
            }
        });
    }

    public void btnSubmit(View view) {
        // Navigate back to LoginPage activity
        Intent intent = new Intent(activity_pet_information.this, activity_owner_information.class);
        startActivity(intent);
        finish(); // Optional: Close SignUpActivity after navigating back
    }
}