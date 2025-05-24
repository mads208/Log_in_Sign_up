package com.example.loginsignup.task;

public class Task {
    private String id;          // Firestore document id
    private String taskName;
    private String dueDate;
    private String userId;

    public Task() {}  // needed for Firestore deserialization



    public Task(String taskName, String dueDate, String userId) {
        this.taskName = taskName;
        this.dueDate = dueDate;
        this.userId = userId;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTaskName() { return taskName; }
    public void setTaskName(String taskName) { this.taskName = taskName; }

    public String getDueDate() { return dueDate; }
    public void setDueDate(String dueDate) { this.dueDate = dueDate; }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
}


