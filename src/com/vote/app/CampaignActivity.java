package com.vote.app;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
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

	String anchorName;
	String clientName;
	String username;
	String campaignId;
	String readOnly;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.campaign);
		Intent intent = getIntent();
		anchorName = intent.getStringExtra("anchorName");
		clientName = intent.getStringExtra("clientName");
		campaignId = intent.getStringExtra("campaignId");
		readOnly = intent.getStringExtra("readOnly");

		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		username = preferences.getString("username", "");
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

		final ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("campaignId", campaignId));

		try {
			response = SimpleHttpClient.executeHttpPost("/displayStats",
					postParameters);

			JSONObject jsonobject = new JSONObject(response);
			String anchorn = jsonobject.getString("anchor_id");
			String clname = jsonobject.getString("client_name");
			anchor.setText(anchorn);
			client.setText(clname);
			String Value = jsonobject.getString("values");
			JSONArray jsonArray2 = new JSONArray(Value);
			int jsonArray3=jsonArray2.length();
			for (int j = 0; j < jsonArray3; j++) {

				jsonobject = jsonArray2.getJSONObject(j);

				String ager = jsonobject.getString("age");

				String cntValue = jsonobject.getString("cnt_value");

				count = Integer.parseInt(cntValue);
				ageRange.add(ager);
				countValue.add(count);
				
			}
		} catch (Exception e) {

		}
		// Pie Chart Section Names
		// Color of each Pie Chart Sections
		int[] colors = { Color.BLUE, Color.MAGENTA, Color.GREEN, Color.CYAN,
				Color.RED, Color.YELLOW };

		// Instantiating CategorySeries to plot Pie Chart
		CategorySeries distributionSeries = new CategorySeries(
				"Campaign Statistics");
		int countValueSize= countValue.size();
		for (Integer i = 0; i < countValueSize; i++) {
			// Adding a slice with its values and name to the Pie Chart
			distributionSeries.add(ageRange.get(i), countValue.get(i));
		}

		// Instantiating a renderer for the Pie Chart
		DefaultRenderer defaultRenderer = new DefaultRenderer();
		int countValueSize1=countValue.size();
		for (Integer i = 0; i < countValueSize1; i++) {
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

		// Creating an intent to plot bar chart using dataset and
		// multipleRenderer
		mChartView = ChartFactory.getPieChartView(this, distributionSeries,
				defaultRenderer);
		layout.addView(mChartView);
		// Start Activity
		// startActivity(intent);
	}

	public void goToCampaign(View view) {
		Intent intent = new Intent(this, AnchorActivity.class);
		intent.putExtra("anchorName", anchorName);
		intent.putExtra("clientName", clientName);
		intent.putExtra("campaignId", campaignId);
		intent.putExtra("readOnly", readOnly);
		startActivity(intent);
	}

	public void goToLandingPage(View view) {
		Intent intent = new Intent(this, SearchActivity.class);
		startActivity(intent);
	}
}