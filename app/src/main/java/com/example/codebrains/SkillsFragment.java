package com.example.codebrains;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import android.widget.AutoCompleteTextView;

public class SkillsFragment extends Fragment {

    private TextInputEditText primarySkillEdit, additionalSkillsEdit;
    private AutoCompleteTextView experienceLevelDropdown;
    private MaterialButton previousButton, nextButton;

    @SuppressLint("WrongViewCast")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the fragment layout (fragment_skills.xml)
        View view = inflater.inflate(R.layout.fragment_skills, container, false);

        // Initialize views as defined in the XML
        primarySkillEdit = view.findViewById(R.id.primary_skill);
        additionalSkillsEdit = view.findViewById(R.id.additional_skills);
        experienceLevelDropdown = view.findViewById(R.id.experience_level);
        previousButton = view.findViewById(R.id.previous_button);
        nextButton = view.findViewById(R.id.next_button);

        // Set up the experience level dropdown with an ArrayAdapter.
        // Ensure you have defined a string-array resource named "experience_levels" in res/values/strings.xml.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                getResources().getStringArray(R.array.experience_levels));
        experienceLevelDropdown.setAdapter(adapter);

        // Handle "PREVIOUS" button click to navigate back.
        previousButton.setOnClickListener(v -> navigateToPrevious());

        // Handle "NEXT" button click to validate inputs and navigate forward.
        nextButton.setOnClickListener(v -> navigateToNext());

        return view;
    }

    private void navigateToPrevious() {
        // Navigate back to the previous fragment (e.g., JobDetailsFragment)
        getParentFragmentManager().popBackStack();
    }

    private void navigateToNext() {
        if (validateInputs()) {
            // Save data and navigate to the next fragment (e.g., BudgetFragment2)
            BudgetFragment2 budgetFragment2 = new BudgetFragment2();
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, budgetFragment2)
                    .addToBackStack(null)
                    .commit();

            // Optionally update step indicators if hosted in a JobpostingActivity.
            if (getActivity() instanceof JobpostingActivity) {
                ((JobpostingActivity) getActivity()).updateStepIndicators(3);
            }
        }
    }

    private boolean validateInputs() {
        if (primarySkillEdit.getText().toString().trim().isEmpty()) {
            primarySkillEdit.setError("Primary skill is required");
            return false;
        }
        // Additional validation can be added here if needed.
        return true;
    }

    // Optional getter methods to retrieve entered data

    public String getPrimarySkill() {
        return primarySkillEdit.getText().toString().trim();
    }

    public String getAdditionalSkills() {
        return additionalSkillsEdit.getText().toString().trim();
    }

    public String getExperienceLevel() {
        return experienceLevelDropdown.getText().toString().trim();
    }
}
