package com.example.mad_mini_project1;

import android.content.Intent;
import android.graphics.Paint;
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

    public frag1() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_frag1, container, false);

        Button btnContinue = view.findViewById(R.id.btnContinue);
        TextView tvSkip = view.findViewById(R.id.tvSkip);
        FrameLayout flPanel = view.findViewById(R.id.flPanel);

        Animation slideInLeft = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_from_left);
        Animation slideToRight = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_to_right);
        Animation scaleDown = AnimationUtils.loadAnimation(getActivity(), R.anim.scale_down);

        flPanel.startAnimation(slideInLeft);

        tvSkip.setPaintFlags(tvSkip.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        btnContinue.setOnClickListener(v -> {
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
            }, 1000); // Delay time in milliseconds (500ms = 0.5 seconds)
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
}