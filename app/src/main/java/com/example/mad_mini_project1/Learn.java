package com.example.mad_mini_project1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Learn extends AppCompatActivity {

    private FrameLayout module1, module2, module3;
    private Animation scaleUp, scaleDown;
    private MediaPlayer bloobSoundPlayer;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_learn);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // sound
        bloobSoundPlayer = MediaPlayer.create(this, R.raw.bloob_sound);

        // animations
        scaleUp = AnimationUtils.loadAnimation(this, R.anim.scale_up);
        scaleDown = AnimationUtils.loadAnimation(this, R.anim.scale_down);

        // views
        module1 = findViewById(R.id.flModule1);
        module2 = findViewById(R.id.flModule2);
        module3 = findViewById(R.id.flModule3);

        // shared touch listener
        View.OnTouchListener touchListener = (v, event) -> {
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
        };

        module1.setOnTouchListener(touchListener);
        module2.setOnTouchListener(touchListener);
        module3.setOnTouchListener(touchListener);

        module1.setOnClickListener(v -> {
            playBloobSound();
            startActivity(new Intent(Learn.this, Module1_Onboarding.class));
            overridePendingTransition(R.anim.slide_to_right, R.anim.slide_from_left);
        });

        module2.setOnClickListener(v -> {
            playBloobSound();
            startActivity(new Intent(Learn.this, Module2_Onboarding.class));
            overridePendingTransition(R.anim.slide_to_right, R.anim.slide_from_left);
        });

        module3.setOnClickListener(v -> {
            playBloobSound();
            startActivity(new Intent(Learn.this, Module3_Onboarding.class));
            overridePendingTransition(R.anim.slide_to_right, R.anim.slide_from_left);
        });
    }

    private void playBloobSound() {
        if (bloobSoundPlayer != null) {
            bloobSoundPlayer.seekTo(0);
            bloobSoundPlayer.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bloobSoundPlayer != null) {
            bloobSoundPlayer.release();
            bloobSoundPlayer = null;
        }
    }
}
