package com.example.codebrains;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.codebrains.databinding.ActivityHomepageDeveloperBinding;

public class Homepage_developer extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityHomepageDeveloperBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomepageDeveloperBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarHomepageDeveloper.toolbar);
        binding.appBarHomepageDeveloper.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Homepage_developer.this,AIchatbot.class);
                startActivity(i);
                finish();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_homepage_developer);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.Profile) {
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.home, new developer_profile()); // Use the correct container ID
                    ft.commit();
                } else if (id == R.id.Home) {
                    // Navigate to home fragment
                    navController.navigate(R.id.nav_home);
                } else if (id == R.id.findjob) {
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.home, new HomeFragment()); // Use the correct container ID
                    ft.commit();
//                    Intent i = new Intent(Homepage_developer.this, FindJobActivity.class);
//                    startActivity(i);
                } else if (id == R.id.evaluate_project) {
//                    Intent i = new Intent(Homepage_developer.this, EvaluateProjectActivity.class);
//                    startActivity(i);
                } else if (id == R.id.chat) {
//                    Intent i = new Intent(Homepage_developer.this, ChatActivity.class);
//                    startActivity(i);
                } else if (id == R.id.ContactUs) {

                    Contactus contactusFragment = new Contactus(Homepage_developer.this);


                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.nav_home, contactusFragment)
                            .commit();
                    return true;
                }

                return NavigationUI.onNavDestinationSelected(item, navController)
                        || Homepage_developer.super.onOptionsItemSelected(item);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.homepage_developer, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_homepage_developer);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}