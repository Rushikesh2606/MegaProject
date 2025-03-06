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
import java.util.List;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.ViewHolder> {
    private List<Job> jobs;
    private OnJobCloseListener closeListener;

    public interface OnJobCloseListener {
        void onJobClosed(int position);
    }

    public JobAdapter(List<Job> jobs, OnJobCloseListener closeListener) {
        this.jobs = jobs;
        this.closeListener = closeListener;
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
        Job job = jobs.get(position);
        holder.tvTitle.setText(job.getTitle());
        holder.tvDate.setText("Posted Date: " + job.getDate());
        holder.tvStatus.setText("Status: " + job.getStatus());
        holder.tvBids.setText("Bids Received: " + job.getBids());

        // View Details Click Listener
        holder.btnViewDetails.setOnClickListener(v -> {
            Context context = holder.itemView.getContext();
            Intent intent = new Intent(context, Job_View_Details.class);
            intent.putExtra("JOB_TITLE", job.getTitle());
            intent.putExtra("POSTED_DATE", job.getDate());
            intent.putExtra("JOB_STATUS", job.getStatus());
            intent.putExtra("BIDS_COUNT", job.getBids());
            context.startActivity(intent);
        });

        // Close Job Click Listener
        holder.btnCloseJob.setOnClickListener(v -> {
            if (closeListener != null) {
                closeListener.onJobClosed(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return jobs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDate, tvStatus, tvBids;
        Button btnViewDetails, btnCloseJob;

        public ViewHolder(@NonNull View itemView) {
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