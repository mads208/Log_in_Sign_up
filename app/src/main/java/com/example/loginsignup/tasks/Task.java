package com.example.loginsignup.tasks;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Task {
    private String taskText;
    private Date createdDate;
    private String dueDate;
    private Date completedDate;

    public Task() {
    }

    public Task(String taskText, Date createdDate, String dueDate) {
        this.taskText = taskText;
        this.createdDate = createdDate;
        this.dueDate = dueDate;
        this.completedDate = null; // Not completed yet
    }

    public String getTaskText() {
        return taskText;
    }

    public String getCreatedDateString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return sdf.format(createdDate);
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setCompletedDate(Date completedDate) {
        this.completedDate = completedDate;
    }

    public void setTaskText(String taskText) {
        this.taskText = taskText;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getCompletedDateString() {
        if (completedDate == null) return "Not completed";
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return sdf.format(completedDate);
    }
}
