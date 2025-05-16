package com.example.loginsignup.task;

public class Task {
    private String id;          // Firestore document id
    private String taskName;
    private String dueDate;     // formatted date string

    public Task() {}  // needed for Firestore deserialization

    public Task(String taskName, String dueDate) {
        this.taskName = taskName;
        this.dueDate = dueDate;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTaskName() { return taskName; }
    public void setTaskName(String taskName) { this.taskName = taskName; }

    public String getDueDate() { return dueDate; }
    public void setDueDate(String dueDate) { this.dueDate = dueDate; }
}


