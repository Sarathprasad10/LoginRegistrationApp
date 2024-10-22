package com.example.loginregestration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegisterActivity extends AppCompatActivity {

    EditText username, password, confirmPassword;
    RadioGroup genderGroup;
    RadioButton selectedGender;
    Button registerButton;
    DbHandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPassword);
        genderGroup = findViewById(R.id.genderGroup);
        registerButton = findViewById(R.id.registerButton);
        dbHandler=new DbHandler(this);

        registerButton.setOnClickListener(v -> {
            String user = username.getText().toString();
            String pass = password.getText().toString();
            String confPass = confirmPassword.getText().toString();
            int selectedId = genderGroup.getCheckedRadioButtonId(); // Get selected radio button ID

            selectedGender = findViewById(selectedId);
            String gender = selectedGender.getText().toString();
            boolean insertSuccess = dbHandler.InsertUser(user, pass, gender);
            if (insertSuccess) {
                Intent intent;
                if (gender.equals("Male")) {
                    intent = new Intent(getApplicationContext(), MalePageActivity.class);
                } else {
                    intent = new Intent(getApplicationContext(), FemalePageActivity.class);
                }
                startActivity(intent);
            }

        });
    }


}