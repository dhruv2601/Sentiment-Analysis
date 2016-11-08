package com.example.dhruv.sentimentanalysis;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import mehdi.sakout.fancybuttons.FancyButton;

public class ExtractConcept extends AppCompatActivity {

    public FancyButton btnURL;
    public FancyButton btnTypeText;
    public FancyButton btnStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extract_concept);

        btnURL = (FancyButton) findViewById(R.id.btn_typeURL);
        btnStorage = (FancyButton) findViewById(R.id.btn_typeFile);
        btnTypeText = (FancyButton) findViewById(R.id.btn_typeText);

        btnStorage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ExtractConcept.this, ConceptStorage.class);
                startActivity(i);
            }
        });

        btnTypeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ExtractConcept.this, ConceptText.class);
                startActivity(i);
            }
        });

        btnURL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ExtractConcept.this, conceptURL.class);
                startActivity(i);
            }
        });
    }

}
