package com.example.mad_mini_project1;

import android.media.MediaPlayer; // 1. Import MediaPlayer
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Module1_frag1 extends Fragment {

    private MediaPlayer popSoundPlayer; // 2. Declare MediaPlayer

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_module1_frag1, container, false);

        // 3. Initialize the 'pop' sound player
        if (getActivity() != null) {
            popSoundPlayer = MediaPlayer.create(getActivity(), R.raw.pop_sound);
        }

        Button btnNext = view.findViewById(R.id.btnNext);

        btnNext.setOnClickListener(v -> {
            // 4. Play sound
            playPopSound();

            // Navigate
            if (getActivity() instanceof Module1_Onboarding) {
                ((Module1_Onboarding) getActivity()).goToNextFragment();
            }
        });

        return view;
    }

    /**
     * Helper method to play the pop sound.
     */
    private void playPopSound() {
        if (popSoundPlayer != null) {
            popSoundPlayer.seekTo(0);
            popSoundPlayer.start();
        }
    }

    // 5. Crucial: Release the MediaPlayer resources when the Fragment is destroyed
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (popSoundPlayer != null) {
            popSoundPlayer.release();
            popSoundPlayer = null;
        }
    }
}