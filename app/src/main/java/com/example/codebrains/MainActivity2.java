package com.example.codebrains;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class MainActivity2 extends AppCompatActivity {

    ViewFlipper v;
    Button btn;
    TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        // Initialize UI components
        v = findViewById(R.id.imgflip);
        btn = findViewById(R.id.login_button);
        txt = findViewById(R.id.signup);

        // Configure the ViewFlipper
        v.setFlipInterval(2000);
        v.startFlipping();  // Start auto-flipping images
      btn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent i=new Intent(MainActivity2.this,login.class);
              startActivity(i);
          }
      });
      txt.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent i2=new Intent(MainActivity2.this,signup.class);
              startActivity(i2);
          }
      });
    }
}