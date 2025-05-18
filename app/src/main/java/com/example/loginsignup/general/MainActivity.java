package com.example.loginsignup.general;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginsignup.Music.MusicFragment;
import com.example.loginsignup.R;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private String[] myDataSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayOutMain, new MusicFragment())
                .commit();


    }

    @Override
    protected void onStart() {
        super.onStart();

        //ayoutManager = new LinearLayoutManager(this);
        //recyclerView.setLayoutManager(layoutManager);
        //mAdapter = new MyAdapter(myDataSet);
        //recyclerView.setAdapter(mAdapter);
        gotoLoginFragment();

    }

    private void gotoLoginFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayOutMain, new LoginFragment());
        ft.commit();
    }

}