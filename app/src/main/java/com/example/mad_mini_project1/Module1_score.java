package com.example.mad_mini_project1;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class Module1_score extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_module1_score, container, false);

        Button btnScore = view.findViewById(R.id.btnViewScore);
        Button btnMain = view.findViewById(R.id.btnMain);
        TextView tvScore = view.findViewById(R.id.tvScore);

        // 1. get score from bundle
        int score = 0;
        if (getArguments() != null) {
            score = getArguments().getInt("score", 0);
        }

        // 2. show it on screen
        if (tvScore != null) {
            tvScore.setText(score + "/3");
        }

        // 3. save to SharedPreferences so All_Score can read it
        if (getActivity() != null) {
            SessionManager session = new SessionManager(getActivity());
            session.saveScoreForModule(1, score);
        }

        // 4. buttons
        btnMain.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), Learn.class));
            requireActivity().overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
        });

        btnScore.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), All_Score.class));
            requireActivity().overridePendingTransition(R.anim.slide_to_right, R.anim.slide_from_left);
        });

        return view;
    }
}
