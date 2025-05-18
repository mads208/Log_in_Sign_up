package com.example.loginsignup.profile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

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

import com.example.loginsignup.R;
import com.example.loginsignup.general.HomePage;
import com.example.loginsignup.general.LoginFragment;
import com.example.loginsignup.journaling.journalHistory;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

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


    private final String[] days = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
    private final String[] genres = {"Pop", "Rock", "Hip-Hop", "Jazz", "Classical", "Electronic", "Country"};

    private Set<String> selectedDays = new HashSet<>();
    private Set<String> selectedGenres = new HashSet<>();

    private FirebaseFirestore db;
    private final String USER_ID = "defaultUser";
    private ImageView backToHomePage;
    private TextView goToLogIn;





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

        db = FirebaseFirestore.getInstance();

        setupTaskDaysCheckboxes();
        setupMusicGenresCheckboxes();

        loadProfileDataFromFirestore();

        saveButton.setOnClickListener(v -> saveProfileDataToFirestore());

        profileImage.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
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
        DocumentReference docRef = db.collection("users").document(USER_ID);
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

    private void saveProfileDataToFirestore() {
        Map<String, Object> data = new HashMap<>();
        data.put("taskDays", new ArrayList<>(selectedDays));
        data.put("musicGenres", new ArrayList<>(selectedGenres));
        data.put("goal", goalEditText.getText().toString().trim());

        db.collection("users").document(USER_ID)
                .set(data)
                .addOnSuccessListener(aVoid -> Toast.makeText(getContext(), "Profile saved!", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(getContext(), "Failed to save profile", Toast.LENGTH_SHORT).show());
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            profileImage.setImageURI(selectedImageUri);
            // Optional: upload selectedImageUri to Firebase Storage here if you want to save the photo
        }
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
