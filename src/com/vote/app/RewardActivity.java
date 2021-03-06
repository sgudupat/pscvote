package com.vote.app;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RewardActivity extends Activity {

	ArrayList<Reward> reward = new ArrayList<Reward>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rewards);
		rewardDetails();
		buildListView();
	}

	private String rewardDetails() {		
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		String sname = preferences.getString("username", "");
		final ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("username", sname));
		try {
			String response2 = SimpleHttpClient.executeHttpPost("/myRewards",
					postParameters);			
			return response2;
		} catch (Exception e) { 

			return "fail";
		}
	}

	private void buildListView() {
		ListView listView = (ListView) findViewById(R.id.reward_view);
		// Adding items to listview
		// 1. pass context and data to the custom adapter
		RewardAdapter adapter = new RewardAdapter(RewardActivity.this,
				generateData());
		// 2. setListAdapter
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(RewardActivity.this,
						RewardDetailActivity.class);
				// String rewardDetail = rewardDetails();
				TextView rid = (TextView) view.findViewById(R.id.reward_name);
				String rewardId = rid.getText().toString();
				for (Reward temp : reward) {
					if (temp.getRewardName().equals(rewardId)) {
						intent.putExtra("rid", temp.getRewardName());
						intent.putExtra("rewarddesc",
								temp.getRewardDescription());
						intent.putExtra("rewardsdate", temp.getRewardDate());
						intent.putExtra("rewardedate", temp.getRewardEdate());
						intent.putExtra("imageName", temp.getImageName());
					}
				}
				startActivity(intent);
			}
		});
	}

	private ArrayList<Reward> generateData() {
		String rewardDetail = rewardDetails();
		JSONArray jsonArray;
		try {	
			jsonArray = new JSONArray(rewardDetail);
			int jsonArray1=jsonArray.length();

			for (int i = 0; i < jsonArray1; i++) {
				JSONObject jsonobject = jsonArray.getJSONObject(i);
				String rname = jsonobject.getString("reward_id");
				String rdate = jsonobject.getString("start_date");
				String edate = jsonobject.getString("end_date");
				String rdesc = jsonobject.getString("reward_desc");
				String image = jsonobject.getString("reward_image");
				reward.add(new Reward(rname, rdate, edate, rdesc, image));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return reward;
	}
}