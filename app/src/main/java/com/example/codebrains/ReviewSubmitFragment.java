package com.example.codebrains;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import android.widget.AutoCompleteTextView;

public class ReviewSubmitFragment extends Fragment {

    // Use AutoCompleteTextView for the location dropdown
    private AutoCompleteTextView locationPreferenceDropdown;
    // Additional questions field (using TextInputEditText as defined in XML)
    private TextInputEditText additionalQuestionsEditText;
    // Buttons for navigation
    private MaterialButton previousButton, submitButton;

    public ReviewSubmitFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review_submit, container, false);

        // Initialize UI elements from the XML
        locationPreferenceDropdown = view.findViewById(R.id.spinner_location_preference);
        additionalQuestionsEditText = view.findViewById(R.id.edittext_additional_questions);
        previousButton = view.findViewById(R.id.button_previous);
        submitButton = view.findViewById(R.id.button_submit);

        // Setup the AutoCompleteTextView dropdown with location options.
        // Ensure that you have a string-array resource named "location_preferences" defined in res/values/strings.xml.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                getResources().getStringArray(R.array.location_preferences));
        locationPreferenceDropdown.setAdapter(adapter);

        // Handle "PREVIOUS" button click to navigate back.
        previousButton.setOnClickListener(v -> navigateToPrevious());

        // Handle "SUBMIT" button click.
        submitButton.setOnClickListener(v -> handleSubmit());

        return view;
    }

    private void navigateToPrevious() {
        // Navigate back to the previous fragment (e.g., BudgetFragment)
        getParentFragmentManager().popBackStack();
    }

    private void handleSubmit() {
        if (validateInputs()) {
            Toast.makeText(requireContext(), "Job Posted Successfully!", Toast.LENGTH_SHORT).show();
            // TODO: Implement further navigation or API call after submission if needed.
        }
    }

    private boolean validateInputs() {
        // Retrieve and validate the selected location from the AutoCompleteTextView
        String selectedLocation = locationPreferenceDropdown.getText().toString().trim();
        if (selectedLocation.isEmpty() || selectedLocation.equalsIgnoreCase("Select Location")) {
            Toast.makeText(requireContext(), "Please select a valid location preference", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
