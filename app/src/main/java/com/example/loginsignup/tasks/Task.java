package com.example.loginsignup.tasks;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Task {
    private String taskText;
    private String createdDate;
    private String dueDate;

    public Task(String taskText,String dueDate, Date createdDate) {
    }

    public Task(String taskText, String createdDate, String dueDate) {
        this.taskText = taskText;
        this.createdDate = createdDate;
        this.dueDate=dueDate;
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



    public void setTaskText(String taskText) {
        this.taskText = taskText;
    }



    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }


}
