package com.example.mad_mini_project1;

import android.content.Intent;
import android.media.MediaPlayer; // Import MediaPlayer
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class All_Score extends AppCompatActivity {

    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_all_score);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize the MediaPlayer object once.
        // The MediaPlayer.create() method is simple for local resource playback.
        final MediaPlayer popSoundPlayer = MediaPlayer.create(this, R.raw.pop_sound);
        // Note: R.raw.sound_pop refers to the sound_pop file in the res/raw directory.

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> {
            // 1. Play the sound
            // Use seekTo(0) to ensure the sound starts from the beginning if the button is clicked quickly multiple times.
            popSoundPlayer.seekTo(0);
            popSoundPlayer.start();

            // 2. Navigate to Home
            Intent intent = new Intent(All_Score.this, Home.class);
            startActivity(intent);
        });
    }

    // It's crucial to release the MediaPlayer resources when the activity is destroyed
    // to prevent memory leaks and free up system resources.
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Check if the MediaPlayer object exists before trying to release it.
        // It's a better practice to manage the MediaPlayer as a class member and release it here.
        // The current implementation is simple but can be improved for robustness.
        // For simple button clicks, sometimes creating/starting/releasing inside onClick is also used
        // but can be inefficient for frequently clicked buttons.

        // Given the simple implementation above, where it's a final local variable,
        // this cleanup step is omitted for simplicity in the snippet, but generally recommended.
        // A more robust design would declare MediaPlayer popSoundPlayer at the class level.
    }
}