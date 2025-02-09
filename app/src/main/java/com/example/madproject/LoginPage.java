package com.example.madproject;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginPage extends AppCompatActivity {

    private EditText edtEmail, edtPassword;
    private UserDatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_page);
        TextView signUpTextView = findViewById(R.id.btnSignup);
        TextView forgetPass = findViewById(R.id.forgetPassword);

        // Initialize views
        edtEmail = findViewById(R.id.edtEmail); // Make sure to add the appropriate ID in the XML
        edtPassword = findViewById(R.id.etPassword); // Same here for the password field
        dbHelper = new UserDatabaseHelper(this);

        // Set the click listener for the login button
        findViewById(R.id.btnLogin).setOnClickListener(v -> {
            String email = edtEmail.getText().toString();
            String password = edtPassword.getText().toString();

            // Check if email or password is empty
            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(LoginPage.this, "Email and password are required", Toast.LENGTH_SHORT).show();
            } else {
                // Check if the email and password are valid
                if (dbHelper.isUserValid(email, password)) {
                    // Retrieve the username after successful verification
                    String username = dbHelper.getUsernameByEmail(email);  // Assuming you create this method

                    // User is valid, navigate to menu page
                    Intent intent = new Intent(LoginPage.this, menu_page.class);
                    intent.putExtra("USERNAME", username);  // Pass the username to MenuPage
                    startActivity(intent);
                    finish(); // Optional: Close the LoginPage activity after navigating to menu page
                } else {
                    // Invalid email or password
                    Toast.makeText(LoginPage.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set the click listener on the sign-up TextView
        signUpTextView.setOnClickListener(v -> {
            // Start the LoginPage activity when the user clicks on the SignUp text
            Intent intent = new Intent(LoginPage.this, SignUp.class);
            startActivity(intent);
        });

        forgetPass.setOnClickListener(v -> {
            // Create a base message
            String message = "If you've forgotten your password, please contact support at ";
            String email = "b032410521@student.utem.edu.my";

            // Combine the message and email into one string
            String fullMessage = message + email;

            // Create a SpannableString from the full message
            SpannableString spannableMessage = new SpannableString(fullMessage);

            // Apply bold style to the email part
            int start = message.length();
            int end = fullMessage.length();
            spannableMessage.setSpan(new StyleSpan(Typeface.BOLD), start, end, 0);  // Make email bold

            // Show the AlertDialog with the formatted message
            new AlertDialog.Builder(LoginPage.this)
                    .setMessage(spannableMessage)  // Set the formatted message
                    .setCancelable(true)
                    .setPositiveButton("OK", (dialog, which) -> {
                        // Handle "OK" click if needed
                    })
                    .show();  // Show the dialog
        });

        // Inside your Activity or Fragment
        // Find the button by its ID
        AppCompatButton btnSignInWithGoogle = findViewById(R.id.btnSignInWithGoogle);
        AppCompatButton btnSignInWithApple = findViewById(R.id.btnSignInWithApple);
        AppCompatButton btnSignInWithFB = findViewById(R.id.btnSignInWithFB);

        // Set OnClickListener for Google Sign-In Button
        btnSignInWithGoogle.setOnClickListener(v -> {
            Toast.makeText(LoginPage.this, "Upgrading...", Toast.LENGTH_SHORT).show();
        });

        // Set OnClickListener for Apple Sign-In Button
        btnSignInWithApple.setOnClickListener(v -> {
            Toast.makeText(LoginPage.this, "Upgrading...", Toast.LENGTH_SHORT).show();
        });

        // Set OnClickListener for Facebook Sign-In Button
        btnSignInWithFB.setOnClickListener(v -> {
            Toast.makeText(LoginPage.this, "Upgrading...", Toast.LENGTH_SHORT).show();
        });


    }
        public void btnLogin(View view) {
            // Navigate back to LoginPage activity
            Intent intent = new Intent(LoginPage.this, menu_page.class);
            startActivity(intent);
            finish(); // Optional: Close SignUpActivity after navigating back
        }
}