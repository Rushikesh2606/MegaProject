package com.example.codebrains;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Arrays;


public class login extends AppCompatActivity {
    EditText email, password;
    FirebaseAuth firebaseAuth;
    AutoCompleteTextView profession;
    ArrayList<String> arr_profession=new ArrayList<>(Arrays.asList("Client","Freelancer"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();
        profession=findViewById(R.id.profession);
        ArrayAdapter<String> aa= new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,arr_profession);
        profession.setAdapter(aa);
        Button loginButton = findViewById(R.id.sign_up_button);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = email.getText().toString().trim();
                String Password = password.getText().toString().trim();
                String role = profession.getText().toString().trim(); // Get selected profession

                // Validate input fields
                if (TextUtils.isEmpty(Email)) {
                    email.setError("Email cannot be empty");
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
                    email.setError("Enter a valid email");
                    return;
                }
                if (TextUtils.isEmpty(Password)) {
                    password.setError("Password cannot be empty");
                    return;
                }
                if (Password.length() < 6) {
                    password.setError("Password should be at least 6 characters long");
                    return;
                }
                if (TextUtils.isEmpty(role)) {
                    profession.setError("Please select a profession");
                    return;
                }

                // Login with Firebase
                firebaseAuth.signInWithEmailAndPassword(Email, Password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Save profession in SharedPreferences
                                    SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("profession", role); // Save profession
                                    editor.apply(); // Commit changes

                                    // Login successful
                                    Toast.makeText(login.this, "Login successful!", Toast.LENGTH_SHORT).show();

                                    // Navigate to Homepage
                                    Intent intent = new Intent(login.this, Homepage.class);
                                    startActivity(intent);
                                    finish(); // Close login activity
                                } else {
                                    // Login failed
                                    Toast.makeText(login.this, "Login failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });


    }
}
