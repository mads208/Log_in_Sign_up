package com.example.loginsignup.DailyRating;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginsignup.R;
import com.example.loginsignup.general.FirebaseServices;
import com.example.loginsignup.user.AddDataFragment;
import com.example.loginsignup.user.DataUser;
import com.example.loginsignup.user.DataUserAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ratingHistory#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ratingHistory extends Fragment {
    private FirebaseServices fbs;
    private ArrayList<Rating> ratings;
    private RecyclerView rvRating;
    private ratingAdapter adapter3;
    private ImageView gotoRating;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ratingHistory() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ratingHistory.
     */
    // TODO: Rename and change types and number of parameters
    public static ratingHistory newInstance(String param1, String param2) {
        ratingHistory fragment = new ratingHistory();
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
        return inflater.inflate(R.layout.fragment_rating_history, container, false);

    }


    @Override
    public void onStart() {
        super.onStart();


        fbs = FirebaseServices.getInstance();
        ratings = new ArrayList<>();
        rvRating = getView().findViewById(R.id.rvRatings);
        adapter3 = new ratingAdapter(getActivity(), ratings);
        rvRating.setAdapter(adapter3);
        rvRating.setHasFixedSize(true);
        rvRating.setLayoutManager(new LinearLayoutManager(getActivity()));
        fbs.getFire().collection("ratingData").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for (DocumentSnapshot dataSnapshot: queryDocumentSnapshots.getDocuments()){
                    Rating rating2 = dataSnapshot.toObject(Rating.class);
                    ratings.add(rating2);
                }

                adapter3.notifyDataSetChanged();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "No data available", Toast.LENGTH_SHORT).show();
                Log.e("ratingHistory", e.getMessage());
            }
        });

        gotoRating = getView().findViewById(R.id.backToRating2);
        gotoRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GotoAddData();
            }
        });



    }
    private void GotoAddData() {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayOutMain, new dayRate());
        ft.commit();
    }

}