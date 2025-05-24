package com.example.loginsignup.task;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginsignup.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private Context context;
    private List<Task> taskList;
    private FirebaseFirestore fbs = FirebaseFirestore.getInstance();

    public TaskAdapter(Context context, List<Task> tasks) {
        this.context = context;
        this.taskList = tasks;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.task_item, parent, false);
        return new TaskViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.taskName.setText(task.getTaskName());
        holder.dueDate.setText(task.getDueDate());
        holder.doneCheck.setChecked(false);

        holder.doneCheck.setOnCheckedChangeListener(null);

        holder.doneCheck.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                if (task.getId() != null) {
                    fbs.collection("tasks").document(task.getId())
                            .delete()
                            .addOnSuccessListener(aVoid -> {
                                taskList.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, taskList.size());
                            })
                            .addOnFailureListener(e ->
                                    Toast.makeText(context, "Failed to delete task", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        CheckBox doneCheck;
        TextView taskName, dueDate;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            doneCheck = itemView.findViewById(R.id.doneCheck);
            taskName = itemView.findViewById(R.id.taskName);
            dueDate = itemView.findViewById(R.id.dueDate);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        String fullTask = taskName.getText().toString();

                        new AlertDialog.Builder(itemView.getContext())
                                .setTitle("Full Task")
                                .setMessage(fullTask)
                                .setPositiveButton("OK", null)
                                .show();
                    }
                }
            });
        }

    }
}


