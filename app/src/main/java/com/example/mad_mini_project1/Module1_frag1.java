package com.example.mad_mini_project1;

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

public class Module1_frag1 extends Fragment {

    private MediaPlayer popSoundPlayer;
    private Animation scaleDown;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_module1_frag1, container, false);

        // init sound
        if (getActivity() != null) {
            popSoundPlayer = MediaPlayer.create(getActivity(), R.raw.pop_sound);
            // load same animation style as your activities
            scaleDown = AnimationUtils.loadAnimation(getActivity(), R.anim.scale_down);
        }

        Button btnNext = view.findViewById(R.id.btnNext);

        btnNext.setOnClickListener(v -> {
            // play animation (if loaded)
            if (scaleDown != null) {
                v.startAnimation(scaleDown);
            }

            // play sound
            playPopSound();

            // tell activity to go to next fragment
            if (getActivity() instanceof Module1_Onboarding) {
                ((Module1_Onboarding) getActivity()).goToNextFragment();
            }
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
