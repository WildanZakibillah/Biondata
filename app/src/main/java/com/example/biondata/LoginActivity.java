package com.example.biondata;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class LoginActivity extends AppCompatActivity {

    private EditText inputUser, inputPassword;
    private MaterialButton btnLogin;
    private TextView txtRegister;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputUser = findViewById(R.id.inputUser);
        inputPassword = findViewById(R.id.inputPassword);
        btnLogin = findViewById(R.id.btnLogin);
        txtRegister = findViewById(R.id.txtRegister);  // TextView untuk Register
        dbHelper = new DatabaseHelper(this);

        // Login button functionality
        btnLogin.setOnClickListener(v -> {
            String username = inputUser.getText().toString().trim();
            String password = inputPassword.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else {
                boolean isValid = dbHelper.checkUserCredentials(username, password);
                if (isValid) {
                    Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
                    // Navigate to another activity (e.g., Home)
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    finish();
                } else {
                    Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Register TextView click listener to navigate to RegisterActivity
        txtRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);  // Navigate to RegisterActivity
        });
    }
}
