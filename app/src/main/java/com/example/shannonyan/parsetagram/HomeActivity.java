package com.example.shannonyan.parsetagram;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final FragmentManager fragmentManager = getSupportFragmentManager();
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        final Fragment feedFragment = new FeedFragment();
        final Fragment profileFragment = new ProfileFragment();
        final Fragment cameraFragment = new CameraFragment();

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        switch (item.getItemId()) {
                            case R.id.action_favorites:
                                fragmentTransaction.replace(R.id.myFragment, feedFragment).commit();
                                return true;
                            case R.id.action_schedules:
                                fragmentTransaction.replace(R.id.myFragment, cameraFragment).commit();
                                return true;
                            case R.id.action_music:
                                fragmentTransaction.replace(R.id.myFragment, profileFragment).commit();
                                return true;
                             default:
                                 return false;
                        }
                    }
                });
    }

    public void openProfilePage(){
        final FragmentManager fragmentManager = getSupportFragmentManager();
        final Fragment profileFragment = new ProfileFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.myFragment, profileFragment).commit();
    }
}
