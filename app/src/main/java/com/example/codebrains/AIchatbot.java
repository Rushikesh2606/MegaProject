package com.example.codebrains;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.codebrains.databinding.ActivityAichatbotBinding;
import com.example.codebrains.databinding.ActivityMainBinding;

public class AIchatbot extends AppCompatActivity {

    ActivityAichatbotBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAichatbotBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.Ai), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // API KEY: AIzaSyAF767lqh4cM4VjMZ0UjwOCnrIT-x2oUMc

        binding.buttonSend.setOnClickListener(v ->{

            GeminiPro model = new GeminiPro();

            String query = binding.inputPrompt.getText().toString();
            binding.progressBar.setVisibility(View.VISIBLE);

            binding.textViewAnswer.setText("");
            binding.inputPrompt.setText("");


            model.getResponse(query, new ResponseCallback() {
                @Override
                public void onResponse(String response) {
                    binding.textViewAnswer.setText(response);
                    binding.progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onError(Throwable throwable) {
                    Toast.makeText(AIchatbot.this, "Error! " + throwable.toString(), Toast.LENGTH_SHORT).show();
                    binding.progressBar.setVisibility(View.GONE);
                }
            });
        });

    }
}