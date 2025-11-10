package com.example.mad_mini_project1;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Module2_score extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_module2_score, container, false);

        Button btnScore = view.findViewById(R.id.btnViewScore2);
        Button btnMain = view.findViewById(R.id.btnMain2);
        TextView tvScore = view.findViewById(R.id.tvScore2);  // make sure this TextView exists in your XML

        // 1. Retrieve score from bundle
        int score = 0;
        if (getArguments() != null) {
            score = getArguments().getInt("score", 0);
        }

        // 2. Display score
        tvScore.setText(score + "/3");

        // 3. Save score using SessionManager (for All_Score page)
        if (getActivity() != null) {
            SessionManager session = new SessionManager(getActivity());
            session.saveScoreForModule(2, score);
        }

        // 4. Buttons
        btnMain.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), Learn.class));
            requireActivity().overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
        });

        btnScore.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), All_Score.class));
            requireActivity().overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
        });

        return view;
    }
}
