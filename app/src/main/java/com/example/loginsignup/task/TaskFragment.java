package com.example.loginsignup.task;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.loginsignup.R;
import com.example.loginsignup.general.HomePage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;



public class TaskFragment extends Fragment {

    private EditText taskInput;
    private Button addTaskBtn;
    private RecyclerView taskRecyclerView;
    private TaskAdapter adapter;
    private List<Task> taskList;
    private FirebaseFirestore fbs;
    private ImageView backtoHomePage;
    private String userId;



    public TaskFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_task, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fbs = FirebaseFirestore.getInstance();
        taskInput = view.findViewById(R.id.taskInput);
        addTaskBtn = view.findViewById(R.id.addTaskBtn);
        taskRecyclerView = view.findViewById(R.id.taskRecyclerView);

        taskList = new ArrayList<>();
        adapter = new TaskAdapter(getActivity(), taskList);
        taskRecyclerView.setAdapter(adapter);
        taskRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        loadTasksFromFirestore();

        addTaskBtn.setOnClickListener(v -> {
            String taskText = taskInput.getText().toString().trim();
            if (taskText.isEmpty()) {
                Toast.makeText(getActivity(), "Please enter a task", Toast.LENGTH_SHORT).show();
                return;
            }
            showDatePickerAndAddTask(taskText);
        });
        backtoHomePage = view.findViewById(R.id.backtoHomePageTask);
        backtoHomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GotoHomePage();
            }
        });


    }
    @Override
    public void onResume() {
        super.onResume();
        loadTasksFromFirestore();
    }


    private void loadTasksFromFirestore() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(getActivity(), "User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }
        String userId = currentUser.getUid();
        fbs.collection("tasks")
                .whereEqualTo("user_ID", userId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    taskList.clear();
                    for (DocumentSnapshot doc : queryDocumentSnapshots.getDocuments()) {
                        Task task = doc.toObject(Task.class);
                        if (task != null) {
                            task.setId(doc.getId());
                            taskList.add(task);
                        }

                    }
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(getActivity(), "Failed to load tasks", Toast.LENGTH_SHORT).show());
    }

    private void showDatePickerAndAddTask(String taskText) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog dpd = new DatePickerDialog(getActivity(),
                (view, year, month, dayOfMonth) -> {
                    // Month is 0-based
                    String dueDateStr = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month + 1, dayOfMonth);
                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                    userId = currentUser.getUid();
                    Task newTask = new Task(taskText, dueDateStr,userId);

                    fbs.collection("tasks")
                            .add(newTask)
                            .addOnSuccessListener(documentReference -> {
                                newTask.setId(documentReference.getId());
                                taskList.add(newTask);
                                adapter.notifyItemInserted(taskList.size() - 1);
                                taskInput.setText("");
                            })
                            .addOnFailureListener(e ->
                                    Toast.makeText(getActivity(), "Failed to add task", Toast.LENGTH_SHORT).show());
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dpd.show();
    }
    private void GotoHomePage() {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayOutMain, new HomePage());
        ft.commit();
    }
}
