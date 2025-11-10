package com.example.mad_mini_project1;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Module1_frag3 extends Fragment {

    private MediaPlayer popSoundPlayer;
    private Animation scaleDown;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_module1_frag3, container, false);

        // Initialize pop sound + animation
        if (getActivity() != null) {
            popSoundPlayer = MediaPlayer.create(getActivity(), R.raw.pop_sound);
            scaleDown = AnimationUtils.loadAnimation(getActivity(), R.anim.scale_down);
        }

        Button btnDone = view.findViewById(R.id.btnNext);
        Button btnBack = view.findViewById(R.id.btnBack);
        Button btnQuiz = view.findViewById(R.id.btnQuiz);

        // Done (go back to Learn or next activity)
        btnDone.setOnClickListener(v -> {
            v.startAnimation(scaleDown);
            playPopSound();

            if (getActivity() instanceof Module1_Onboarding) {
                ((Module1_Onboarding) getActivity()).goToNextFragment();
            }
        });

        // Back
        btnBack.setOnClickListener(v -> {
            v.startAnimation(scaleDown);
            playPopSound();

            if (getActivity() instanceof Module1_Onboarding) {
                ((Module1_Onboarding) getActivity()).goToPreviousFragment();
            }
        });

        // Go to quiz onboarding
        btnQuiz.setOnClickListener(v -> {
            v.startAnimation(scaleDown);
            playPopSound();

            Intent intent = new Intent(getActivity(), Module1_Quiz_Onboarding.class);
            startActivity(intent);
            requireActivity().overridePendingTransition(R.anim.slide_to_right, R.anim.slide_from_left);
        });

        return view;
    }

    private void playPopSound() {
        if (popSoundPlayer != null) {
            popSoundPlayer.seekTo(0);
            popSoundPlayer.start();
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
