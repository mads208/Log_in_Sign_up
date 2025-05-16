package com.example.loginsignup.tasks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginsignup.general.FirebaseServices;
import com.example.loginsignup.R;


import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.MyViewHolder> {
    private ArrayList<Task> taskList;
    Context context;
    private FirebaseServices fbs;





    public TaskAdapter(Context context, ArrayList<Task> taskList) {
        this.context = context;
        this.taskList = taskList;
        this.fbs = FirebaseServices.getInstance();

    }

    @NonNull
    public TaskAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View v= LayoutInflater.from(context).inflate(R.layout.item_task,parent,false);
        return  new TaskAdapter.MyViewHolder(v);
    }



    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.taskText.setText(task.getTaskText());
        holder.createdDate.setText(task.getCreatedDateString());
        holder.dueDate.setText("Due: " + task.getDueDate());
    }


    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView taskText, createdDate, dueDate;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            taskText = itemView.findViewById(R.id.taskTexttv);
            createdDate = itemView.findViewById(R.id.createdDatetv);
            dueDate = itemView.findViewById(R.id.dueDatetv);
        }
    }
}
