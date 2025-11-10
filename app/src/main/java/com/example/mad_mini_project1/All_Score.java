package com.example.mad_mini_project1;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class All_Score extends AppCompatActivity {

    private Button btnBack;
    private MediaPlayer popSoundPlayer;   // make it a field so we can release it
    private TextView tvMod1, tvMod2, tvMod3, tvTotal;

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

        // init views
        btnBack = findViewById(R.id.btnBack);
        tvMod1 = findViewById(R.id.tvMod1Score);
        tvMod2 = findViewById(R.id.tvMod2Score);
        tvMod3 = findViewById(R.id.tvMod3Score);
        tvTotal = findViewById(R.id.tvTotalScore);

        // init sound once
        popSoundPlayer = MediaPlayer.create(this, R.raw.pop_sound);

        // get scores from SessionManager
        SessionManager session = new SessionManager(this);
        int mod1 = session.getScoreForModule(1);
        int mod2 = session.getScoreForModule(2);
        int mod3 = session.getScoreForModule(3);

        int total = mod1 + mod2 + mod3;

        // display them
        tvMod1.setText(mod1 + "/3");
        tvMod2.setText(mod2 + "/3");
        tvMod3.setText(mod3 + "/3");
        tvTotal.setText(total + "/9");

        btnBack.setOnClickListener(v -> {
            if (popSoundPlayer != null) {
                popSoundPlayer.seekTo(0);
                popSoundPlayer.start();
            }
            startActivity(new Intent(All_Score.this, Home.class));
            overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
            finish();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (popSoundPlayer != null) {
            popSoundPlayer.release();
            popSoundPlayer = null;
        }
    }
}
