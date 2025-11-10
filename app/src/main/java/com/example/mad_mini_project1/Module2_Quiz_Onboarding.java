package com.example.mad_mini_project1;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class Module2_Quiz_Onboarding extends AppCompatActivity {

    public int currentFragment = 1;
    private int currentScore = 0;

    private CountDownTimer quizTimer;
    private static final long QUIZ_DURATION_MS = 60000; // 1 minute
    private long timeLeftMs = QUIZ_DURATION_MS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_module2_quiz_onboarding);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // first quiz screen
        loadFragment(new Module2_Quiz1(), false);

        // start timer as soon as quiz starts
        startTimer();
    }

    public void submitAnswer(boolean isCorrect) {
        if (isCorrect) {
            currentScore += 1;
        }
        goToNextFragment();
    }

    public void goToNextFragment() {
        Fragment nextFragment;

        if (currentFragment == 1) {
            nextFragment = new Module2_Quiz2();
            currentFragment = 2;
        } else if (currentFragment == 2) {
            nextFragment = new Module2_Quiz3();
            currentFragment = 3;
        } else if (currentFragment == 3) {
            goToScoreScreen();
            return;
        } else {
            goBackToLearn();
            return;
        }

        loadFragment(nextFragment, true);
    }

    private void goToScoreScreen() {
        cancelTimer();
        Module2_score scoreFragment = new Module2_score();
        Bundle b = new Bundle();
        b.putInt("score", currentScore);
        scoreFragment.setArguments(b);
        currentFragment = 4;
        loadFragment(scoreFragment, true);
    }

    private void goBackToLearn() {
        cancelTimer();
        startActivity(new Intent(this, Learn.class));
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
        finish();
    }

    private void loadFragment(Fragment fragment, boolean animate) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (animate) {
            transaction.setCustomAnimations(
                    R.anim.slide_from_left,
                    R.anim.slide_to_right,
                    R.anim.slide_to_right,
                    R.anim.slide_from_left
            );
        }

        transaction.replace(R.id.frame_container, fragment);
        transaction.commit();
    }

    private void loadFragment(Fragment fragment) {
        loadFragment(fragment, false);
    }

    private void startTimer() {
        cancelTimer();

        quizTimer = new CountDownTimer(timeLeftMs, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftMs = millisUntilFinished;
                String timeStr = formatTime(millisUntilFinished);

                Fragment f = getSupportFragmentManager().findFragmentById(R.id.frame_container);
                if (f != null && f instanceof QuizTimerReceiver) {
                    ((QuizTimerReceiver) f).updateTimer(timeStr);
                }
            }

            @Override
            public void onFinish() {
                timeLeftMs = 0;

                Fragment f = getSupportFragmentManager().findFragmentById(R.id.frame_container);
                if (f != null && f instanceof QuizTimerReceiver) {
                    ((QuizTimerReceiver) f).updateTimer("00:00");
                }

                // time’s up, show score
                goToScoreScreen();
            }
        }.start();
    }

    private void cancelTimer() {
        if (quizTimer != null) {
            quizTimer.cancel();
            quizTimer = null;
        }
    }

    private String formatTime(long millis) {
        int seconds = (int) (millis / 1000);
        int minutes = seconds / 60;
        int sec = seconds % 60;
        return String.format("%02d:%02d", minutes, sec);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cancelTimer();
    }
}
