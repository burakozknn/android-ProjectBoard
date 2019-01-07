package com.gmail.burakozknn.project_board;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

public class AddEditProjectActivity extends AppCompatActivity {

    public static final String EXTRA_ID =
            "com.gmail.burakozknn.project_board.EXTRA_ID";

    public static final String EXTRA_TITLE =
            "com.gmail.burakozknn.project_board.EXTRA_TITLE";

    public static final String EXTRA_DESCRIPTION =
            "com.gmail.burakozknn.project_board.EXTRA_DESCRIPTION";

    public static final String EXTRA_PRICE =
            "com.gmail.burakozknn.project_board.EXTRA_PRICE";

    private EditText editTextTitle;
    private EditText editTextDescription;
    private EditText editTextPrice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);


        editTextTitle = findViewById(R.id.edit_text_title);
        editTextDescription = findViewById(R.id.edit_text_description);
        editTextPrice = findViewById(R.id.edit_text_price);


        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Project");
            editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            editTextDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            editTextPrice.setText(String.valueOf(intent.getIntExtra(EXTRA_PRICE,1)));
        } else {
            setTitle("Add Project");
        }


    }

    private void saveProject() {
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        int price = Integer.parseInt(editTextPrice.getText().toString());


        if (title.equals("") || description.equals("") ) {
            Toast.makeText(this, "Please insert a title and description !", Toast.LENGTH_SHORT).show();
            return;
        }



        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DESCRIPTION, description);
        data.putExtra(EXTRA_PRICE, price);

        int id = getIntent().getIntExtra(EXTRA_ID,-1);

        if(id != -1) {
            data.putExtra(EXTRA_ID,id);
        }

        setResult(RESULT_OK, data);
        finish();

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent( event );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_project_menu, menu);
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
