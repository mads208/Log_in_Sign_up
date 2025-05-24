package com.example.loginsignup.journaling;

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
import android.widget.ImageView;
import android.widget.Toast;

import com.example.loginsignup.DailyRating.Rating;
import com.example.loginsignup.DailyRating.dayRate;
import com.example.loginsignup.DailyRating.ratingAdapter;
import com.example.loginsignup.R;
import com.example.loginsignup.general.FirebaseServices;
import com.example.loginsignup.user.DataUserAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link journalHistory#newInstance} factory method to
 * create an instance of this fragment.
 */
public class journalHistory extends Fragment {
    private FirebaseServices fbs;
    private ArrayList<Journal> journals;
    private RecyclerView rvJournals;
    private journalAdapter adapterJo;
    private ImageView gotoJournal;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public journalHistory() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment journalHistory.
     */
    // TODO: Rename and change types and number of parameters
    public static journalHistory newInstance(String param1, String param2) {
        journalHistory fragment = new journalHistory();
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
        return inflater.inflate(R.layout.fragment_journal_history, container, false);
    }
    @Override
    public void onStart() {
        super.onStart();


        fbs = FirebaseServices.getInstance();
        journals = new ArrayList<>();
        rvJournals = getView().findViewById(R.id.rvJournal);
        adapterJo = new journalAdapter(getActivity(), journals);
        rvJournals.setAdapter(adapterJo);
        rvJournals.setHasFixedSize(true);
        rvJournals.setLayoutManager(new LinearLayoutManager(getActivity()));
        fbs.getFire().collection("journalData")
                .whereEqualTo("userId", fbs.getAuth().getCurrentUser().getUid())
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for (DocumentSnapshot dataSnapshot: queryDocumentSnapshots.getDocuments()){
                    Journal journal2 = dataSnapshot.toObject(Journal.class);
                    journals.add(journal2);
                }

                adapterJo.notifyDataSetChanged();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "No data available", Toast.LENGTH_SHORT).show();
                Log.e("journalHistory", e.getMessage());
            }
        });

        gotoJournal = getView().findViewById(R.id.backToJournal);
        gotoJournal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GotoAddData();
            }
        });



    }
    private void GotoAddData() {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayOutMain, new journalPage());
        ft.commit();
    }

}