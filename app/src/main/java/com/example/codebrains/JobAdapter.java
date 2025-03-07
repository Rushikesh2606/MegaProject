package com.example.codebrains;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.ViewHolder> {
    public interface OnJobCloseListener {
        void onJobClosed(String jobId);
    }

    private List<JobController> jobs;
    private final OnJobCloseListener closeListener;

    public JobAdapter(List<JobController> jobs, OnJobCloseListener closeListener) {
        this.jobs = jobs;
        this.closeListener = closeListener;
    }

    public void updateJobs(List<JobController> newJobs) {
        this.jobs = new ArrayList<>(newJobs);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.job_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        JobController job = jobs.get(position);
        holder.tvTitle.setText(job.getJobTitle());
        holder.tvDate.setText("Posted Date: " + job.getPostedDate());
        holder.tvStatus.setText("Status: " + job.getStatus());
        holder.tvBids.setText("Bids Received: " + job.getNoOfBidsReceived());

        holder.btnViewDetails.setOnClickListener(v -> {
            Context context = v.getContext();
            Intent intent = new Intent(context, Job_View_Details.class);
            intent.putExtra("JOB_ID", job.getId());
            context.startActivity(intent);
        });

        holder.btnCloseJob.setOnClickListener(v -> {
            if (closeListener != null && job != null) {
                closeListener.onJobClosed(job.getId());
            }
        });
    }

    @Override
    public int getItemCount() { return jobs.size(); }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDate, tvStatus, tvBids;
        Button btnViewDetails, btnCloseJob;

        ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvBids = itemView.findViewById(R.id.tvBids);
            btnViewDetails = itemView.findViewById(R.id.btnViewDetails);
            btnCloseJob = itemView.findViewById(R.id.btnCloseJob);
        }
    }
}