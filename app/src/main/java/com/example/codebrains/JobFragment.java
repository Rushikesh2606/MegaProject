package com.example.codebrains;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class JobFragment extends Fragment {
    private String filter;

    public static JobFragment newInstance(String filter) {
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
            filter = getArguments().getString("filter");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_job, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Sample data
        List<Job> allJobs = new ArrayList<>();
        allJobs.add(new Job("freelance work", "2025-03-02 17:37:49", "In Progress", 0));
        allJobs.add(new Job("React Developer", "2025-03-03 08:43:55", "In Progress", 0));
        allJobs.add(new Job("Video Editor", "2025-03-05 07:37:27", "In Progress", 0));
        allJobs.add(new Job("Video Editor", "2025-03-06 12:38:27", "Open", 2));
        allJobs.add(new Job("Video Editor", "2025-02-05 12:38:27", "completed", 20));



        List<Job> filteredJobs = new ArrayList<>();
        if ("open".equals(filter)) {
            for (Job job : allJobs) {
                if ("Open".equals(job.getStatus())) {
                    filteredJobs.add(job);
                }
            }
        } else if ("in-progress".equals(filter)) {
            for (Job job : allJobs) {
                if ("In Progress".equals(job.getStatus())) {
                    filteredJobs.add(job);
                }
            }
        } else if ("completed".equals(filter)) {
            for (Job job : allJobs) {
                if ("completed".equals(job.getStatus())) {
                    filteredJobs.add(job);
                }
            }
        } else {
            filteredJobs = allJobs;
        }

        recyclerView.setAdapter(new JobAdapter(filteredJobs));
        return view;
    }
}