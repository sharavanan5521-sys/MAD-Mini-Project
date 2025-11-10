package com.example.mad_mini_project1;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class Module1_Quiz1 extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_module1__quiz1, container, false);

        ImageView ivAns1, ivAns2, ivAns3;
        ivAns1 = view.findViewById(R.id.ivAns1);

        ivAns1.setOnClickListener(v -> {
            ((Module1_Quiz_Onboarding) getActivity()).goToNextFragment();
        });
        return view;
    };
}