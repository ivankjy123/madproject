package com.example.madproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SignUp extends AppCompatActivity {

    private EditText edtEmail,edtUsername, edtIC, etPassword;
    private Button btnSave;
    private UserDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);

        // Initialize views
        edtEmail = findViewById(R.id.edtEmail);
        edtUsername = findViewById(R.id.edtUsername);
        edtIC = findViewById(R.id.edtIC);
        etPassword = findViewById(R.id.etPassword);
        btnSave = findViewById(R.id.btnSignup);

        dbHelper = new UserDatabaseHelper(this);

        // Set click listener for the save button
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString();  // Assuming edtUsername is the EditText for username
                String email = edtEmail.getText().toString();
                String password = etPassword.getText().toString();
                String icnoStr = edtIC.getText().toString();

                // Check if all fields are filled
                if (username.isEmpty() || email.isEmpty() || icnoStr.isEmpty() || password.isEmpty()) {
                    Toast.makeText(SignUp.this, "All fields are required", Toast.LENGTH_SHORT).show();
                } else {
                    // Check if the IC number is exactly 12 digits long
                    if (icnoStr.length() != 12) {
                        Toast.makeText(SignUp.this, "IC number must be exactly 12 digits", Toast.LENGTH_SHORT).show();
                    } else {
                        try {
                            // Check if the IC number contains only numeric characters
                            long icno = Long.parseLong(icnoStr);

                            // Check if email or IC number already exists in the database
                            if (dbHelper.doesEmailExist(email)) {
                                Toast.makeText(SignUp.this, "Email already exists", Toast.LENGTH_SHORT).show();
                            } else if (dbHelper.doesIcNoExist((int) icno)) {
                                Toast.makeText(SignUp.this, "IC number already exists", Toast.LENGTH_SHORT).show();
                            } else {
                                // Create a new User object with username, email, IC number, and password
                                User newUser = new User(username, email, (int) icno, password);

                                // Add user to the database
                                dbHelper.addUser(newUser);

                                // Show success toast and clear fields
                                Toast.makeText(SignUp.this, "User saved!", Toast.LENGTH_SHORT).show();
                                edtUsername.setText("");  // Clear username field
                                edtEmail.setText("");  // Clear email field
                                edtIC.setText("");  // Clear IC number field
                                etPassword.setText("");  // Clear password field
                                Intent intent = new Intent(SignUp.this, LoginPage.class);
                                startActivity(intent);
                            }
                        } catch (NumberFormatException e) {
                            // Show an error if the IC number is not a valid integer
                            Toast.makeText(SignUp.this, "Invalid IC number. It should be a numeric value", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

    }
    public void btnBack(View view) {
        // Navigate back to LoginPage activity
        Intent intent = new Intent(SignUp.this, LoginPage.class);
        startActivity(intent);
        finish(); // Optional: Close SignUpActivity after navigating back
    }
}