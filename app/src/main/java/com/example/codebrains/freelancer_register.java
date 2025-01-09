package com.example.codebrains;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class freelancer_register extends AppCompatActivity {
  private static final int PICK_FILE_REQUEST=100;
  TextView file_name,uploaded;
  Button resume,submit_btn;
  Spinner availability_spinner;
  ArrayList<String> available=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freelancer_register);

        //redirecting to homepage
        submit_btn=findViewById(R.id.submit_btn);
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(freelancer_register.this, Homepage.class);
                startActivity(i);
            }
        });
   // related to the resume file picker

        resume=findViewById(R.id.resume);
        file_name=findViewById(R.id.file_name);
        uploaded=findViewById(R.id.resume_uploaded);

        resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
                resume.setVisibility(View.GONE);
                uploaded.setText("Resume Uploaded");
                file_name.setVisibility(View.VISIBLE);
            }
        });

//        spinner
        availability_spinner=findViewById(R.id.availability_spinner);
        available.add("Full-Time");
        available.add("Part-Time");
        ArrayAdapter<String> aa=new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item,available);
        availability_spinner.setAdapter(aa);





    }
//    method for creating intent for picjking up the file
    private void openFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf"); // Restrict to PDF files
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent, "Select Resume"), PICK_FILE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_FILE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri fileUri = data.getData();

            // Display selected file name (optional)
            String fileName = getFileName(fileUri);
            Toast.makeText(this, "Selected File: " + fileName, Toast.LENGTH_SHORT).show();

        }
    }
//    method for getting file name
    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        file_name.setText(result);
        return result;
    }
}