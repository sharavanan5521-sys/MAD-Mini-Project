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

public class Module2_Quiz_Onboarding extends AppCompatActivity {
    public int currentFragment = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_module2_quiz_onboarding);

        loadFragment(new Module2_Quiz1());
        }

    public void goToNextFragment(){
        Fragment nextFragment;

        if(currentFragment == 1){
            nextFragment = new Module2_Quiz2();
            currentFragment = 2;
        } else if(currentFragment == 2){
            nextFragment = new Module2_Quiz3();
            currentFragment = 3;
        } else if(currentFragment == 3) {
            nextFragment = new Module2_score();
            currentFragment = 4;
        }   else {
            startActivity(new Intent(this, Learn.class));
            finish();
            return;
        }
        loadFragment(nextFragment);
    }
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.commit();
    }
}
