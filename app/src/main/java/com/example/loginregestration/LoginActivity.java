package com.example.loginregestration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText username, password;
    Button loginButton;
    DbHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);

        // Initialize the database handler
        dbHandler = new DbHandler(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass = password.getText().toString();

                if (user.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please enter both username and password", Toast.LENGTH_SHORT).show();
                } else {
                    // Check if username and password are correct and get the gender
                    String gender = dbHandler.getUserGender(user, pass);
                    if (gender != null) {
                        // Navigate to MalePageActivity or FemalePageActivity based on gender
                        Intent intent;
                        if (gender.equals("Male")) {
                            intent = new Intent(getApplicationContext(), MalePageActivity.class);
                        } else {
                            intent = new Intent(getApplicationContext(), FemalePageActivity.class);
                        }
                        startActivity(intent);
                    } else {
                        // If the login credentials are invalid
                        Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
