package com.gmail.burakozknn.projectapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Objects;

import static com.gmail.burakozknn.projectapp.AddEditProjectActivity.EXTRA_BUDGET;
import static com.gmail.burakozknn.projectapp.AddEditProjectActivity.EXTRA_ID;
import static com.gmail.burakozknn.projectapp.AddEditProjectActivity.EXTRA_TITLE;

public class MainActivity extends AppCompatActivity {

    private ProjectViewModel projectViewModel;

    public static final int ADD_PROJECT_REQUEST = 1;

    public static final int EDIT_PROJECT_REQUEST = 2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton butttonAddProject = findViewById(R.id.button_add_project);
        butttonAddProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddEditProjectActivity.class);
                startActivityForResult(intent,ADD_PROJECT_REQUEST);

            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final ProjectAdapter adapter = new ProjectAdapter();
        recyclerView.setAdapter(adapter);

        projectViewModel = ViewModelProviders.of(this).get(ProjectViewModel.class);
        projectViewModel.getAllProjects().observe(this, new Observer<List<Project>>() {
            @Override
            public void onChanged(@Nullable List<Project> projects) {

                adapter.submitList(projects);

                //Toast.makeText(MainActivity.this, "onChanged", Toast.LENGTH_SHORT).show();

            }
        });



        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

                projectViewModel.delete(adapter.getProjectAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Project deleted", Toast.LENGTH_SHORT).show();

            }
        }).attachToRecyclerView(recyclerView);




        adapter.setOnItemClickListener(new ProjectAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Project project) {
                Intent intent = new Intent(MainActivity.this,AddEditProjectActivity.class);
                intent.putExtra(AddEditProjectActivity.EXTRA_ID,project.getId());
                intent.putExtra(AddEditProjectActivity.EXTRA_TITLE,project.getTitle());
                intent.putExtra(AddEditProjectActivity.EXTRA_DESCRIPTION,project.getDescription());
                intent.putExtra(AddEditProjectActivity.EXTRA_BUDGET,project.getPrice());
                startActivityForResult(intent,EDIT_PROJECT_REQUEST);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ( requestCode == ADD_PROJECT_REQUEST && resultCode == RESULT_OK ) {

            String title = data.getStringExtra(AddEditProjectActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditProjectActivity.EXTRA_DESCRIPTION);
            int budget = data.getIntExtra(AddEditProjectActivity.EXTRA_BUDGET,1);

            Project project = new Project(title,description,budget);
            projectViewModel.insert(project);

            String message = String.valueOf(project.getId());

            Toast.makeText(this, message+"Project uploaded", Toast.LENGTH_SHORT).show();

        } else if(requestCode == EDIT_PROJECT_REQUEST && resultCode == RESULT_OK ) {

            int id = data.getIntExtra(AddEditProjectActivity.EXTRA_ID,-1);

            if(id == -1) {
                Toast.makeText(this, "Project can't be updated!!", Toast.LENGTH_SHORT).show();
                return;
            }


            String title = data.getStringExtra(AddEditProjectActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditProjectActivity.EXTRA_DESCRIPTION);
            int budget = data.getIntExtra(AddEditProjectActivity.EXTRA_BUDGET,1);



            Project project = new Project(title,description,budget);
            project.setId(id);
            projectViewModel.update(project);

            Toast.makeText(this, "Project updated!", Toast.LENGTH_SHORT).show();

        }

        else {

            Toast.makeText(this, "Project not saved", Toast.LENGTH_SHORT).show();

        }
    }


}