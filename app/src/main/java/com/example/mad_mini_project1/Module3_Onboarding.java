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

        loadFragment(new Module3_frag1());
        }

    public void goToNextFragment(){
        Fragment nextFragment;

        if(currentFragment == 1){
            nextFragment = new Module3_frag2();
            currentFragment = 2;
        } else if(currentFragment == 2){
            nextFragment = new Module3_frag3();
            currentFragment = 3;
        } else {
            startActivity(new Intent(this, Learn.class));
            finish();
            return;
        }

        loadFragment(nextFragment);
    }
    public void goToPreviousFragment(){
        Fragment previousFragment;

        if(currentFragment == 2) {
            previousFragment = new Module3_frag1();
            currentFragment = 1;
        } else if(currentFragment == 3){
            previousFragment = new Module3_frag2();
            currentFragment = 2;
        } else {
            startActivity(new Intent(this, Learn.class));
            finish();
            return;
        }

        loadFragment(previousFragment);
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.commit();
    }
}
