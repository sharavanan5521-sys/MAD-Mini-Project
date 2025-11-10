package com.example.mad_mini_project1;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class Module2_Onboarding extends AppCompatActivity {

    private int currentFragment = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_module2_onboarding);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // load first fragment without animation
        loadFragment(new Module2_frag1(), false);
    }

    public void goToNextFragment() {
        Fragment nextFragment;

        if (currentFragment == 1) {
            nextFragment = new Module2_frag2();
            currentFragment = 2;
        } else if (currentFragment == 2) {
            nextFragment = new Module2_frag3();
            currentFragment = 3;
        } else {
            startActivity(new Intent(this, Learn.class));
            overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
            finish();
            return;
        }

        loadFragment(nextFragment, true);
    }

    public void goToPreviousFragment() {
        Fragment previousFragment;

        if (currentFragment == 2) {
            previousFragment = new Module2_frag1();
            currentFragment = 1;
        } else if (currentFragment == 3) {
            previousFragment = new Module2_frag2();
            currentFragment = 2;
        } else {
            startActivity(new Intent(this, Learn.class));
            overridePendingTransition(R.anim.slide_to_right, R.anim.slide_from_left);
            finish();
            return;
        }

        loadFragment(previousFragment, false);
    }

    private void loadFragment(Fragment fragment, boolean isForward) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // forward = new fragment comes in from right, old one goes out to left
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
