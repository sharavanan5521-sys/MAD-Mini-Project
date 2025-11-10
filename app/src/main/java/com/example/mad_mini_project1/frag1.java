package com.example.mad_mini_project1;

import android.content.Intent;
import android.graphics.Paint;
import android.media.MediaPlayer; // 1. Import MediaPlayer
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class frag1 extends Fragment {

    // Declare MediaPlayer as a member variable to ensure it's available
    // across the fragment's lifecycle for proper cleanup (optional but recommended)
    private MediaPlayer popSoundPlayer;

    public frag1() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_frag1, container, false);

        // 2. Initialize the MediaPlayer when the view is created
        if (getActivity() != null) {
            popSoundPlayer = MediaPlayer.create(getActivity(), R.raw.pop_sound);
        }

        Button btnContinue = view.findViewById(R.id.btnContinue);
        TextView tvSkip = view.findViewById(R.id.tvSkip);
        FrameLayout flPanel = view.findViewById(R.id.flPanel);

        Animation slideInLeft = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_from_left);
        Animation slideToRight = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_to_right);
        Animation scaleDown = AnimationUtils.loadAnimation(getActivity(), R.anim.scale_down);

        flPanel.startAnimation(slideInLeft);

        tvSkip.setPaintFlags(tvSkip.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        btnContinue.setOnClickListener(v -> {
            // 3. Play the sound effect first
            if (popSoundPlayer != null) {
                popSoundPlayer.seekTo(0);
                popSoundPlayer.start();
            }

            v.setEnabled(false);
            btnContinue.startAnimation(scaleDown);
            flPanel.startAnimation(slideToRight);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (getActivity() instanceof MainActivity) {
                        ((MainActivity) getActivity()).goToNextFragment();
                    }
                }
            }, 1000); // Delay time in milliseconds (1000ms = 1 second)
        });
        tvSkip.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Login.class);
            startActivity(intent);
            if (getActivity() != null) {
                getActivity().finish();
            }
        });

        return view;
    }

    // 4. Important: Release the MediaPlayer resources when the Fragment is destroyed
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (popSoundPlayer != null) {
            popSoundPlayer.release();
            popSoundPlayer = null; // Set to null for safety
        }
    }
}