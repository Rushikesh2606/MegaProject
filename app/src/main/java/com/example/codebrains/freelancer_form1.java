package com.example.codebrains;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class freelancer_form1 extends AppCompatActivity {

    Button btn_submit;
    EditText calendertext,Tools,desc,tagLine;
    AutoCompleteTextView skills;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freelancer_form1);

//        Declarations
        calendertext = findViewById(R.id.calendertext);
        btn_submit = findViewById(R.id.btn_submit);
        Tools=findViewById(R.id.tools);
        skills=findViewById(R.id.skills);
        desc=findViewById(R.id.desc);
        tagLine=findViewById(R.id.tagLine);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line,
                getResources().getStringArray(R.array.job_categories));
        skills.setAdapter(adapter);


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validateFields()) {
                    Bundle bundle = getIntent().getExtras();
                  if(bundle!=null){
                    bundle.putString("Desc",desc.getText().toString());
                    bundle.putString("skills",skills.getText().toString());
                    bundle.putString("tagLine",tagLine.getText().toString());
                    bundle.putString("Tools",Tools.getText().toString());
                      bundle.putString("Years_of_experience",calendertext.getText().toString());
                    Intent i = new Intent(freelancer_form1.this, freelancer_register.class);
                      i.putExtras(bundle);
                    startActivity(i);
                }else{
                      Toast.makeText(freelancer_form1.this,"Error Receiving data",Toast.LENGTH_SHORT).show();
                  }
            }
            }
        });
    }



    private boolean validateFields() {
        if (TextUtils.isEmpty(Tools.getText())) {
            Tools.setError("Username is required !!");
            return false;
        }

        if (TextUtils.isEmpty(skills.getText())) {
            skills.setError("Password is required !!");
            return false;
        }

        if (TextUtils.isEmpty(desc.getText())) {
            desc.setError("Please enter Your Description !!");
            return false;
        }


        if (TextUtils.isEmpty(tagLine.getText())) {
            tagLine.setError("Please enter Your TagLine !!");
            return false;
        }

        // Validate date of birth
        if (TextUtils.isEmpty(calendertext.getText())) {
            calendertext.setError("Years of experience is required !!");
            return false;
        }

        return true;
    }

}
