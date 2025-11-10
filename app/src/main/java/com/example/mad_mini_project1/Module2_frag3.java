package com.example.mad_mini_project1;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Module2_frag3 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_module2_frag3, container, false);

        Button btnNext, btnBack, btnQuiz;

        btnNext = view.findViewById(R.id.btnNext);
        btnBack = view.findViewById(R.id.btnBack);
        btnQuiz = view.findViewById(R.id.btnQuiz);

        btnNext.setOnClickListener(v -> {
            ((Module2_Onboarding) getActivity()).goToNextFragment();
        });

        btnBack.setOnClickListener(v -> {
            ((Module2_Onboarding) getActivity()).goToPreviousFragment();
        });

        btnQuiz.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), Module2_Quiz_Onboarding.class));
        });
         return view;
    }
}