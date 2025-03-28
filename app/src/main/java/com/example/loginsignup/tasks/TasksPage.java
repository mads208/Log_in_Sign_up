package com.example.loginsignup.tasks;

import android.content.DialogInterface;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.loginsignup.FirebaseServices;
import com.example.loginsignup.HomePage;
import com.example.loginsignup.R;
import com.example.loginsignup.user.DataUser;
import com.example.loginsignup.user.DataUserAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import java.util.Date;



/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TasksPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TasksPage extends Fragment {
    private ArrayList<Task> tasks;
    private Button addButton;
    private TaskAdapter taskAdapter;
    private FirebaseServices fbs;
    private RecyclerView rvTasks;
    private EditText taskText;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TasksPage() {
        // Required empty public constructor
    }
    @Override
    public void onStart() {
        super.onStart();


        /*itemsAdapter= new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        List.setAdapter(itemsAdapter);*/

        connectComponents();



        fbs = FirebaseServices.getInstance();
        tasks = new ArrayList<>();
        rvTasks = getView().findViewById(R.id.rvTaskspage);
        taskAdapter = new TaskAdapter(getActivity(), tasks);
        rvTasks.setAdapter(taskAdapter);
        rvTasks.setHasFixedSize(true);
        rvTasks.setLayoutManager(new LinearLayoutManager(getActivity()));
        fbs.getFire().collection("task").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for (DocumentSnapshot dataSnapshot: queryDocumentSnapshots.getDocuments()){
                    Task task = dataSnapshot.toObject(Task.class);
                    tasks.add(task);
                }

                taskAdapter.notifyDataSetChanged();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "No data available", Toast.LENGTH_SHORT).show();
                Log.e("AllDataFragment", e.getMessage());
            }
        });

    }
    private void connectComponents() {

        taskText = getView().findViewById(R.id.TaskET);
        fbs =  FirebaseServices.getInstance();

        addButton=getView().findViewById(R.id.add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskText2;
                String createdDate2;
                taskText2 = taskText.getText().toString();

                long currentTime = System.currentTimeMillis();  // This gives the current time in milliseconds
                createdDate2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date(currentTime));


                if( taskText2.trim().isEmpty() ){
                    Toast.makeText(getActivity(), "some fields are empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                Task task = new Task(taskText2,createdDate2 ,"");

                //fbs.getAuth().createUserWithEmailAndPassword(user, pass);

                fbs.getFire().collection("task").add(task).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "fail", Toast.LENGTH_SHORT).show();
                        return;

                    }
                });
                showDueDateDialog(task);
            }
        });

    }
    private void showDueDateDialog(Task task) {
        // Create an EditText for the due date input
        final EditText dueDateInput = new EditText(getActivity());
        dueDateInput.setHint("Enter due date");

        // Create an AlertDialog to ask for the due date
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Enter Due Date")
                .setView(dueDateInput)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Get the due date entered by the user
                        String dueDate2 = dueDateInput.getText().toString().trim();

                        if (dueDate2.isEmpty()) {
                            Toast.makeText(getActivity(), "Due date is required", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // Update the task with the due date
                        task.setDueDate(dueDate2);

                        // Now add the task to Firestore
                        fbs.getFire().collection("task").add(task).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                // Handle success (optional)
                                Toast.makeText(getActivity(), "Task added successfully", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(), "Failed to add task", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .setNegativeButton("Cancel", null)  // Do nothing on Cancel
                .create()
                .show();
    }




    /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TasksPage.
         */
    // TODO: Rename and change types and number of parameters
    public static TasksPage newInstance(String param1, String param2) {
        TasksPage fragment = new TasksPage();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tasks_page, container, false);
    }




}