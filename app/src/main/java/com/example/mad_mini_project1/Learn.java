package com.example.mad_mini_project1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Learn extends AppCompatActivity {

    FrameLayout Module1, Module2, Module3;
    Animation scaleUp, scaleDown;

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

        scaleUp = AnimationUtils.loadAnimation(this, R.anim.scale_up);
        scaleDown = AnimationUtils.loadAnimation(this, R.anim.scale_down);

        Module1 = findViewById(R.id.flModule1);
        Module2 = findViewById(R.id.flModule2);
        Module3 = findViewById(R.id.flModule3);

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

        Module1.setOnTouchListener(touchListener);
        Module2.setOnTouchListener(touchListener);
        Module3.setOnTouchListener(touchListener);

        Module1.setOnClickListener(v -> {
            Intent intent = new Intent(Learn.this, Module1_Onboarding.class);
            startActivity(intent);
        });

        Module2.setOnClickListener(v -> {
            Intent intent = new Intent(Learn.this, Module2_Onboarding.class);
            startActivity(intent);
        });

        Module3.setOnClickListener(v -> {
            Intent intent = new Intent(Learn.this, Module3_Onboarding.class);
            startActivity(intent);
        });

    }
}
