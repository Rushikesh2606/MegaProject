package com.example.codebrains;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class JobDetailsFragment extends Fragment {

    // Views from the layout
    private TextInputEditText jobTitleEdit, jobDescriptionEdit;
    private AutoCompleteTextView jobCategoryAutoComplete;
    private TextView fileNameText;
    private MaterialButton chooseFileButton, nextButton;

    // Request code for file picker
    private static final int FILE_PICKER_REQUEST_CODE = 1;

    public JobDetailsFragment() {
        // Required empty constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_job_details, container, false);

        // Initialize views from XML
        jobTitleEdit = view.findViewById(R.id.job_title);
        jobDescriptionEdit = view.findViewById(R.id.job_description);
        jobCategoryAutoComplete = view.findViewById(R.id.job_category);
        fileNameText = view.findViewById(R.id.file_name);
        chooseFileButton = view.findViewById(R.id.choose_file);
        nextButton = view.findViewById(R.id.next_button);

        // Setup the Job Category dropdown using an ArrayAdapter.
        // Make sure you have an array resource named "job_categories" in res/values/strings.xml.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                getResources().getStringArray(R.array.job_categories));
        jobCategoryAutoComplete.setAdapter(adapter);

        // Setup click listener for file selection
        chooseFileButton.setOnClickListener(v -> openFilePicker());

        // Setup click listener for the NEXT button
        nextButton.setOnClickListener(v -> navigateToSkills());

        // Optional: Setup DatePicker if a date field is present in your XML (e.g., with id "deadline_date")


        return view;
    }

    // Launches a file picker to select any file
    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent, FILE_PICKER_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILE_PICKER_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            Uri uri = data.getData();
            if (uri != null) {
                String fileName = getFileName(uri);
                fileNameText.setText(fileName);
            }
        }
    }

    // Retrieves the display name of the selected file
    @SuppressLint("Range")
    private String getFileName(Uri uri) {
        String result = null;
        if ("content".equals(uri.getScheme())) {
            try (Cursor cursor = requireActivity().getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            }
        }
        if (result == null) {
            result = uri.getPath();
            if (result != null) {
                int cut = result.lastIndexOf('/');
                if (cut != -1) {
                    result = result.substring(cut + 1);
                }
            }
        }
        return result;
    }

    // Validates user input; you can extend this as needed.
    private boolean validateInputs() {
        if (jobTitleEdit.getText().toString().trim().isEmpty()) {
            jobTitleEdit.setError("Job title is required");
            return false;
        }
        if (jobDescriptionEdit.getText().toString().trim().isEmpty()) {
            jobDescriptionEdit.setError("Job description is required");
            return false;
        }
        if (jobCategoryAutoComplete.getText().toString().trim().isEmpty()) {
            jobCategoryAutoComplete.setError("Job category is required");
            return false;
        }
        return true;
    }

    // Navigates to the next fragment (e.g., SkillsFragment) if inputs are valid.
    private void navigateToSkills() {
        if (validateInputs()) {
            SkillsFragment skillsFragment = new SkillsFragment();
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, skillsFragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

    // Displays a DatePickerDialog and sets the selected date in the provided EditText.
    private void showDatePicker(final EditText dateField) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                        // Format the selected date as desired (e.g., "dd/MM/yyyy")
                        String date = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                        dateField.setText(date);
                    }
                }, year, month, day);
        datePickerDialog.show();
    }
}
