package com.example.dhruv.sentimentanalysis;

import android.content.Context;
import android.graphics.Color;
import android.hardware.input.InputManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cardiomood.android.controls.gauge.SpeedometerGauge;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

import mehdi.sakout.fancybuttons.FancyButton;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainAct";
    public String jsonStr;
    public String inputText = "";
    public double score=0;
    private SpeedometerGauge speedometer;
    public LinearLayout rl;
    public TextView sentiment;
    public TextView dispScore;
    public RelativeLayout mainActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        rl = (LinearLayout) findViewById(R.id.linear_layout1);
        FancyButton btnGetData = (FancyButton) findViewById(R.id.btn_getData);
        sentiment = (TextView) findViewById(R.id.sentiment);
        dispScore = (TextView) findViewById(R.id.dispScore);
        mainActivity = (RelativeLayout) findViewById(R.id.activity_main);

        final EditText edtInput = (EditText) findViewById(R.id.edt_input);

        speedometer = (SpeedometerGauge) findViewById(R.id.speedometer);
//        speedometer.setBackgroundResource(R.drawable.images);              // change the color of the one changed
        speedometer.setLabelConverter(new SpeedometerGauge.LabelConverter() {
            @Override
            public String getLabelFor(double progress, double maxProgress) {
                return String.valueOf((int) Math.round(progress));
            }
        });

        speedometer.setSpeed(score, 2000, 500);
        speedometer.setLabelTextSize(25);

        // configure value range and ticks
        speedometer.setMaxSpeed(100);
        speedometer.setMajorTickStep(10);
        speedometer.setMinorTicks(2);

        // Configure value range colors
        speedometer.addColoredRange(0, 200, Color.green(300));

//        speedometer.addColoredRange(16, 40, R.color.lightGreen);
//        speedometer.addColoredRange(40, 80, R.color.semiLightGreen);
        speedometer.addColoredRange(0, 200, R.color.darkGreen);


//        drawView = new DrawView(this);
//        setContentView(drawView);
        btnGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputText1 = edtInput.getText().toString();

                for (int i = 0; i < inputText1.length(); i++) {
                    if (inputText1.charAt(i) == ' ') {
                        inputText += '+';
                    } else {
                        inputText += inputText1.charAt(i);
                    }
                }
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mainActivity.getWindowToken(), 0);

                inputText += '+';
                Log.d(TAG, "inputTxt" + inputText);
                new getData().execute();
                Log.d(TAG, "jsonStr " + jsonStr);
            }
        });
    }

    private class getData extends AsyncTask<Void, Void, Void> {
        HttpHandler sh = new HttpHandler();
        String reqUrl;

        @Override
        protected Void doInBackground(Void... params) {

            Log.d(TAG, "inTEXT::: " + inputText);
            reqUrl = ("https://api.havenondemand.com/1/api/sync/analyzesentiment/v2?text=" + inputText + "&language=eng&apikey=56985acd-2182-4468-994c-3bfcec560b30");
            jsonStr = sh.makeServiceCall(reqUrl);
            inputText = "";
            try {
                JSONObject jsonObject = new JSONObject(jsonStr);
                JSONArray analysis = jsonObject.getJSONArray("sentiment_analysis");
                JSONObject aggreagate = analysis.getJSONObject(0).getJSONObject("aggregate");
                score = aggreagate.getDouble("score");
                String sentiment = aggreagate.getString("sentiment");
                Log.d(TAG, "analysis: " + analysis);
                Log.d(TAG, "aggregate: " + aggreagate);
                Log.d(TAG, "score: " + score);
                Log.d(TAG, "sentiment: " + sentiment);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            score = score*100;

            if(score>=0)
            {
                rl.setBackgroundResource(R.drawable.green);
                if(score==0)
                {
                    sentiment.setText("Neutral");
                }
                else
                {
                    sentiment.setText("Positive");
                }
                dispScore.setText(""+(int)score);
//                speedometer.setBackgroundResource(R.drawable.images);
            }

            else
            {
                rl.setBackgroundResource(R.drawable.red);
                sentiment.setText("Negative");
                dispScore.setText(""+(int)score);
//                speedometer.setBackgroundResource(R.drawable.redback);
            }

            if(score<0)
            {
                score*=(-1);
            }
            speedometer.setSpeed(score, 2000, 300);
        }
    }
}