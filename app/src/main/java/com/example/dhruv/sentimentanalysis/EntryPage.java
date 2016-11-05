package com.example.dhruv.sentimentanalysis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import mehdi.sakout.fancybuttons.FancyButton;

public class EntryPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        FancyButton sentiment = (FancyButton) findViewById(R.id.btn_sentiment);
        FancyButton concept = (FancyButton) findViewById(R.id.btn_extractConcept);

        sentiment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EntryPage.this, MainActivity.class);
                startActivity(i);
            }
        });

        concept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EntryPage.this, ExtractConcept.class);
                startActivity(i);
            }
        });

    }
}
