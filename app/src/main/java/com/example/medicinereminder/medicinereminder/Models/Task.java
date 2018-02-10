package com.example.medicinereminder.medicinereminder.Models;

/**
 * Created by sukrit on 10/2/18.
 */

public class Task {
    String taskName;
    String status;
    String dateString;
    String timeString;

    public Task() {
    }

    public Task(String taskName, String status, String dateString, String timeString) {
        this.taskName = taskName;
        this.status = status;
        this.dateString = dateString;
        this.timeString = timeString;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public String getTimeString() {
        return timeString;
    }

    public void setTimeString(String timeString) {
        this.timeString = timeString;
    }
}
