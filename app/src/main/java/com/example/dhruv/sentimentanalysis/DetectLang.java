package com.example.dhruv.sentimentanalysis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import mehdi.sakout.fancybuttons.FancyButton;

public class DetectLang extends AppCompatActivity {

    public FancyButton btnURL;
    public FancyButton btnStorage;
    public FancyButton btnText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detect_lang);

        btnURL = (FancyButton) findViewById(R.id.btn_typeURL);
        btnStorage = (FancyButton) findViewById(R.id.btn_typeFile);
        btnText = (FancyButton) findViewById(R.id.btn_typeText);

        btnURL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetectLang.this,LangURL.class);
                startActivity(i);
            }
        });
    }
}