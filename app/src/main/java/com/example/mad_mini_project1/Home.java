package com.example.mad_mini_project1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer; // Import MediaPlayer
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Home extends AppCompatActivity {

    FrameLayout flLearn, flScore;
    Animation scaleUp, scaleDown;
    private MediaPlayer backgroundSoundPlayer; // For reveal.mp3 (activity start)
    private MediaPlayer clickSoundPlayer;      // For sound_pop (button clicks)

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 2. Initialize the 'pop' sound player
        clickSoundPlayer = MediaPlayer.create(this, R.raw.pop_sound);

        flLearn = findViewById(R.id.flLearn);
        flScore = findViewById(R.id.flScore);
        scaleUp = AnimationUtils.loadAnimation(this, R.anim.scale_up);
        scaleDown = AnimationUtils.loadAnimation(this, R.anim.scale_down);

        View.OnTouchListener touchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        v.startAnimation(scaleUp);
                        return true;

                    case MotionEvent.ACTION_UP:
                        v.startAnimation(scaleDown);
                        v.postDelayed(v::performClick, 50);
                        return true;

                    case MotionEvent.ACTION_CANCEL:
                        v.startAnimation(scaleDown);
                        return true;
                }
                return false;
            }
        };

        flLearn.setOnTouchListener(touchListener);
        flScore.setOnTouchListener(touchListener);


        flLearn.setOnClickListener(v -> {
            // Play click sound
            playClickSound();
            Intent intent = new Intent(Home.this, Learn.class);
            startActivity(intent);
        });

        flScore.setOnClickListener(v -> {
            // Play click sound
            playClickSound();
            Intent intent = new Intent(Home.this, All_Score.class);
            startActivity(intent);
        });
    }

    // Helper method to play the click sound
    private void playClickSound() {
        if (clickSoundPlayer != null) {
            clickSoundPlayer.seekTo(0);
            clickSoundPlayer.start();
        }
    }

    // Crucial: Release all MediaPlayer resources when the Activity is destroyed
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (backgroundSoundPlayer != null) {
            backgroundSoundPlayer.release();
            backgroundSoundPlayer = null;
        }
        if (clickSoundPlayer != null) {
            clickSoundPlayer.release();
            clickSoundPlayer = null;
        }
    }
}