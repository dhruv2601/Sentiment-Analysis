package com.example.dhruv.sentimentanalysis;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.refactor.typer.TyperEditText;
import mehdi.sakout.fancybuttons.FancyButton;

public class conceptURL extends AppCompatActivity {

    private static final String TAG = "conceptURL";
    public FancyButton btnGetConcept;
    public TyperEditText edtInput;
    public String inputText = "";
    public RelativeLayout conceptLayout;
    public String jsonStr;
    public String conceptsFound[]=new String[6];
    public TextView tv1;
    public TextView tv2;
    public TextView tv3;
    public TextView tv4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concept_url);

        btnGetConcept = (FancyButton) findViewById(R.id.btn_getConcept);
        edtInput = (TyperEditText) findViewById(R.id.edt_input);
        conceptLayout = (RelativeLayout) findViewById(R.id.activity_concept_text);
        tv1 = (TextView) findViewById(R.id.res1);
        tv2 = (TextView) findViewById(R.id.res2);
        tv3 = (TextView) findViewById(R.id.res3);
        tv4 = (TextView) findViewById(R.id.res4);

        ConnectivityManager cm = (ConnectivityManager) conceptURL.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo state = cm.getActiveNetworkInfo();
        final boolean isConnected = state != null && state.isConnectedOrConnecting();

        btnGetConcept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnected == false) {
                    Toast.makeText(conceptURL.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                } else {
                    inputText = edtInput.getText().toString();
                    Log.d(TAG,"inputText:: "+inputText);
//                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                    imm.hideSoftInputFromWindow(conceptLayout.getWindowToken(), 0);
                    new getConcept().execute();
                }
            }
        });
    }
    private class getConcept extends AsyncTask<Void, Void, Void> {
        HttpHandler sh = new HttpHandler();
        String reqUrl;

        @Override
        protected Void doInBackground(Void... params) {
            reqUrl = ("https://api.havenondemand.com/1/api/sync/extractconcepts/v1?url=" + inputText + "&apikey=56985acd-2182-4468-994c-3bfcec560b30");
//            reqUrl = ("https://api.havenondemand.com/1/api/sync/extractconcepts/v1?url=https://en.wikipedia.org/wiki/United_Kingdom&apikey=56985acd-2182-4468-994c-3bfcec560b30");
            jsonStr = sh.makeServiceCall(reqUrl);

            Log.d(TAG, "jsonConcept" + jsonStr);
            try {
                JSONObject jsonObject = new JSONObject(jsonStr);
                JSONArray concept = jsonObject.getJSONArray("concepts");
                for(int i=0;i<5;i++)
                {
                    JSONObject temp = concept.getJSONObject(i);
                    conceptsFound[i] = temp.getString("concept");
                    Log.d(TAG,"finally::: "+temp.getString("concept"));
                    Log.d(TAG,"temp111:::: "+temp.toString());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            tv1.setText(1+")    "+conceptsFound[0].toUpperCase());
            tv2.setText(2+")    "+conceptsFound[1].toUpperCase());
            tv3.setText(3+")    "+conceptsFound[2].toUpperCase());
            tv4.setText(4+")    "+conceptsFound[3].toUpperCase());
            super.onPostExecute(aVoid);
        }
    }
}
