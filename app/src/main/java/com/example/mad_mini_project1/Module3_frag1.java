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

public class Module3_frag1 extends Fragment {

    private MediaPlayer popSoundPlayer;
    private Animation scaleDown;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_module3_frag1, container, false);

        Button btnNext = view.findViewById(R.id.btnNext);

        // Initialize animation and sound
        if (getActivity() != null) {
            popSoundPlayer = MediaPlayer.create(getActivity(), R.raw.pop_sound);
            scaleDown = AnimationUtils.loadAnimation(getActivity(), R.anim.scale_down);
        }

        btnNext.setOnClickListener(v -> {
            if (scaleDown != null) v.startAnimation(scaleDown);
            playPopSound();

            if (getActivity() instanceof Module3_Onboarding) {
                ((Module3_Onboarding) getActivity()).goToNextFragment();
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
