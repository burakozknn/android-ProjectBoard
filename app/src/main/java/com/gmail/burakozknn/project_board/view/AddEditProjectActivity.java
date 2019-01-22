package com.gmail.burakozknn.project_board.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amazonaws.amplify.generated.graphql.CreateProjectMutation;
import com.apollographql.apollo.GraphQLCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.gmail.burakozknn.project_board.R;
import com.gmail.burakozknn.project_board.aws.ClientFactory;

import javax.annotation.Nonnull;

import type.CreateProjectInput;

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
    private Button postButton;

    private String title, description;
    private int price;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        postButton=findViewById(R.id.button_post_project);
        editTextTitle = findViewById(R.id.edit_text_title);
        editTextDescription = findViewById(R.id.edit_text_description);
        editTextPrice = findViewById(R.id.edit_text_price);

        //Post button onClick
        PostButtonOnClick();

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
        title = editTextTitle.getText().toString();
        description = editTextDescription.getText().toString();
        price = Integer.parseInt(editTextPrice.getText().toString());

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

    private void PostButtonOnClick() {
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProject();
            }
        });
    }


}
