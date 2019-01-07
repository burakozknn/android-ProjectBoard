package com.gmail.burakozknn.project_board;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.gmail.burakozknn.project_board.aws.AWSLoginModel;

import static com.gmail.burakozknn.project_board.AddEditProjectActivity.EXTRA_DESCRIPTION;
import static com.gmail.burakozknn.project_board.AddEditProjectActivity.EXTRA_ID;
import static com.gmail.burakozknn.project_board.AddEditProjectActivity.EXTRA_PRICE;
import static com.gmail.burakozknn.project_board.AddEditProjectActivity.EXTRA_TITLE;
import static com.gmail.burakozknn.project_board.MainActivity.EXTRA_CONTACT;

public class DetailActivity extends AppCompatActivity {

    private TextView textViewId;
    private TextView textViewTitle;
    private TextView textViewDescription;
    private TextView textViewBudget;
    public TextView textViewContact;

    private String contactEmail, contactTitle;

    private Intent intent;
    private Intent mailIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        textViewId = findViewById(R.id.textIdDetails);
        textViewTitle = findViewById(R.id.textTitleDetails);
        textViewDescription = findViewById(R.id.textDescriptionDetails);
        textViewBudget = findViewById(R.id.textBudgetDetails);
        textViewContact = findViewById(R.id.textContactDetails);

        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        intent = getIntent();

        contactEmail = intent.getStringExtra(EXTRA_CONTACT);
        contactTitle = intent.getStringExtra(EXTRA_TITLE);

        textViewId.setText(String.valueOf(intent.getExtras().getInt(EXTRA_ID)));
        textViewTitle.setText(intent.getStringExtra(EXTRA_TITLE));
        textViewDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
        textViewBudget.setText(String.valueOf(intent.getIntExtra(EXTRA_PRICE,1)));
        textViewContact.setText(contactEmail);


        textViewContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                support(v);
            }
        });

    }


    // add intent library to send an e-mail

    public void support(View view) {

        mailIntent = new Intent(Intent.ACTION_SENDTO);
        mailIntent.setData(Uri.parse("mailto:")); // only email apps should handle this
        mailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{contactEmail});
        mailIntent.putExtra(Intent.EXTRA_SUBJECT, contactTitle);
        if (mailIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mailIntent);
        }
    }


}
