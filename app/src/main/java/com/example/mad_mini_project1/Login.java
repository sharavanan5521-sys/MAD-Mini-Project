package com.example.mad_mini_project1;

import android.content.Intent;
import android.media.MediaPlayer; // Import MediaPlayer
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Login extends AppCompatActivity {
    Button btnSignUp, btnSignIn;
    private MediaPlayer popSoundPlayer; // Declare MediaPlayer

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize the 'pop' sound player
        popSoundPlayer = MediaPlayer.create(this, R.raw.pop_sound);

        Animation scaleDown = AnimationUtils.loadAnimation(this, R.anim.scale_down);

        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignIn = findViewById(R.id.btnSignIn);

        btnSignUp.setOnClickListener(v -> {
            // Play sound and then start animation/intent
            playPopSound();
            v.startAnimation(scaleDown);
            Intent intent = new Intent(Login.this, Register.class);
            startActivity(intent);
        });

        btnSignIn.setOnClickListener(v -> {
            // Play sound and then start animation/intent
            playPopSound();
            v.startAnimation(scaleDown);
            Intent intent = new Intent(Login.this, Home.class);
            startActivity(intent);
        });
    }

    /**
     * Helper method to play the pop sound.
     * Uses seekTo(0) to ensure the sound plays from the start even if clicked rapidly.
     */
    private void playPopSound() {
        if (popSoundPlayer != null) {
            popSoundPlayer.seekTo(0);
            popSoundPlayer.start();
        }
    }

    // Crucial: Release the MediaPlayer resources when the Activity is destroyed
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (popSoundPlayer != null) {
            popSoundPlayer.release();
            popSoundPlayer = null;
        }
    }
}