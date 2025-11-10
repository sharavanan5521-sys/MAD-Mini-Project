package com.example.mad_mini_project1;

import android.content.Intent;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

public class frag1 extends Fragment {

    private MediaPlayer popSoundPlayer;
    private Handler handler;   // to clear delayed runnable
    private Runnable goNextRunnable;

    public frag1() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_frag1, container, false);

        Button btnContinue = view.findViewById(R.id.btnContinue);
        TextView tvSkip = view.findViewById(R.id.tvSkip);
        FrameLayout flPanel = view.findViewById(R.id.flPanel);

        handler = new Handler(Looper.getMainLooper());

        // init sound
        if (getActivity() != null) {
            popSoundPlayer = MediaPlayer.create(getActivity(), R.raw.pop_sound);
        }

        // init animations safely
        Animation slideInLeft = null;
        Animation slideToRight = null;
        Animation scaleDown = null;
        if (getContext() != null) {
            slideInLeft = AnimationUtils.loadAnimation(getContext(), R.anim.slide_from_left);
            slideToRight = AnimationUtils.loadAnimation(getContext(), R.anim.slide_to_right);
            scaleDown = AnimationUtils.loadAnimation(getContext(), R.anim.scale_down);
        }

        // start panel animation
        if (slideInLeft != null) {
            flPanel.startAnimation(slideInLeft);
        }

        // underline skip
        tvSkip.setPaintFlags(tvSkip.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        Animation finalScaleDown = scaleDown;
        Animation finalSlideToRight = slideToRight;

        btnContinue.setOnClickListener(v -> {
            // play sound
            if (popSoundPlayer != null) {
                popSoundPlayer.seekTo(0);
                popSoundPlayer.start();
            }

            // prevent double click
            v.setEnabled(false);

            if (finalScaleDown != null) {
                v.startAnimation(finalScaleDown);
            }
            if (finalSlideToRight != null) {
                flPanel.startAnimation(finalSlideToRight);
            }

            // prepare runnable
            goNextRunnable = () -> {
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).goToNextFragment();
                }
            };

            // 1 second delay
            handler.postDelayed(goNextRunnable, 1000);
        });

        tvSkip.setOnClickListener(v -> {
            if (getActivity() != null) {
                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // stop pending navigation
        if (handler != null && goNextRunnable != null) {
            handler.removeCallbacks(goNextRunnable);
        }

        // release sound
        if (popSoundPlayer != null) {
            popSoundPlayer.release();
            popSoundPlayer = null;
        }
    }
}
