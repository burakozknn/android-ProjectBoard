package com.gmail.burakozknn.projectapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ProjectViewModel projectViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final ProjectAdapter adapter = new ProjectAdapter();
        recyclerView.setAdapter(adapter);

        projectViewModel = ViewModelProviders.of(this).get(ProjectViewModel.class);
        projectViewModel.getAllProjects().observe(this, new Observer<List<Project>>() {
            @Override
            public void onChanged(@Nullable List<Project> projects) {

                adapter.setProjects(projects);

                //Toast.makeText(MainActivity.this, "onChanged", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
