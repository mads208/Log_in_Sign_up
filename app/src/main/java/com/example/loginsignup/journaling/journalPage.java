package com.example.loginsignup.journaling;

import android.icu.text.SimpleDateFormat;
import android.icu.util.TimeZone;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginsignup.DailyRating.Rating;
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
 * Use the {@link journalPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class journalPage extends Fragment {

    private ImageView backToHomePage;
    private TextView Addbtn;
    private FirebaseServices fbs;
    private ImageView history;
    private EditText TitleET;
    private EditText TextET;




    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public journalPage() {
        // Required empty public constructor
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        connectComponents(view);
    }

    private void connectComponents(View view) {
        fbs = FirebaseServices.getInstance();
        backToHomePage = view.findViewById(R.id.backToHomePageJournal);
        backToHomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GotoHomePage();
            }
        });
        history = view.findViewById(R.id.historyJournal);
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GotoHistory();
            }
        });

        TitleET = view.findViewById(R.id.Title);
        TextET = view.findViewById(R.id.typingBox);

        Addbtn = view.findViewById(R.id.doneJournal);
        Addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Title2,Text2,createdDateJournal;
                Title2 = TitleET.getText().toString();
                Text2 = TextET.getText().toString();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                sdf.setTimeZone(TimeZone.getTimeZone("Asia/Jerusalem"));
                createdDateJournal = sdf.format(new Date());

                if( Title2.trim().isEmpty() || Text2.trim().isEmpty() || createdDateJournal.trim().isEmpty() ){
                    Toast.makeText(getActivity(), "some fields are empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                Journal journal = new Journal(Title2,Text2,createdDateJournal);

                //fbs.getAuth().createUserWithEmailAndPassword(user, pass);

                fbs.getFire().collection("journalData").add(journal).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getActivity(), "Added", Toast.LENGTH_SHORT).show();
                        GotoHomePage();

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
     * @return A new instance of fragment journalPage.
     */
    // TODO: Rename and change types and number of parameters
    public static journalPage newInstance(String param1, String param2) {
        journalPage fragment = new journalPage();
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
        return inflater.inflate(R.layout.fragment_journal_page, container, false);
    }

    private void GotoHomePage() {
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
        ft.replace(R.id.frameLayOutMain, new journalHistory());
        ft.commit();
    }

}