package com.example.mad_mini_project1;

import android.animation.ValueAnimator;
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
// Note: android.view.ViewGroup is imported twice in the original, I'll remove one.

public class frag3 extends Fragment {

    // Declare MediaPlayer as a member variable
    private MediaPlayer popSoundPlayer;

    // Define the start and end targets for this segment of the animation
    private static final int START_WIDTH_DP = 63;
    private static final int END_WIDTH_DP = 125;

    public frag3() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_frag3, container, false);

        // 2. Initialize the MediaPlayer when the view is created
        if (getActivity() != null) {
            popSoundPlayer = MediaPlayer.create(getActivity(), R.raw.pop_sound);
        }

        Button btnContinue = view.findViewById(R.id.btnContinue3);
        FrameLayout flPanel3 = view.findViewById(R.id.flPanel3);

        Animation slideInLeft = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_from_left);
        Animation slideToRight = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_to_right);
        Animation scaleDown = AnimationUtils.loadAnimation(getActivity(), R.anim.scale_down);

        flPanel3.startAnimation(slideInLeft);

        // Assuming the ImageView ID follows the pattern
        ImageView ivPageLoading = view.findViewById(R.id.ivPageLoading3);
        // Find the new ImageView for the tracker
        ImageView ivEarth = view.findViewById(R.id.ivEarth3);

        btnContinue.setOnClickListener(v -> {
            // 3. Play the sound effect first
            if (popSoundPlayer != null) {
                popSoundPlayer.seekTo(0);
                popSoundPlayer.start();
            }

            v.setEnabled(false);
            btnContinue.startAnimation(scaleDown);
            flPanel3.startAnimation(slideToRight);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (getActivity() instanceof MainActivity) {
                        ((MainActivity) getActivity()).goToNextFragment();
                    }
                }
            }, 1000); // Delay time in milliseconds (1000ms = 1 second)
        });

        // --- Animation Logic for Frag 3 (Second Half) ---
        view.post(new Runnable() {
            @Override
            public void run() {
                // Animate the width and position from 63dp (halfway) to 125dp (full width)
                animateProgress(ivPageLoading, ivEarth, START_WIDTH_DP, END_WIDTH_DP);
            }
        });
        // --- Animation Logic Ends Here ---

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

    /**
     * Helper method to convert density-independent pixels (dp) to pixels (px).
     * LayoutParams require pixel values.
     */
    private int dpToPx(int dp) {
        if (getContext() == null) return 0;
        return Math.round(dp * getContext().getResources().getDisplayMetrics().density);
    }

    /**
     * Animates the ImageView's true layout width and a tracker's position simultaneously.
     */
    private void animateProgress(final ImageView progressBar, final ImageView tracker, final int startWidthDp, final int endWidthDp) {
        // Convert DP values to pixels
        final int startWidthPx = dpToPx(startWidthDp);
        final int endWidthPx = dpToPx(endWidthDp);

        // Get the actual width of the tracker image (ivEarth) in pixels.
        final int trackerWidth = tracker.getWidth();

        // Ensure the progress bar starts at the correct position (63dp)
        ViewGroup.LayoutParams layoutParams = progressBar.getLayoutParams();
        layoutParams.width = startWidthPx;
        progressBar.setLayoutParams(layoutParams);

        // Ensure the tracker starts at the correct position (63dp).
        // To align the right edge of the tracker with the starting width (startWidthPx),
        // we subtract the tracker's full width.
        tracker.setTranslationX(startWidthPx - trackerWidth);

        // Create the ValueAnimator
        ValueAnimator widthAnimator = ValueAnimator.ofInt(startWidthPx, endWidthPx);
        widthAnimator.setDuration(1000); // 1 second duration

        // Add the update listener to modify LayoutParams and TranslationX
        widthAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                // Get the animated integer value
                int animatedValue = (int) animator.getAnimatedValue();

                // 1. Update the progress bar width (Layout change)
                ViewGroup.LayoutParams layoutParams = progressBar.getLayoutParams();
                layoutParams.width = animatedValue;
                progressBar.setLayoutParams(layoutParams);

                // 2. Update the tracker position (Visual translation)
                // We subtract the full tracker width from the animated width to align their right edges.
                tracker.setTranslationX(animatedValue - trackerWidth);
            }
        });

        widthAnimator.start();
    }
}