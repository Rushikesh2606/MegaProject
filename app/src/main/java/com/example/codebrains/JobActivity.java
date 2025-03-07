package com.example.codebrains;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class JobActivity extends AppCompatActivity implements JobAdapter.OnJobCloseListener {

    private FirebaseDatabase database;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private DatabaseReference jobsRef;
    private ViewPager2 viewPager;
    private JobPagerAdapter pagerAdapter;
    private List<JobController> jobs = new ArrayList<>();
    private ProgressBar progressBar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_job);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.jobactivityid), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        progressBar = findViewById(R.id.progressBar);

        pagerAdapter = new JobPagerAdapter(this, this);
        viewPager.setAdapter(pagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    switch (position) {
                        case 0:
                            tab.setText("All Jobs");
                            break;
                        case 1:
                            tab.setText("Open Jobs");
                            break;
                        case 2:
                            tab.setText("In-Progress Jobs");
                            break;
                        case 3:
                            tab.setText("Completed Jobs");
                            break;
                    }
                }).attach();

        database = FirebaseDatabase.getInstance();
        jobsRef = database.getReference("jobs");
        fetchJobsFromFirebase();
    }

    private void fetchJobsFromFirebase() {
        showProgressBar(true);
        jobsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                jobs.clear();
                for (DataSnapshot jobSnapshot : snapshot.getChildren()) {
                    JobController job = jobSnapshot.getValue(JobController.class);
                    if (job != null && job.getUsername() != null) {
                        job.setId(jobSnapshot.getKey());
                        if (job.getUsername().equals(auth.getCurrentUser().getUid())) {
                            jobs.add(job);
                        }
                    }
                }
                showProgressBar(false);
                updateAdapter(jobs);
                updateTabCounts(jobs);
                checkEmptyState();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                showProgressBar(false);
                Toast.makeText(getApplicationContext(), "Failed to load data.", Toast.LENGTH_SHORT).show();
                Log.e("JobActivity", "Database error: " + error.getMessage());
            }
        });
    }

    private void updateAdapter(List<JobController> jobs) {
        if (pagerAdapter != null) {
            pagerAdapter.setJobs(jobs);
        }
    }

    private void updateTabCounts(List<JobController> jobs) {
        int allCount = jobs.size();
        int openCount = 0;
        int inProgressCount = 0;
        int completedCount = 0;

        for (JobController job : jobs) {
            if ("Open".equalsIgnoreCase(job.getStatus())) {
                openCount++;
            } else if ("In-Progress".equalsIgnoreCase(job.getStatus())) {
                inProgressCount++;
            } else if ("Completed".equalsIgnoreCase(job.getStatus())) {
                completedCount++;
            }
        }

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        if (tabLayout != null) {
            tabLayout.getTabAt(0).setText("All Jobs (" + allCount + ")");
            tabLayout.getTabAt(1).setText("Open Jobs (" + openCount + ")");
            tabLayout.getTabAt(2).setText("In-Progress Jobs (" + inProgressCount + ")");
            tabLayout.getTabAt(3).setText("Completed Jobs (" + completedCount + ")");
        }
    }

    private void showProgressBar(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private void checkEmptyState() {
        if (jobs.isEmpty()) {
            findViewById(R.id.emptyTextView).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.emptyTextView).setVisibility(View.GONE);
        }
    }

    @Override
    public void onJobClosed(String jobId) {
        jobsRef.child(jobId).child("status").setValue("completed")
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Job marked as completed", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Log.e("JobActivity", "Failed to update job", e);
                });
    }
}