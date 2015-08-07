package com.psc.vote.app;

import java.util.ArrayList;
import java.util.Iterator;

import org.achartengine.ChartFactory;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

public class CampaignActivity extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i("SignInPageActivity", "inside user campaign page");
		setContentView(R.layout.campaign);
		openChart();
	}
	 private void openChart() {
		 String response="";
		 ArrayList<String> ageRange = new ArrayList<String>();
		 ArrayList<Integer> countValue = new ArrayList<Integer>();
		 
		int count=0;
		int [] cnt={};
		Log.i("displayCampaign:", "displayCampaign");
			final ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
			postParameters.add(new BasicNameValuePair("campaignId","loreal_camp1" ));

			try {
				response = SimpleHttpClient.executeHttpPost("/displayStats",
						postParameters);

				Log.i("Response:", response);
				

				   JSONObject jsonobject = new JSONObject(response);
				   String Value = jsonobject.getString("values");
					JSONArray jsonArray2=new JSONArray(Value);
					for (int j = 0; j < jsonArray2.length(); j++) {
						Log.i("inside", "inside campaign");
						 jsonobject = jsonArray2.getJSONObject(j);
						Log.i("campaign ", "json object built");
						String ager = jsonobject.getString("age");
						Log.i("agerane", ager);
						 String cntValue = jsonobject.getString("cnt_value");
						Log.i("client id", cntValue);
						count=Integer.parseInt(cntValue);
						ageRange.add(ager);
						countValue.add(count);
					}
				/* String ager= jsonobject.getString("age");
				count=Integer.parseInt(cntValue);
				ageRange.add(ager);
				countValue.add(count);*/

			} catch (Exception e) {
				Log.i("Response 2:Error:", e.getMessage());
				
			}

		
			
		 
		 
	        // Pie Chart Section Names
	      /*  String[] code = new String[] {
	            "Eclair & Older", "Froyo", "Gingerbread", "Honeycomb",
	            "IceCream Sandwich", "Jelly Bean"
	        };
	 
	        // Pie Chart Section Value
	        double[] distribution = { 2.9, 19, 55.8, 1.9, 23.7, 1.8 } ;*/
	 
	        // Color of each Pie Chart Sections
	        int[] colors = { Color.BLUE, Color.MAGENTA, Color.GREEN, Color.CYAN, Color.RED,
	                        Color.YELLOW };
	 
	        // Instantiating CategorySeries to plot Pie Chart
	        CategorySeries distributionSeries = new CategorySeries(" Android version distribution as on October 1, 2012");
	        for(Integer i=0 ;i < countValue.size();i++){
	            // Adding a slice with its values and name to the Pie Chart
	            distributionSeries.add(ageRange.get(i), countValue.get(i));
	        }
	 
	        // Instantiating a renderer for the Pie Chart
	        DefaultRenderer defaultRenderer  = new DefaultRenderer();
	        for(Integer i = 0 ;i<countValue.size();i++){
	            SimpleSeriesRenderer seriesRenderer = new SimpleSeriesRenderer();
	            seriesRenderer.setColor(colors[i]);
	            seriesRenderer.setDisplayChartValues(true);
	            // Adding a renderer for a slice
	            defaultRenderer.addSeriesRenderer(seriesRenderer);
	        }
	 
	        defaultRenderer.setChartTitle("Android version distribution as on October 1, 2012 ");
	        defaultRenderer.setChartTitleTextSize(20);
	        defaultRenderer.setZoomButtonsVisible(true);
	 
	        // Creating an intent to plot bar chart using dataset and multipleRenderer
	        Intent intent = ChartFactory.getPieChartIntent(getBaseContext(), distributionSeries , defaultRenderer, "AChartEnginePieChartDemo");
	 
	        // Start Activity
	        startActivity(intent);
	 
	    }


}
