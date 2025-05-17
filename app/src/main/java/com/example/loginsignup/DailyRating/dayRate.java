package com.example.loginsignup.DailyRating;

import android.icu.text.SimpleDateFormat;
import android.icu.util.TimeZone;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginsignup.R;
import com.example.loginsignup.general.FirebaseServices;
import com.example.loginsignup.general.HomePage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;

import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link dayRate#newInstance} factory method to
 * create an instance of this fragment.
 */
public class dayRate extends Fragment {
    private ImageView backToHomePage;
    private SeekBar seekBar;
    private TextView Addbtn;
    private FirebaseServices fbs;
    private ImageView history;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public dayRate() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        connectComponents(view);
    }

    private void connectComponents(View view) {
        fbs = FirebaseServices.getInstance();
        backToHomePage = view.findViewById(R.id.backToHomePageRate);
        backToHomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GotoHomePage();
            }
        });
        history = view.findViewById(R.id.historyRate);
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GotoHistory();
            }
        });


        seekBar = view.findViewById(R.id.seekBar2);
        int[] currentScore = {0};

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                currentScore[0] = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

        });

        Addbtn = view.findViewById(R.id.doneRate);
        Addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String seekbar1,createdDate3;
                seekbar1 =  String.valueOf(currentScore[0]);


                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                sdf.setTimeZone(TimeZone.getTimeZone("Asia/Jerusalem"));
                createdDate3 = sdf.format(new Date());


                if( seekbar1.trim().isEmpty() || createdDate3.trim().isEmpty() ){
                    Toast.makeText(getActivity(), "some fields are empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                Rating rating = new Rating(createdDate3,seekbar1);

                //fbs.getAuth().createUserWithEmailAndPassword(user, pass);

                fbs.getFire().collection("ratingData").add(rating).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getActivity(), "Added", Toast.LENGTH_SHORT).show();
                        GotoHomePage2();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "fail", Toast.LENGTH_SHORT).show();
                        return;

                    }
                });

            }

        });


    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment dayRate.
     */
    // TODO: Rename and change types and number of parameters
    public static dayRate newInstance(String param1, String param2) {
        dayRate fragment = new dayRate();
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
        return inflater.inflate(R.layout.fragment_day_rate, container, false);
    }

    private void GotoHomePage() {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();

        ft.setCustomAnimations(
                0,
                R.anim.exit_to_right
        );

        ft.replace(R.id.frameLayOutMain, new HomePage());
        ft.commit();
    }
    private void GotoHomePage2() {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();

        ft.setCustomAnimations(
                0,
                R.anim.slide_out_down
        );

        ft.replace(R.id.frameLayOutMain, new HomePage());
        ft.commit();
    }
    private void GotoHistory() {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayOutMain, new ratingHistory());
        ft.commit();
    }
}