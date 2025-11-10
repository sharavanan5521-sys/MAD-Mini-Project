package com.example.mad_mini_project1;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class Module1_Onboarding extends AppCompatActivity {

    public int currentFragment = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_module1_onboarding);

        // first screen
        loadFragment(new Module1_frag1(), false);
    }

    public void goToNextFragment() {
        Fragment nextFragment;

        if (currentFragment == 1) {
            nextFragment = new Module1_frag2();
            currentFragment = 2;
        } else if (currentFragment == 2) {
            nextFragment = new Module1_frag3();
            currentFragment = 3;
        } else {
            // after last fragment go back to Learn (or to quiz if you change later)
            startActivity(new Intent(this, Learn.class));
            overridePendingTransition(R.anim.slide_to_right, R.anim.slide_from_left);
            finish();
            return;
        }

        loadFragment(nextFragment, true);   // true = forward animation
    }

    public void goToPreviousFragment() {
        Fragment previousFragment;

        if (currentFragment == 2) {
            previousFragment = new Module1_frag1();
            currentFragment = 1;
        } else if (currentFragment == 3) {
            previousFragment = new Module1_frag2();
            currentFragment = 2;
        } else {
            // if user is at first fragment and goes back, send to Learn
            startActivity(new Intent(this, Learn.class));
            overridePendingTransition(R.anim.slide_to_right, R.anim.slide_from_left);
            finish();
            return;
        }

        loadFragment(previousFragment, false);  // false = backward animation
    }

    private void loadFragment(Fragment fragment, boolean isForward) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // add custom animations for fragment change
        if (isForward) {
            transaction.setCustomAnimations(
                    R.anim.frag_slide_in,   // enter
                    R.anim.frag_slide_out,   // exit
                    R.anim.frag_slide_out,    // pop enter
                    R.anim.frag_slide_in   // pop exit
            );
        }else {
            transaction.setCustomAnimations(
                    R.anim.frag_slide_out_reverse,   // enter
                    R.anim.frag_slide_in_reverse,   // exit
                    R.anim.frag_slide_in_reverse,    // pop enter
                    R.anim.frag_slide_out_reverse   // pop exit
            );
        }

        transaction.replace(R.id.frame_container, fragment);
        transaction.commit();
    }
}
