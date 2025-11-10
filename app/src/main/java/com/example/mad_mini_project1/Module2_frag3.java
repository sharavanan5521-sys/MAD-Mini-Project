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

public class Module2_frag3 extends Fragment {

    private MediaPlayer popSoundPlayer;
    private Animation scaleDown;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_module2_frag3, container, false);

        Button btnNext = view.findViewById(R.id.btnNext);
        Button btnBack = view.findViewById(R.id.btnBack);
        Button btnQuiz = view.findViewById(R.id.btnQuiz);

        if (getActivity() != null) {
            popSoundPlayer = MediaPlayer.create(getActivity(), R.raw.pop_sound);
            scaleDown = AnimationUtils.loadAnimation(getActivity(), R.anim.scale_down);
        }

        // Next (exit module onboarding → back to Learn as per your activity logic)
        btnNext.setOnClickListener(v -> {
            v.startAnimation(scaleDown);
            playPop();
            if (getActivity() instanceof Module2_Onboarding) {
                ((Module2_Onboarding) getActivity()).goToNextFragment();
            }
        });

        // Back
        btnBack.setOnClickListener(v -> {
            v.startAnimation(scaleDown);
            playPop();
            if (getActivity() instanceof Module2_Onboarding) {
                ((Module2_Onboarding) getActivity()).goToPreviousFragment();
            }
        });

        // Go to Module 2 quiz
        btnQuiz.setOnClickListener(v -> {
            v.startAnimation(scaleDown);
            playPop();
            Intent intent = new Intent(getActivity(), Module2_Quiz_Onboarding.class);
            startActivity(intent);
            requireActivity().overridePendingTransition(R.anim.frag_slide_in, R.anim.frag_slide_out);
        });

        return view;
    }

    private void playPop() {
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
