package com.example.loginsignup.tasks;

import android.os.Bundle;

import androidx.annotation.NonNull;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


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
    private ImageView backtohome;

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

    @Override
    public void onStart() {
        super.onStart();
        addButton=getView().findViewById(R.id.add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        /*itemsAdapter= new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        List.setAdapter(itemsAdapter);*/

        fbs = FirebaseServices.getInstance();
        tasks = new ArrayList<>();
        rvTasks = getView().findViewById(R.id.rvTaskspage);
        taskAdapter = new DataUserAdapter(getActivity(), tasks);
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
    /*private void addItem(View view) {
        EditText input = getView().findViewById(R.id.TaskET); 
        String itemText = input.getText().toString();
        
        if (!(itemText.equals(""))){
            itemsAdapter.add(itemText);
        }
        else {
            Toast.makeText(getActivity().getApplicationContext(), "please enter text...", Toast.LENGTH_SHORT).show();
        }


    }*/


}