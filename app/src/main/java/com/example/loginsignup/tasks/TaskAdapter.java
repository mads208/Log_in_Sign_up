package com.example.loginsignup.tasks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginsignup.FirebaseServices;
import com.example.loginsignup.R;


import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private ArrayList<Task> taskList;
    Context context;
    private FirebaseServices fbs;



    public interface OnTaskClickListener {
        void onTaskClick(Task task);
    }

    public TaskAdapter(ArrayList<Task> taskList, OnTaskClickListener listener) {
        this.context = context;
        this.taskList = taskList;
        this.fbs = FirebaseServices.getInstance();

    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.taskText.setText(task.getTaskText());
        holder.createdDate.setText("Created: " + task.getCreatedDateString());
        holder.dueDate.setText("Due: " + task.getDueDate());
        holder.completedDate.setText("completed: "+ task.getCompletedDateString());
    }


    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView taskText, createdDate, dueDate,completedDate;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            taskText = itemView.findViewById(R.id.taskTexttv);
            createdDate = itemView.findViewById(R.id.createdDatetv);
            dueDate = itemView.findViewById(R.id.dueDatetv);
            completedDate = itemView.findViewById(R.id.);
        }
    }
}
