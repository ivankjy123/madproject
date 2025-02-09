package com.example.madproject;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
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

public class activity_owner_information extends AppCompatActivity {

    private EditText ownerName, contactNumber, emailAddress, emergencyContact, remarks;
    private RadioGroup genderGroup;
    private RadioButton maleRadioButton, femaleRadioButton;
    private Button submitButton;

    private Calendar checkInCalendar = Calendar.getInstance();

    // Add check-in and check-out date EditText fields
    private EditText checkInDateEditText, checkOutDateEditText;
    private int mYear, mMonth, mDay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_owner_information);

            // Initialize views
            ownerName = findViewById(R.id.et_owner_name);
            contactNumber = findViewById(R.id.et_owner_contact);
            emailAddress = findViewById(R.id.et_email);
            emergencyContact = findViewById(R.id.emergency_contact);
            remarks = findViewById(R.id.remarks);
            genderGroup = findViewById(R.id.gender_group);
            maleRadioButton = findViewById(R.id.male_radio_button);
            femaleRadioButton = findViewById(R.id.female_radio_button);
            submitButton = findViewById(R.id.submit_button);

            // Initialize check-in and check-out date fields
            checkInDateEditText = findViewById(R.id.check_in_date);
            checkOutDateEditText = findViewById(R.id.check_out_date);


// Handle check-in date selection
        checkInDateEditText.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int mYear = calendar.get(Calendar.YEAR);
            int mMonth = calendar.get(Calendar.MONTH);
            int mDay = calendar.get(Calendar.DAY_OF_MONTH);

            // Set the minimum date to today's date
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    activity_owner_information.this,
                    (view, year, monthOfYear, dayOfMonth) -> {
                        checkInCalendar.set(year, monthOfYear, dayOfMonth); // Save the selected date
                        checkInDateEditText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                        // Clear the check-out date to avoid invalid selection
                        checkOutDateEditText.setText("");
                    },
                    mYear, mMonth, mDay);

            // Set minimum date to today
            calendar.set(Calendar.YEAR, mYear);
            calendar.set(Calendar.MONTH, mMonth);
            calendar.set(Calendar.DAY_OF_MONTH, mDay);
            datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());

            datePickerDialog.show();
        });


// Handle check-out date selection
        checkOutDateEditText.setOnClickListener(v -> {
            if (checkInDateEditText.getText().toString().isEmpty()) {
                // If check-in date is not set, prompt the user
                Toast.makeText(activity_owner_information.this,
                        "Please select a check-in date first",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            // Use the current date for the default date picker values
            Calendar calendar = Calendar.getInstance();
            int mYear = calendar.get(Calendar.YEAR);
            int mMonth = calendar.get(Calendar.MONTH);
            int mDay = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    activity_owner_information.this,
                    (view, year, monthOfYear, dayOfMonth) -> {
                        Calendar selectedDate = Calendar.getInstance();
                        selectedDate.set(year, monthOfYear, dayOfMonth);

                        // Validate check-out date against the check-in date
                        if (selectedDate.before(checkInCalendar)) {
                            Toast.makeText(activity_owner_information.this,
                                    "Check-out date cannot be earlier than check-in date",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            checkOutDateEditText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                        }
                    },
                    mYear, mMonth, mDay);

            // Set the minimum selectable date for check-out based on the check-in date
            datePickerDialog.getDatePicker().setMinDate(checkInCalendar.getTimeInMillis());
            datePickerDialog.show();
        });

            // Handle submit button click
            submitButton.setOnClickListener(v -> {
                String name = ownerName.getText().toString();
                String contact = contactNumber.getText().toString();
                String email = emailAddress.getText().toString();
                String emergency = emergencyContact.getText().toString();

                int selectedGenderId = genderGroup.getCheckedRadioButtonId();
                String gender = "Not Specified";
                if (selectedGenderId != -1) {
                    RadioButton selectedButton = findViewById(selectedGenderId);
                    gender = selectedButton.getText().toString();
                }

                // Get check-in and check-out dates
                String checkInDate = checkInDateEditText.getText().toString();
                String checkOutDate = checkOutDateEditText.getText().toString();

                // Validation
                if (name.isEmpty() || contact.isEmpty() || email.isEmpty() || emergency.isEmpty() || checkInDate.isEmpty() || checkOutDate.isEmpty()) {
                    Toast.makeText(activity_owner_information.this, "Please fill in all fields!", Toast.LENGTH_SHORT).show();
                } else {
                    // Get the pet information passed from the PetInformationActivity
                    Intent intent = getIntent();
                    String petInfo = intent.getStringExtra("PET_INFO");

                    // Send data to the ConfirmationPageActivity
                    Intent confirmationIntent = new Intent(activity_owner_information.this, activity_confirmation_page.class);
                    confirmationIntent.putExtra("PET_INFO", petInfo);
                    confirmationIntent.putExtra("OWNER_INFO", name + "\n" + contact + "\n" + email + "\n" + emergency);
                    confirmationIntent.putExtra("GENDER", gender);
                    confirmationIntent.putExtra("CHECK_IN_DATE", checkInDate);
                    confirmationIntent.putExtra("CHECK_OUT_DATE", checkOutDate);
                    startActivity(confirmationIntent);
                }
            });


        }
    public void btnSubmit(View view) {
        // Navigate back to LoginPage activity
        Intent intent = new Intent(activity_owner_information.this, activity_confirmation_page.class);
        startActivity(intent);
        finish(); // Optional: Close SignUpActivity after navigating back
    }
}