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
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class frag2 extends Fragment {

    private MediaPlayer popSoundPlayer;
    private Handler handler;
    private Runnable goNextRunnable;

    // progress animation targets
    private static final int START_WIDTH_DP = 1;
    private static final int END_WIDTH_DP = 63;

    public frag2() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_frag2, container, false);

        Button btnContinue = view.findViewById(R.id.btnContinue2);
        TextView tvSkip = view.findViewById(R.id.tvSkip2);
        ImageView ivPageLoading = view.findViewById(R.id.ivPageLoading2);
        ImageView ivEarth = view.findViewById(R.id.ivEarth2);
        FrameLayout flPanel2 = view.findViewById(R.id.flPanel2);

        handler = new Handler(Looper.getMainLooper());

        // sound
        if (getActivity() != null) {
            popSoundPlayer = MediaPlayer.create(getActivity(), R.raw.pop_sound);
        }

        // animations (safe)
        Animation slideInLeft = null;
        Animation slideToRight = null;
        Animation scaleDown = null;
        if (getContext() != null) {
            slideInLeft = AnimationUtils.loadAnimation(getContext(), R.anim.slide_from_left);
            slideToRight = AnimationUtils.loadAnimation(getContext(), R.anim.slide_to_right);
            scaleDown = AnimationUtils.loadAnimation(getContext(), R.anim.scale_down);
        }

        if (slideInLeft != null) {
            flPanel2.startAnimation(slideInLeft);
        }

        // underline skip
        tvSkip.setPaintFlags(tvSkip.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        Animation finalSlideToRight = slideToRight;
        Animation finalScaleDown = scaleDown;

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
                flPanel2.startAnimation(finalSlideToRight);
            }

            // delayed navigation
            goNextRunnable = () -> {
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).goToNextFragment();
                }
            };
            handler.postDelayed(goNextRunnable, 1000);
        });

        tvSkip.setOnClickListener(v -> {
            if (getActivity() != null) {
                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        // start your progress animation after layout is ready
        view.post(() -> animateProgress(ivPageLoading, ivEarth, START_WIDTH_DP, END_WIDTH_DP));

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // stop pending navigation
        if (handler != null && goNextRunnable != null) {
            handler.removeCallbacks(goNextRunnable);
        }

        // release media
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

        final float widthRange = (float) (endWidthPx - startWidthPx);
        final float endTranslationPx = endWidthPx - (trackerWidth / 2f);

        // set initial width
        ViewGroup.LayoutParams layoutParams = progressBar.getLayoutParams();
        layoutParams.width = startWidthPx;
        progressBar.setLayoutParams(layoutParams);

        // tracker starts at 0
        tracker.setTranslationX(0);

        ValueAnimator widthAnimator = ValueAnimator.ofInt(startWidthPx, endWidthPx);
        widthAnimator.setDuration(1000);

        widthAnimator.addUpdateListener(animator -> {
            int animatedValue = (int) animator.getAnimatedValue();

            // update bar width
            ViewGroup.LayoutParams lp = progressBar.getLayoutParams();
            lp.width = animatedValue;
            progressBar.setLayoutParams(lp);

            // update tracker position
            if (widthRange > 0) {
                float progress = (animatedValue - startWidthPx) / widthRange;
                float currentTranslation = progress * endTranslationPx;
                tracker.setTranslationX(currentTranslation);
            } else {
                tracker.setTranslationX(endTranslationPx);
            }
        });

        widthAnimator.start();
    }
}
