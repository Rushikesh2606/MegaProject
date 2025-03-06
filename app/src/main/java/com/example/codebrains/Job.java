package com.example.codebrains;

public class Job {
    private String title;
    private String date;
    private String status;
    private int bids;

    public Job(String title, String date, String status, int bids) {
        this.title = title;
        this.date = date;
        this.status = status;
        this.bids = bids;
    }

    // Getters and setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public int getBids() { return bids; }
    public void setBids(int bids) { this.bids = bids; }
}