package com.example.mad_mini_project1;

import android.annotation.SuppressLint;
import android.content.Intent;
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
            Intent intent = new Intent(Home.this, Learn.class);
            startActivity(intent);
        });

        flScore.setOnClickListener(v -> {
            Intent intent = new Intent(Home.this, All_Score.class);
            startActivity(intent);
        });
    }
}