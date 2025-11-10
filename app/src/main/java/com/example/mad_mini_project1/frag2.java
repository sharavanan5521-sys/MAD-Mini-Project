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
import android.view.ViewGroup;

public class frag2 extends Fragment {

    // Declare MediaPlayer as a member variable
    private MediaPlayer popSoundPlayer;

    // Define the start and end targets for this segment of the animation
    private static final int START_WIDTH_DP = 1;
    private static final int END_WIDTH_DP = 63;

    public frag2() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_frag2, container, false);

        // 2. Initialize the MediaPlayer when the view is created
        if (getActivity() != null) {
            popSoundPlayer = MediaPlayer.create(getActivity(), R.raw.pop_sound);
        }

        Button btnContinue = view.findViewById(R.id.btnContinue2);
        TextView tvSkip = view.findViewById(R.id.tvSkip2);
        ImageView ivPageLoading = view.findViewById(R.id.ivPageLoading2);
        ImageView ivEarth = view.findViewById(R.id.ivEarth2);
        FrameLayout flPanel2 = view.findViewById(R.id.flPanel2);

        Animation slideInLeft = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_from_left);
        Animation slideToRight = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_to_right);
        Animation scaleDown = AnimationUtils.loadAnimation(getActivity(), R.anim.scale_down);

        tvSkip.setPaintFlags(tvSkip.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        flPanel2.startAnimation(slideInLeft);


        btnContinue.setOnClickListener(v -> {
            // 3. Play the sound effect first
            if (popSoundPlayer != null) {
                popSoundPlayer.seekTo(0);
                popSoundPlayer.start();
            }

            v.setEnabled(false);
            btnContinue.startAnimation(scaleDown);
            flPanel2.startAnimation(slideToRight);
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

        // --- Animation Logic Updated for Half Width and Tracker ---
        view.post(new Runnable() {
            @Override
            public void run() {
                // Animate the width (ivPageLoading) and position (ivEarth) from start to 63dp
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
     * The tracker (ivEarth) starts flush (TranslationX=0) and ends centered on the bar's right edge.
     */
    private void animateProgress(final ImageView progressBar, final ImageView tracker, final int startWidthDp, final int endWidthDp) {

        final int startWidthPx = dpToPx(startWidthDp);
        final int endWidthPx = dpToPx(endWidthDp);

        final int trackerWidth = tracker.getWidth();

        // --- PROPORTIONAL TRACKER MOVEMENT CALCULATION ---
        // Range for the width animator (denominator for progress calculation)
        final float widthRange = (float)(endWidthPx - startWidthPx);

        // Target TranslationX: Center of tracker aligns with the end of the bar
        final float endTranslationPx = endWidthPx - (trackerWidth / 2f);

        // 1. Ensure the progress bar starts at the correct position
        ViewGroup.LayoutParams layoutParams = progressBar.getLayoutParams();
        layoutParams.width = startWidthPx;
        progressBar.setLayoutParams(layoutParams);

        // 2. Set initial state: "starts to starts" alignment means TranslationX is 0.
        // This prevents the visual jump/teleport.
        tracker.setTranslationX(0);

        // Create the ValueAnimator for the BAR WIDTH
        ValueAnimator widthAnimator = ValueAnimator.ofInt(startWidthPx, endWidthPx);
        widthAnimator.setDuration(1000); // 1 second duration

        // Add the update listener to modify LayoutParams and TranslationX
        widthAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                int animatedValue = (int) animator.getAnimatedValue();

                // A. Update the progress bar width (Layout change)
                ViewGroup.LayoutParams layoutParams = progressBar.getLayoutParams();
                layoutParams.width = animatedValue;
                progressBar.setLayoutParams(layoutParams);

                // B. Update the tracker position (Visual translation)
                if (widthRange > 0) {
                    // 1. Calculate animation progress (0.0 to 1.0)
                    float progress = (animatedValue - startWidthPx) / widthRange;

                    // 2. Calculate current TranslationX: Move proportionally from 0 to endTranslationPx
                    float currentTranslation = progress * endTranslationPx;
                    tracker.setTranslationX(currentTranslation);
                } else {
                    tracker.setTranslationX(endTranslationPx);
                }
            }
        });

        widthAnimator.start();
    }
}