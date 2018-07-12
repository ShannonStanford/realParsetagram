package com.example.shannonyan.parsetagram;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

public class HomeActivity extends AppCompatActivity {

    private static final String imagePath = Environment.getExternalStorageDirectory() + "/storage/emulated/0/DCIM/Camera/IMG_20180710_130915.jpg";
    private EditText etDescription;
    private Button btCreate;
    private Button btRefresh;
    private Button btCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final FragmentManager fragmentManager = getSupportFragmentManager();

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);


        // define your fragments here
        final Fragment feedFragment = new FeedFragment();
        final Fragment profileFragment = new ProfileFragment();
        final Fragment cameraFragment = new CameraFragment();

        // handle navigation selection
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
}
