package com.example.codebrains;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth=FirebaseAuth.getInstance();
        if(auth.getCurrentUser()!=null){
            SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            String profession = sharedPreferences.getString("profession", "");
            if(profession.equals("client")){
                Intent i=new Intent(MainActivity.this,Homepage.class);
                startActivity(i);
                finish();
            }
        else if(profession.equals("freelancer")) {
                Intent i=new Intent(MainActivity.this,Homepage_developer.class);
                startActivity(i);
                finish();
            }
        }
        else{
            Intent i=new Intent(MainActivity.this, MainActivity2.class);
            startActivity(i);
            finish();
        }
    }
}