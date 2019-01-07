package com.gmail.burakozknn.projectapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import static java.lang.Integer.parseInt;

public class AddEditProjectActivity extends AppCompatActivity {

    public static final String EXTRA_ID =
            "com.gmail.burakozknn.projectapp.EXTRA_ID";

    public static final String EXTRA_TITLE =
            "com.gmail.burakozknn.projectapp.EXTRA_TITLE";

    public static final String EXTRA_DESCRIPTION =
            "com.gmail.burakozknn.projectapp.EXTRA_DESCRIPTION";

    public static final String EXTRA_BUDGET =
            "com.gmail.burakozknn.projectapp.EXTRA_BUDGET";

    private EditText editTextTitle;
    private EditText editTextDescription;
    private EditText editTextBudget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);

        editTextTitle = findViewById(R.id.edit_text_title);
        editTextDescription = findViewById(R.id.edit_text_description);
        editTextBudget = findViewById(R.id.edit_text_budget);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();

        if(intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Project");
            editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            editTextDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            editTextBudget.setText(String.valueOf(intent.getIntExtra(EXTRA_BUDGET, 1)));

        }
        else {
            setTitle("Add Project");
        }


    }

    private void saveProject() {
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        int budget = Integer.parseInt(editTextBudget.getText().toString());

        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(this, "Please insert title and description", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE,title);
        data.putExtra(EXTRA_DESCRIPTION,description);
        data.putExtra(EXTRA_BUDGET,budget);

        int id = getIntent().getIntExtra(EXTRA_ID,-1);

        if(id != -1) {
            data.putExtra(EXTRA_ID,-1);
        }

        setResult(RESULT_OK,data);

        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_project_menu,menu);
        return true;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_project:
                saveProject();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
