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

public class Module3_Onboarding extends AppCompatActivity {

    public int currentFragment = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_module3_onboarding);

        // if your layout root has id="@+id/main"
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // first fragment, no animation
        loadFragment(new Module3_frag1(), false);
    }

    public void goToNextFragment() {
        Fragment nextFragment;

        if (currentFragment == 1) {
            nextFragment = new Module3_frag2();
            currentFragment = 2;
        } else if (currentFragment == 2) {
            nextFragment = new Module3_frag3();
            currentFragment = 3;
        } else {
            // done with module 3 onboarding
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
            previousFragment = new Module3_frag1();
            currentFragment = 1;
        } else if (currentFragment == 3) {
            previousFragment = new Module3_frag2();
            currentFragment = 2;
        } else {
            // if already at first, go back to Learn
            startActivity(new Intent(this, Learn.class));
            overridePendingTransition(R.anim.slide_to_right, R.anim.slide_from_left);
            finish();
            return;
        }

        loadFragment(previousFragment, false);
    }

    private void loadFragment(Fragment fragment, boolean isForward) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (isForward) {
            // moving forward in the module
            transaction.setCustomAnimations(
                    R.anim.slide_from_left,   // enter
                    R.anim.slide_to_right,    // exit
                    R.anim.slide_to_right,    // pop enter
                    R.anim.slide_from_left    // pop exit
            );
        } else {
            // going back
            transaction.setCustomAnimations(
                    R.anim.slide_to_right,
                    R.anim.slide_from_left,
                    R.anim.slide_from_left,
                    R.anim.slide_to_right
            );
        }

        transaction.replace(R.id.frame_container, fragment);
        transaction.commit();
    }

    // Overload for first load
    private void loadFragment(Fragment fragment) {
        loadFragment(fragment, false);
    }
}
