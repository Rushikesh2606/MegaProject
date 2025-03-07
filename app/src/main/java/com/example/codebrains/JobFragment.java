package com.example.codebrains;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class JobFragment extends Fragment implements JobAdapter.OnJobCloseListener {
    private String filter;
    private JobAdapter adapter;
    private List<JobController> jobs = new ArrayList<>();
    private ProgressBar progressBar;
    private TextView emptyTextView;
    private List<JobController> pendingJobs;

    public static JobFragment newInstance(String filter, JobAdapter.OnJobCloseListener listener) {
        JobFragment fragment = new JobFragment();
        Bundle args = new Bundle();
        args.putString("filter", filter);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.filter = getArguments().getString("filter");
        }
        this.filter = (this.filter == null) ? "All" : this.filter;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_job, container, false);

        progressBar = view.findViewById(R.id.progressBar);
        emptyTextView = view.findViewById(R.id.emptyTextView);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new JobAdapter(new ArrayList<>(), this);
        recyclerView.setAdapter(adapter);

        if (pendingJobs != null) {
            processPendingJobs();
        }

        return view;
    }

    public void updateJobs(List<JobController> newJobs) {
        this.jobs = newJobs;
        if (adapter != null) {
            filterJobs();
        } else {
            pendingJobs = newJobs;
        }
    }

    private void filterJobs() {
        List<JobController> filtered = new ArrayList<>();
        String processedFilter = (filter == null) ? "All" : filter.replace(" ", "");

        for (JobController job : jobs) {
            if ("All".equals(filter) ||
                    (job.getStatus() != null && job.getStatus().equalsIgnoreCase(processedFilter))) {
                filtered.add(job);
            }
        }

        adapter.updateJobs(filtered);
        updateEmptyState(filtered.isEmpty());
    }

    private void processPendingJobs() {
        if (pendingJobs != null) {
            jobs = pendingJobs;
            pendingJobs = null;
            filterJobs();
        }
    }

    private void updateEmptyState(boolean isEmpty) {
        progressBar.setVisibility(View.GONE);
        if (isEmpty) {
            emptyTextView.setVisibility(View.VISIBLE);
            emptyTextView.setText("No jobs available in this category");
        } else {
            emptyTextView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onJobClosed(String jobId) {
        for (JobController job : jobs) {
            if (job.getId().equals(jobId)) {
                job.setStatus("Completed");
                break;
            }
        }
        filterJobs(); // Refresh the list
    }
}