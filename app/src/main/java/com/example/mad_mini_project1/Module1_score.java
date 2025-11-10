package com.example.mad_mini_project1;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Module1_score extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_module1_score, container, false);

        Button btnScore, btnMain;
        btnScore = view.findViewById(R.id.btnViewScore);
        btnMain = view.findViewById(R.id.btnMain);

        btnMain.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), Learn.class));
        });

        btnScore.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), All_Score.class));
        });
        return view;
        }
    }