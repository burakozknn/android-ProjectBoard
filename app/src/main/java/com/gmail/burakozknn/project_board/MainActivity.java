package com.gmail.burakozknn.project_board;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.config.AWSConfiguration;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;


import java.util.Random;

import com.gmail.burakozknn.project_board.aws.AWSLoginModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_CONTACT =
            "com.gmail.burakozknn.project_board.EXTRA_CONTACT";


    // Declare a DynamoDBMapper object
    DynamoDBMapper dynamoDBMapper;

    private ProjectViewModel projectViewModel;

    private RecyclerView recyclerView;

    private ProjectAdapter adapter;

    private FloatingActionButton buttonAddProject;

    //private Project project;

    public static final int ADD_PROJECT_REQUEST = 1;

    public static final int EDIT_PROJECT_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        buttonAddProject = findViewById(R.id.button_add_project);
        buttonAddProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, AddEditProjectActivity.class);
                startActivityForResult(intent, ADD_PROJECT_REQUEST);

            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        adapter = new ProjectAdapter();
        recyclerView.setAdapter(adapter);

        projectViewModel = ViewModelProviders.of(this).get(ProjectViewModel.class);
        projectViewModel.getAllProjects().observe(this, new Observer<List<Project>>() {
            @Override
            public void onChanged(@Nullable List<Project> projects) {
                adapter.submitList(projects);

            }
        });

        SwipeHelper swipeHelper = new SwipeHelper(this, recyclerView) {
            @Override
            public void instantiateUnderlayButton(final RecyclerView.ViewHolder viewHolder, List<UnderlayButton> underlayButtons) {
                underlayButtons.add(new SwipeHelper.UnderlayButton(
                        "Delete",
                        0,
                        Color.parseColor("#FF3C30"),
                        new SwipeHelper.UnderlayButtonClickListener() {
                            @Override
                            public void onClick(int pos) {
                                // TODO: onDelete
                                projectViewModel.delete(adapter.getProjectAt(viewHolder.getAdapterPosition()));
                            }
                        }
                ));

                underlayButtons.add(new SwipeHelper.UnderlayButton(
                        "Edit",
                        0,
                        Color.parseColor("#FF9502"),
                        new SwipeHelper.UnderlayButtonClickListener() {
                            @Override
                            public void onClick(int pos) {
                                // TODO: OnEdit
                                Project project = adapter.getProjectAt(pos);
                                //Project project = adapter.getProjectAt(viewHolder.getAdapterPosition());
                                Intent intent = new Intent(MainActivity.this, AddEditProjectActivity.class);

                                intent.putExtra(AddEditProjectActivity.EXTRA_ID, project.getId());
                                intent.putExtra(AddEditProjectActivity.EXTRA_TITLE, project.getTitle());
                                intent.putExtra(AddEditProjectActivity.EXTRA_DESCRIPTION, project.getDescription());
                                intent.putExtra(AddEditProjectActivity.EXTRA_PRICE, project.getPrice());
                                startActivityForResult(intent, EDIT_PROJECT_REQUEST);
                            }
                        }
                ));

            }
        };

        final String savedUserEmail = AWSLoginModel.getSavedUserEmail(this);


        adapter.setOnItemClickListener(new ProjectAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Project project) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra(AddEditProjectActivity.EXTRA_ID, project.getId());
                intent.putExtra(AddEditProjectActivity.EXTRA_TITLE, project.getTitle());
                intent.putExtra(AddEditProjectActivity.EXTRA_DESCRIPTION, project.getDescription());
                intent.putExtra(AddEditProjectActivity.EXTRA_PRICE, project.getPrice());
                intent.putExtra(MainActivity.EXTRA_CONTACT,savedUserEmail);
                startActivity(intent);
            }
        });

        // AWSMobileClient enables AWS user credentials to access your table
        AWSMobileClient.getInstance().initialize(this).execute();

        AWSCredentialsProvider credentialsProvider = AWSMobileClient.getInstance().getCredentialsProvider();
        AWSConfiguration configuration = AWSMobileClient.getInstance().getConfiguration();


        // Add code to instantiate a AmazonDynamoDBClient
        AmazonDynamoDBClient dynamoDBClient = new AmazonDynamoDBClient(credentialsProvider);

        this.dynamoDBMapper = DynamoDBMapper.builder()
                .dynamoDBClient(dynamoDBClient)
                .awsConfiguration(configuration)
                .build();

        // other activity code ...


    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_PROJECT_REQUEST && resultCode == RESULT_OK) {

            String title = data.getStringExtra(AddEditProjectActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditProjectActivity.EXTRA_DESCRIPTION);
            int price = data.getIntExtra(AddEditProjectActivity.EXTRA_PRICE, 1);

            Project project = new Project(title, description, price);
            projectViewModel.insert(project);

            Toast.makeText(this, "Project saved", Toast.LENGTH_SHORT).show();

        } else if (requestCode == EDIT_PROJECT_REQUEST && resultCode == RESULT_OK) {

            int id = data.getIntExtra(AddEditProjectActivity.EXTRA_ID, -1);

            if (id == -1) {
                Toast.makeText(this, "Project can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String title = data.getStringExtra(AddEditProjectActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditProjectActivity.EXTRA_DESCRIPTION);
            int price = data.getIntExtra(AddEditProjectActivity.EXTRA_PRICE, 1);

            Project project = new Project(title, description, price);
            project.setId(id);
            projectViewModel.update(project);

            Toast.makeText(this, "Project updated", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Project not saved", Toast.LENGTH_SHORT).show();
        }
    }

}

//Swipe to delete
        /*
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                projectViewModel.delete(adapter.getProjectAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Project deleted!", Toast.LENGTH_SHORT).show();

            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new ProjectAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Project project) {
                Intent intent = new Intent(MainActivity.this, AddEditProjectActivity.class);
                intent.putExtra(AddEditProjectActivity.EXTRA_ID, project.getId());
                intent.putExtra(AddEditProjectActivity.EXTRA_TITLE, project.getTitle());
                intent.putExtra(AddEditProjectActivity.EXTRA_DESCRIPTION, project.getDescription());
                intent.putExtra(AddEditProjectActivity.EXTRA_PRICE, project.getPrice());
                startActivityForResult(intent, EDIT_PROJECT_REQUEST);
            }
        });

        @Override
    protected void onResume() {
        super.onResume();

        String who = AWSLoginModel.getSavedUserName(MainActivity.this);

        TextView hello = findViewById(R.id.hello);
        hello.setText("Hello " + who + "!");
    }

        */
