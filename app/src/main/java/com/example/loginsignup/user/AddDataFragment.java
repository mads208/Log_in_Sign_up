package com.example.loginsignup.user;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginsignup.general.FirebaseServices;
import com.example.loginsignup.general.HomePage;
import com.example.loginsignup.general.LoginFragment;
import com.example.loginsignup.R;
import com.example.loginsignup.general.Utils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddDataFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddDataFragment extends Fragment {
    private static final int GALLARY_REQUEST_CODE = 100;

    private EditText etMusicGenre;
    private EditText etGoal;
    private EditText etTaskDays;
    private Button Addbtn;
    private FirebaseServices fbs;
    private TextView GoToAllData;
    private TextView GoToHome;
    private ImageView img;
    private String imageStr;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddDataFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart(){
        super.onStart();
        connectComponents();
    }

    private void connectComponents() {

        etMusicGenre = getView().findViewById(R.id.etMusicGenreAdd);
        etGoal = getView().findViewById(R.id.etGoalAdd);
        etTaskDays = getView().findViewById(R.id.etTaskDaysAdd);
        fbs =  FirebaseServices.getInstance();
        img = getView().findViewById(R.id.imageViewProfile);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallerIntent =new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(gallerIntent,GALLARY_REQUEST_CODE);

            }
        });


        /*img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri selectedImageUri=fbs.getSelectedImageURL();
                String imageUri = "";
                if(selectedImageUri!=null)
                    imageUri =selectedImageUri.toString();
            }
        });*/
        Addbtn = getView().findViewById(R.id.AddAddbtn);
        Addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String MusicGenre,Goal,TaskDays,photo;
                TaskDays = etTaskDays.getText().toString();
                Goal = etGoal.getText().toString();
                MusicGenre = etMusicGenre.getText().toString();
                Uri selectedImageUri=fbs.getSelectedImageURL();
                String imageUri = "";
                if(selectedImageUri!=null) {
                    imageUri = selectedImageUri.toString();
                }


                if( TaskDays.trim().isEmpty() || Goal.trim().isEmpty() || MusicGenre.trim().isEmpty() ){
                    Toast.makeText(getActivity(), "some fields are empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                DataUser userdata = new DataUser(TaskDays,Goal,MusicGenre, imageUri.toString() );

                //fbs.getAuth().createUserWithEmailAndPassword(user, pass);

                fbs.getFire().collection("data").add(userdata).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getActivity(), "welcome", Toast.LENGTH_SHORT).show();

                        gotoAllData();
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
        GoToAllData = getView().findViewById(R.id.GoToAllDataAddData);
        GoToAllData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoAllData();

            }
        });
        GoToHome = getView().findViewById(R.id.gotoHomeAddData);
        GoToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GotoHomePage();

            }
        });


    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddDataFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddDataFragment newInstance(String param1, String param2) {
        AddDataFragment fragment = new AddDataFragment();
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
        return inflater.inflate(R.layout.fragment_add_data, container, false);
    }
    private void gotoAllData() {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayOutMain, new AllDataFragment());
        ft.commit();
    }
    private void GotoLogin() {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayOutMain, new LoginFragment());
        ft.commit();
    }
    private void GotoHomePage() {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayOutMain, new HomePage());
        ft.commit();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLARY_REQUEST_CODE && resultCode == getActivity().RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            img.setImageURI(selectedImageUri);
            Utils.getInstance().uploadImage(getActivity(), selectedImageUri);
        }
    }



}