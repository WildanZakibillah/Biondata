package com.example.biondata;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText inputEmail, inputPassword, inputConfirmPassword;
    private MaterialButton btnRegister;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize views
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        inputConfirmPassword = findViewById(R.id.inputConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);

        // Initialize database helper
        dbHelper = new DatabaseHelper(this);

        // Set OnClickListener for Register button
        btnRegister.setOnClickListener(v -> {
            String email = inputEmail.getText().toString().trim();
            String password = inputPassword.getText().toString().trim();
            String confirmPassword = inputConfirmPassword.getText().toString().trim();

            // Input validation
            if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else if (!password.equals(confirmPassword)) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            } else {
                // Try to register the user
                boolean isRegistered = dbHelper.registerUser(email, password);
                if (isRegistered) {
                    Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();
                    finish(); // Close the Register Activity
                } else {
                    Toast.makeText(this, "Registration failed. Try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
