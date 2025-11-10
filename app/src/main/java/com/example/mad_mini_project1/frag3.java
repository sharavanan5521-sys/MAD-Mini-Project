package com.example.mad_mini_project1;

import android.animation.ValueAnimator;
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
import android.view.ViewGroup;   // needed for LayoutParams
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class frag3 extends Fragment {

    private MediaPlayer popSoundPlayer;
    private Handler handler;
    private Runnable goNextRunnable;

    // this fragment animates from half bar (63dp) to full (125dp)
    private static final int START_WIDTH_DP = 63;
    private static final int END_WIDTH_DP = 125;

    public frag3() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_frag3, container, false);

        Button btnContinue = view.findViewById(R.id.btnContinue3);
        FrameLayout flPanel3 = view.findViewById(R.id.flPanel3);
        ImageView ivPageLoading = view.findViewById(R.id.ivPageLoading3);
        ImageView ivEarth = view.findViewById(R.id.ivEarth3);

        handler = new Handler(Looper.getMainLooper());

        // sound
        if (getActivity() != null) {
            popSoundPlayer = MediaPlayer.create(getActivity(), R.raw.pop_sound);
        }

        // animations, but safe
        Animation slideInLeft = null;
        Animation slideToRight = null;
        Animation scaleDown = null;
        if (getContext() != null) {
            slideInLeft = AnimationUtils.loadAnimation(getContext(), R.anim.slide_from_left);
            slideToRight = AnimationUtils.loadAnimation(getContext(), R.anim.slide_to_right);
            scaleDown = AnimationUtils.loadAnimation(getContext(), R.anim.scale_down);
        }

        if (slideInLeft != null) {
            flPanel3.startAnimation(slideInLeft);
        }

        final Animation finalScaleDown = scaleDown;
        final Animation finalSlideToRight = slideToRight;

        btnContinue.setOnClickListener(v -> {
            // play sound
            if (popSoundPlayer != null) {
                popSoundPlayer.seekTo(0);
                popSoundPlayer.start();
            }

            v.setEnabled(false);

            if (finalScaleDown != null) {
                v.startAnimation(finalScaleDown);
            }
            if (finalSlideToRight != null) {
                flPanel3.startAnimation(finalSlideToRight);
            }

            // delayed nav to next onboarding (which is your Login)
            goNextRunnable = () -> {
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).goToNextFragment();
                } else if (getActivity() != null) {
                    // fallback: go to Login if somehow not in MainActivity
                    startActivity(new Intent(getActivity(), Login.class));
                    getActivity().finish();
                }
            };
            handler.postDelayed(goNextRunnable, 1000);
        });

        // run the progress animation after layout is ready
        view.post(() -> animateProgress(ivPageLoading, ivEarth, START_WIDTH_DP, END_WIDTH_DP));

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // stop pending navigation if user leaves early
        if (handler != null && goNextRunnable != null) {
            handler.removeCallbacks(goNextRunnable);
        }

        if (popSoundPlayer != null) {
            popSoundPlayer.release();
            popSoundPlayer = null;
        }
    }

    private int dpToPx(int dp) {
        if (getContext() == null) return 0;
        return Math.round(dp * getContext().getResources().getDisplayMetrics().density);
    }

    private void animateProgress(final ImageView progressBar,
                                 final ImageView tracker,
                                 final int startWidthDp,
                                 final int endWidthDp) {

        if (getContext() == null) return;

        final int startWidthPx = dpToPx(startWidthDp);
        final int endWidthPx = dpToPx(endWidthDp);
        final int trackerWidth = tracker.getWidth();

        // set initial bar width
        ViewGroup.LayoutParams lp = progressBar.getLayoutParams();
        lp.width = startWidthPx;
        progressBar.setLayoutParams(lp);

        // tracker should start aligned to current width (right edge)
        tracker.setTranslationX(startWidthPx - trackerWidth);

        ValueAnimator widthAnimator = ValueAnimator.ofInt(startWidthPx, endWidthPx);
        widthAnimator.setDuration(1000);

        widthAnimator.addUpdateListener(animator -> {
            int animatedValue = (int) animator.getAnimatedValue();

            // resize bar
            ViewGroup.LayoutParams p = progressBar.getLayoutParams();
            p.width = animatedValue;
            progressBar.setLayoutParams(p);

            // move tracker along with bar
            tracker.setTranslationX(animatedValue - trackerWidth);
        });

        widthAnimator.start();
    }
}
