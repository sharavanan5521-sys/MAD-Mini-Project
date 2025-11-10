package com.example.mad_mini_project1;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Module2_Quiz2 extends Fragment implements QuizTimerReceiver {

    private MediaPlayer popSoundPlayer;
    private Animation scaleDown;
    private TextView tvTime;   // show timer here

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_module2__quiz2, container, false);

        tvTime = view.findViewById(R.id.tvTime5);
        // init sound + animation
        if (getActivity() != null) {
            popSoundPlayer = MediaPlayer.create(getActivity(), R.raw.pop_sound);
            scaleDown = AnimationUtils.loadAnimation(getActivity(), R.anim.scale_down);
        }

        ImageView ivAns1 = view.findViewById(R.id.ivAns1);
        ImageView ivAns2 = view.findViewById(R.id.ivAns2);
        ImageView ivAns3 = view.findViewById(R.id.ivAns3);

        // correct answer = ivAns2
        ivAns1.setOnClickListener(v -> handleAnswer(v, false));
        ivAns2.setOnClickListener(v -> handleAnswer(v, true));
        ivAns3.setOnClickListener(v -> handleAnswer(v,false ));

        return view;
    }

    private void handleAnswer(View v, boolean isCorrect) {
        if (scaleDown != null) v.startAnimation(scaleDown);
        playPop();

        if (getActivity() instanceof Module2_Quiz_Onboarding) {
            ((Module2_Quiz_Onboarding) getActivity()).submitAnswer(isCorrect);
        }
    }

    private void playPop() {
        if (popSoundPlayer != null) {
            popSoundPlayer.seekTo(0);
            popSoundPlayer.start();
        }
    }

    // called every second from the activity
    @Override
    public void updateTimer(String timeText) {
        if (tvTime != null) {
            tvTime.setText(timeText);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (popSoundPlayer != null) {
            popSoundPlayer.release();
            popSoundPlayer = null;
        }
    }
}
