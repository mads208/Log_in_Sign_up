package com.example.loginsignup.general;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;


import com.example.loginsignup.DailyRating.dayRate;
import com.example.loginsignup.R;
import com.example.loginsignup.journaling.journalPage;
import com.example.loginsignup.tasks.TasksPage;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomePage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomePage extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ImageView journal;
    private ImageView dailyrate;
    private ImageView schedule;
    private ImageView playlist;
    private ImageView journalIcon;





    public HomePage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomePage.
     */
    // TODO: Rename and change types and number of parameters
    public static HomePage newInstance(String param1, String param2) {
        HomePage fragment = new HomePage();
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
        return inflater.inflate(R.layout.fragment_home_page, container, false);
    }
    public void onStart() {
        super.onStart();
        journal = getView().findViewById(R.id.journaling);
        Animation rotateAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_once);

        journal.setOnClickListener(v -> {
            journal.startAnimation(rotateAnimation);

            // Wait for animation to finish before switching fragment
            rotateAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {}

                @Override
                public void onAnimationEnd(Animation animation) {
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.frameLayOutMain, new journalPage()); // Replace with your actual fragment
                    ft.commit();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {}
            });
        });
        dailyrate = getView().findViewById(R.id.DayRating);

        dailyrate.setOnClickListener(v -> {
            dailyrate.startAnimation(rotateAnimation);

            // Wait for animation to finish before switching fragment
            rotateAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {}

                @Override
                public void onAnimationEnd(Animation animation) {
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.frameLayOutMain, new dayRate()); // Replace with your target fragment
                    ft.commit();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {}
            });
        });
        schedule = getView().findViewById(R.id.schedules);

        schedule.setOnClickListener(v -> {
            schedule.startAnimation(rotateAnimation);

            // Wait for animation to finish before switching fragment
            rotateAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {}

                @Override
                public void onAnimationEnd(Animation animation) {
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.frameLayOutMain, new TasksPage()); // Replace with your target fragment
                    ft.commit();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {}
            });
        });
        playlist = getView().findViewById(R.id.playlists1);

        playlist.setOnClickListener(v -> {
            playlist.startAnimation(rotateAnimation);
            // Wait for animation to finish before switching fragment
            rotateAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {}

                @Override
                public void onAnimationEnd(Animation animation) {
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.frameLayOutMain, new LoginFragment()); // Replace with your target fragment
                    ft.commit();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {}
            });
        });



    }



    private void LoginFragment() {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayOutMain, new LoginFragment());
        ft.commit();
    }
    private void GoToTaskFragment() {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayOutMain, new TasksPage());
        ft.commit();
    }



}