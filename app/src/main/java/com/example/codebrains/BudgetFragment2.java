package com.example.codebrains;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class BudgetFragment2 extends Fragment {

    private TextInputEditText budgetAmountEdit, deadlineEdit;
    private AutoCompleteTextView budgetTypeDropdown, projectVisibilityDropdown;
    private MaterialButton previousButton, nextButton;

    public BudgetFragment2() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_budget2, container, false);

        // Initialize views from XML
        budgetAmountEdit = view.findViewById(R.id.budget_amount);
        deadlineEdit = view.findViewById(R.id.deadline);
        budgetTypeDropdown = view.findViewById(R.id.budget_type_spinner);
        projectVisibilityDropdown = view.findViewById(R.id.project_visibility_spinner);
        previousButton = view.findViewById(R.id.previous_button);
        nextButton = view.findViewById(R.id.next_button);

        // Setup dropdown for Budget Type
        // Ensure you have defined a string-array resource named "budget_types" in res/values/strings.xml.
        ArrayAdapter<String> budgetTypeAdapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                getResources().getStringArray(R.array.budget_types));
        budgetTypeDropdown.setAdapter(budgetTypeAdapter);

        // Setup dropdown for Project Visibility
        // Ensure you have defined a string-array resource named "project_visibility_options" in res/values/strings.xml.
        ArrayAdapter<String> visibilityAdapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                getResources().getStringArray(R.array.project_visibility_options));
        projectVisibilityDropdown.setAdapter(visibilityAdapter);

        // Set click listeners for navigation buttons
        previousButton.setOnClickListener(v -> navigateToPrevious());
        nextButton.setOnClickListener(v -> navigateToNext());

        return view;
    }

    private void navigateToPrevious() {
        // Navigate back to the previous fragment (for example, SkillsFragment)
        getParentFragmentManager().popBackStack();
    }

    private void navigateToNext() {
        if (validateInputs()) {
            // Save data if necessary and navigate to the next fragment (e.g., ReviewSubmitFragment)
            ReviewSubmitFragment reviewSubmitFragment = new ReviewSubmitFragment();
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, reviewSubmitFragment)
                    .addToBackStack(null)
                    .commit();

            // Optionally update step indicators if hosted in a JobpostingActivity
            if (getActivity() instanceof JobpostingActivity) {
                ((JobpostingActivity) getActivity()).updateStepIndicators(3);
            }
        }
    }

    private boolean validateInputs() {
        String budget = budgetAmountEdit.getText().toString().trim();
        String deadline = deadlineEdit.getText().toString().trim();
        String budgetType = budgetTypeDropdown.getText().toString().trim();
        String projectVisibility = projectVisibilityDropdown.getText().toString().trim();

        if (budget.isEmpty()) {
            budgetAmountEdit.setError("Budget amount is required");
            return false;
        }
        if (deadline.isEmpty()) {
            deadlineEdit.setError("Deadline is required");
            return false;
        }
        if (budgetType.isEmpty() || budgetType.equalsIgnoreCase("Select Budget Type")) {
            Toast.makeText(requireContext(), "Please select a valid budget type", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (projectVisibility.isEmpty() || projectVisibility.equalsIgnoreCase("Select Visibility")) {
            Toast.makeText(requireContext(), "Please select a valid project visibility", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
