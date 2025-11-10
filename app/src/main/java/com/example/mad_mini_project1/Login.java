package com.example.mad_mini_project1;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {

    private EditText etEmail, etPass;
    private Button btnSignIn, btnSignUp;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        session = new SessionManager(this);

        // auto skip if logged in
        if (session.isLoggedIn()) {
            goToHome();
            return;
        }

        etEmail = findViewById(R.id.etEmail);
        etPass = findViewById(R.id.etPass);
        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignUp = findViewById(R.id.btnSignUp);

        Animation scaleDown = AnimationUtils.loadAnimation(this, R.anim.scale_down);

        btnSignIn.setOnClickListener(v -> {
            v.startAnimation(scaleDown);

            String email = etEmail.getText().toString().trim();
            String pass = etPass.getText().toString().trim();

            if (email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (session.checkLogin(email, pass)) {
                Toast.makeText(this, "Welcome " + session.getName() + "!", Toast.LENGTH_SHORT).show();
                goToHome();
            } else {
                Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
            }
        });

        btnSignUp.setOnClickListener(v -> {
            v.startAnimation(scaleDown);
            startActivity(new Intent(Login.this, Register.class));
        });
    }

    private void goToHome() {
        Intent intent = new Intent(Login.this, Home.class);
        startActivity(intent);
        finish();
    }
}
