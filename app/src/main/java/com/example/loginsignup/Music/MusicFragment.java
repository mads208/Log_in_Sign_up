package com.example.loginsignup.Music;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ToggleButton;

import com.example.loginsignup.R;
import com.example.loginsignup.general.HomePage;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MusicFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MusicFragment extends Fragment {
    private MediaPlayer mediaPlayer;
    private boolean isPlaying = true;
    private ImageView backToHomePage;


    private int[] soundIds = {
            R.raw.sound1,
            R.raw.sound2,
            R.raw.sound3,
            R.raw.sound4,
            R.raw.sound5,
            R.raw.sound6
    };

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public MusicFragment() {
        // Required empty public constructor
    }

    public static MusicFragment newInstance(String param1, String param2) {
        MusicFragment fragment = new MusicFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music, container, false);

        ToggleButton toggle = view.findViewById(R.id.togglePlayStop);
        toggle.setOnClickListener(v -> {
            if (mediaPlayer != null) {
                if (isPlaying) {
                    mediaPlayer.pause();
                    isPlaying = false;
                } else {
                    mediaPlayer.start();
                    isPlaying = true;
                }
            }
        });

        int[] buttonIds = {
                R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6
        };

        List<Button> allButtons = new ArrayList<>();

        for (int i = 0; i < buttonIds.length; i++) {
            final int index = i;
            Button btn = view.findViewById(buttonIds[i]);
            btn.setOnClickListener(v -> playSound(index));
            allButtons.add(btn); // <-- added for search bar logic
        }

        EditText searchBar = view.findViewById(R.id.search_bar);
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = s.toString().trim();
                for (Button btn : allButtons) {
                    if (query.isEmpty() || btn.getText().toString().contains(query)) {
                        btn.setVisibility(View.VISIBLE);
                    } else {
                        btn.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        backToHomePage = getView().findViewById(R.id.backToHomePageMusic);
        backToHomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GotoHomePage();
            }
        });
    }

    private void playSound(int index) {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        mediaPlayer = MediaPlayer.create(getContext(), soundIds[index]);
        mediaPlayer.start();
        isPlaying = true;
    }

    @Override
    public void onDestroy() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        super.onDestroy();
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
}
