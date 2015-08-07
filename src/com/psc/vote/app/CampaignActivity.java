package com.psc.vote.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class CampaignActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("SignInPageActivity", "inside user campaign page");
        setContentView(R.layout.campaign);
        Intent intent = getIntent();
        String campaignId = intent.getStringExtra("campaignId");
        openChart(campaignId);
    }

    private void openChart(String campaignId) {
    	GraphicalView mChartView;
        String response = "";
        ArrayList<String> ageRange = new ArrayList<String>();
        ArrayList<Integer> countValue = new ArrayList<Integer>();
        TextView anchor = (TextView) findViewById(R.id.textView1);
        TextView client = (TextView) findViewById(R.id.client);
        

        int count = 0;
        Log.i("displayCampaign:", "displayCampaign");
        final ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
        postParameters.add(new BasicNameValuePair("campaignId", campaignId));

        try {
            response = SimpleHttpClient.executeHttpPost("/displayStats", postParameters);
            Log.i("Response:", response);
            JSONObject jsonobject = new JSONObject(response);
           String anchorn=  jsonobject.getString("anchor_id");
           String clname=  jsonobject.getString("client_name");
           anchor.setText(anchorn);
           client.setText(clname);
            String Value = jsonobject.getString("values");
            JSONArray jsonArray2 = new JSONArray(Value);
            for (int j = 0; j < jsonArray2.length(); j++) {
                Log.i("inside", "inside campaign");
                jsonobject = jsonArray2.getJSONObject(j);
                Log.i("campaign ", "json object built");
                String ager = jsonobject.getString("age");
                Log.i("agerane", ager);
                String cntValue = jsonobject.getString("cnt_value");
                Log.i("client id", cntValue);
                count = Integer.parseInt(cntValue);
                ageRange.add(ager);
                countValue.add(count);
            }
        } catch (Exception e) {
            Log.i("Response 2:Error:", e.getMessage());
        }
        // Pie Chart Section Names
        // Color of each Pie Chart Sections
        int[] colors = {Color.BLUE, Color.MAGENTA, Color.GREEN, Color.CYAN, Color.RED, Color.YELLOW};

        // Instantiating CategorySeries to plot Pie Chart
        CategorySeries distributionSeries = new CategorySeries("Campaign Statistics");
        for (Integer i = 0; i < countValue.size(); i++) {
            // Adding a slice with its values and name to the Pie Chart
            distributionSeries.add(ageRange.get(i), countValue.get(i));
        }

        // Instantiating a renderer for the Pie Chart
        DefaultRenderer defaultRenderer = new DefaultRenderer();
        for (Integer i = 0; i < countValue.size(); i++) {
            SimpleSeriesRenderer seriesRenderer = new SimpleSeriesRenderer();
            seriesRenderer.setColor(colors[i]);
            seriesRenderer.setDisplayChartValues(true);
            // Adding a renderer for a slice
            defaultRenderer.addSeriesRenderer(seriesRenderer);
        }

        defaultRenderer.setChartTitle("Campaign Statistics");
        defaultRenderer.setChartTitleTextSize(10);
        defaultRenderer.setZoomButtonsVisible(false);
        LinearLayout layout = (LinearLayout) findViewById(R.id.chart);
       
        
        // Creating an intent to plot bar chart using dataset and multipleRenderer
        mChartView = ChartFactory.getPieChartView(this, distributionSeries, defaultRenderer);
        layout.addView(mChartView);
        // Start Activity
       // startActivity(intent);
    }
}