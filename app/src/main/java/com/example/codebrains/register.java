package com.example.codebrains;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.TintableCheckedTextView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.net.URI;
import java.util.ArrayList;

public class register extends AppCompatActivity {
Button btn_profile,btn_register;
ArrayList<String> gender=new ArrayList<>();;
Spinner spinner;
RadioGroup radiogrp;
private static final int CAMERA_REQ=100,GALLERY_REQ=200;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btn_profile=findViewById(R.id.profile);
        btn_register=findViewById(R.id.btn_register);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radiogrp=findViewById(R.id.radiogrp);
                radiogrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        if(checkedId==R.id.freelancer){
                            Intent i=new Intent(register.this,freelancer_form1.class);
                            startActivity(i);
                        }
                    }
                });
            }
        });


        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ad=new AlertDialog.Builder(register.this);
                ad.setIcon(R.drawable.baseline_account_circle_24);
                ad.setTitle("Profile Picture ");
                ad.setNegativeButton("Gallery", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i=new Intent(Intent.ACTION_PICK);
                        i.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(i,GALLERY_REQ);
                    }
                });
                ad.setPositiveButton("Camera", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(i,CAMERA_REQ);
                    }
                });
                ad.show();
            }
        });
        spinner=findViewById(R.id.spinner);
        gender.add("Male");
        gender.add("Female");
        ArrayAdapter<String> gender_aa=new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,gender);
        spinner.setAdapter(gender_aa);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode==RESULT_OK){
            if(requestCode==CAMERA_REQ)
            {
                Bitmap b=(Bitmap) data.getExtras().get("data");
            } else if (requestCode==GALLERY_REQ) {
                Uri u=data.getData();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}