package com.example.loginsignup.profile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.example.loginsignup.R;
import com.example.loginsignup.general.HomePage;
import com.example.loginsignup.general.LoginFragment;
import com.example.loginsignup.journaling.journalHistory;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class ProfileFragment extends Fragment {

    private ImageView profileImage;
    private LinearLayout taskDaysContainer, musicGenresContainer;
    private EditText goalEditText;
    private Button saveButton;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri selectedImageUri;
    private ActivityResultLauncher<String> imagePickerLauncher;



    private final String[] days = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
    private final String[] genres = {"Pop", "Rock", "Hip-Hop", "Jazz", "Classical", "Electronic", "Country"};

    private Set<String> selectedDays = new HashSet<>();
    private Set<String> selectedGenres = new HashSet<>();

    private FirebaseFirestore fbs;
    private String USER_ID;
    private ImageView backToHomePage;
    private TextView goToLogIn;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseStorage = FirebaseStorage.getInstance();

        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                uri -> {
                    if (uri != null) {
                        selectedImageUri = uri;
                        if (profileImage != null) {
                            profileImage.setImageURI(uri);
                        }

                        String fileName = "profile_images/" + USER_ID + ".jpg";
                        StorageReference storageRef = firebaseStorage.getReference().child(fileName);
                        storageRef.putFile(uri)
                                .addOnSuccessListener(taskSnapshot -> {
                                    storageRef.getDownloadUrl().addOnSuccessListener(downloadUri -> {
                                        uploadedImageUrl = downloadUri.toString(); // Save URL to a class variable
                                        saveProfileDataToFirestore(); // Save immediately after upload
                                        Toast.makeText(getContext(), "Image uploaded and saved!", Toast.LENGTH_SHORT).show();
                                    });
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(getContext(), "Upload failed", Toast.LENGTH_SHORT).show();
                                });

                    }
                }
        );
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        backToHomePage = root.findViewById(R.id.backToHomePageProfile);
        backToHomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GotoHomePage();
            }
        });
        goToLogIn = root.findViewById(R.id.LogOutProfile);
        goToLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GotoLogin();
            }
        });
        profileImage = root.findViewById(R.id.profileImage);
        taskDaysContainer = root.findViewById(R.id.taskDaysContainer);
        musicGenresContainer = root.findViewById(R.id.musicGenresContainer);
        goalEditText = root.findViewById(R.id.goalEditText);
        saveButton = root.findViewById(R.id.saveButton);

        fbs = FirebaseFirestore.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            USER_ID = currentUser.getUid();
            loadProfileDataFromFirestore(); // Only call this if we have a valid user
        } else {
            Toast.makeText(getContext(), "User not logged in", Toast.LENGTH_SHORT).show();
        }


        setupTaskDaysCheckboxes();
        setupMusicGenresCheckboxes();


        saveButton.setOnClickListener(v -> saveProfileDataToFirestore());

        profileImage.setOnClickListener(v -> {
            imagePickerLauncher.launch("image/*");
        });


        return root;
    }

    private void setupTaskDaysCheckboxes() {
        taskDaysContainer.removeAllViews();

        LinearLayout row1 = createHorizontalRow();
        LinearLayout row2 = createHorizontalRow();

        for (int i = 0; i < days.length; i++) {
            CheckBox cb = new CheckBox(getContext());
            cb.setText(days[i]);
            cb.setAllCaps(false);
            cb.setLayoutParams(new LinearLayout.LayoutParams(0,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 1f));

            final String day = days[i];
            cb.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    selectedDays.add(day);
                } else {
                    selectedDays.remove(day);
                }
            });

            if (i < 4) row1.addView(cb);
            else row2.addView(cb);
        }

        taskDaysContainer.addView(row1);
        taskDaysContainer.addView(row2);
    }

    private void setupMusicGenresCheckboxes() {
        musicGenresContainer.removeAllViews();

        LinearLayout row1 = createHorizontalRow();
        LinearLayout row2 = createHorizontalRow();

        for (int i = 0; i < genres.length; i++) {
            CheckBox cb = new CheckBox(getContext());
            cb.setText(genres[i]);
            cb.setAllCaps(false);
            cb.setLayoutParams(new LinearLayout.LayoutParams(0,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 1f));

            final String genre = genres[i];
            cb.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    selectedGenres.add(genre);
                } else {
                    selectedGenres.remove(genre);
                }
            });

            if (i < 4) row1.addView(cb);
            else row2.addView(cb);
        }

        musicGenresContainer.addView(row1);
        musicGenresContainer.addView(row2);
    }

    private LinearLayout createHorizontalRow() {
        LinearLayout row = new LinearLayout(getContext());
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        return row;
    }

    private void loadProfileDataFromFirestore() {
        DocumentReference docRef = fbs.collection("users2").document(USER_ID);
        docRef.get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        Map<String, Object> data = documentSnapshot.getData();
                        if (data != null) {
                            // Load days
                            ArrayList<String> daysFromDb = (ArrayList<String>) data.get("taskDays");
                            if (daysFromDb != null) {
                                selectedDays.clear();
                                selectedDays.addAll(daysFromDb);
                                updateTaskDaysUI();
                            }

                            // Load goal
                            String goal = (String) data.get("goal");
                            if (goal != null) {
                                goalEditText.setText(goal);
                            }

                            // Load genres
                            ArrayList<String> genresFromDb = (ArrayList<String>) data.get("musicGenres");
                            if (genresFromDb != null) {
                                selectedGenres.clear();
                                selectedGenres.addAll(genresFromDb);
                                updateMusicGenresUI();
                            }
                            String imageUrl = (String) data.get("profileImageUrl");
                            if (imageUrl != null && getContext() != null) {
                                uploadedImageUrl = imageUrl;
                                Glide.with(getContext())
                                        .load(imageUrl)
                                        .into(profileImage);
                            }


                        }
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(getContext(), "Failed to load profile data", Toast.LENGTH_SHORT).show());
    }

    private void updateTaskDaysUI() {
        for (int i = 0; i < taskDaysContainer.getChildCount(); i++) {
            LinearLayout row = (LinearLayout) taskDaysContainer.getChildAt(i);
            for (int j = 0; j < row.getChildCount(); j++) {
                View v = row.getChildAt(j);
                if (v instanceof CheckBox) {
                    CheckBox cb = (CheckBox) v;
                    cb.setChecked(selectedDays.contains(cb.getText().toString()));
                }
            }
        }
    }

    private void updateMusicGenresUI() {
        for (int i = 0; i < musicGenresContainer.getChildCount(); i++) {
            LinearLayout row = (LinearLayout) musicGenresContainer.getChildAt(i);
            for (int j = 0; j < row.getChildCount(); j++) {
                View v = row.getChildAt(j);
                if (v instanceof CheckBox) {
                    CheckBox cb = (CheckBox) v;
                    cb.setChecked(selectedGenres.contains(cb.getText().toString()));
                }
            }
        }
    }

    private String uploadedImageUrl = null; // add this as a class member variable

    private void saveProfileDataToFirestore() {
        Map<String, Object> data = new HashMap<>();
        data.put("taskDays", new ArrayList<>(selectedDays));
        data.put("musicGenres", new ArrayList<>(selectedGenres));
        data.put("goal", goalEditText.getText().toString().trim());

        if (uploadedImageUrl != null) {
            data.put("profileImageUrl", uploadedImageUrl);
        }

        fbs.collection("users2").document(USER_ID)
                .set(data)
                .addOnSuccessListener(aVoid ->
                        Toast.makeText(getContext(), "Profile saved!", Toast.LENGTH_SHORT).show()
                )
                .addOnFailureListener(e ->
                        Toast.makeText(getContext(), "Failed to save profile", Toast.LENGTH_SHORT).show()
                );
    }



    private void GotoHomePage() {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayOutMain, new HomePage());
        ft.commit();
    }
    private void GotoLogin() {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayOutMain, new LoginFragment());
        ft.commit();
    }


}