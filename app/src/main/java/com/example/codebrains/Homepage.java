package com.example.codebrains;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class Homepage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        loadFragment(new HomeFragment());
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigation);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            FrameLayout frame=findViewById(R.id.frame);
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                int i=item.getItemId();
                if(i==R.id.nav_jobs){
                    loadFragment(new JobsFragment());
                }
                else if(i==R.id.nav_home){
                    loadFragment(new HomeFragment());
                }
                else if(i==R.id.nav_messages){
                    loadFragment(new MessagesFragment());
                }
                else if (i==R.id.nav_profile){
                    loadFragment(new developer_profile());
                }
//
                else if (selectedFragment != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, selectedFragment).commit();
                }
                return true;
            }
        });

        }

    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frame, fragment);
        ft.commit();
    }
}