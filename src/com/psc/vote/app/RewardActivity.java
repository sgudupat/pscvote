package com.psc.vote.app;

import android.app.Activity;
import android.content.Context;
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
    final Context context = this;
    ArrayList<Reward> reward = new ArrayList<Reward>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("rewardActivity", "inside user sign in page");
        setContentView(R.layout.rewards);
        rewardDetails();
        buildListView();
    }

    private String rewardDetails() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String sname = preferences.getString("username", "");
        Log.i("session name", sname);
        final ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
        postParameters.add(new BasicNameValuePair("username", sname));
        try {
            Log.i("LoginPageActivity", "try");
            String response2 = SimpleHttpClient.executeHttpPost("/myRewards", postParameters);
            Log.i("Response 2:", response2);
            return response2;
        } catch (Exception e) {
            String errorMsg = e.getMessage() + "";
            Log.e("LoginPageActivity", errorMsg);
            return "fail";
        }
    }

    private void buildListView() {
        ListView listView = (ListView) findViewById(R.id.reward_view);
        // Adding items to listview
        // 1. pass context and data to the custom adapter
        Log.i("inside buildlist", "listbuitl");
        RewardAdapter adapter = new RewardAdapter(RewardActivity.this, generateData());
        //2. setListAdapter
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(RewardActivity.this, RewardDetailActivity.class);
                //String  rewardDetail = rewardDetails();
                TextView rid = (TextView) view.findViewById(R.id.reward_name);
                String rewardId = rid.getText().toString();
                for (Reward temp : reward) {
                    if (temp.getRewardName().equals(rewardId)) {
                        Log.i("reward name", temp.getRewardName());
                        Log.i("reward start date", temp.getRewardDate());
                        Log.i("reward end date", temp.getRewardEdate());
                        Log.i("descritpion name", temp.getRewardDescription());
                        intent.putExtra("rid", temp.getRewardName());
                        intent.putExtra("rewarddesc", temp.getRewardDescription());
                        intent.putExtra("rewardsdate", temp.getRewardDate());
                        intent.putExtra("rewardedate", temp.getRewardEdate());
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

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonobject = jsonArray.getJSONObject(i);
                String rname = jsonobject.getString("reward_id");
                String rdate = jsonobject.getString("start_date");
                String edate = jsonobject.getString("end_date");
                String rdesc = jsonobject.getString("reward_desc");
                Log.i("reward name", rname);
                Log.i("reward date", rdate);
                Log.i("descritpion name", rdesc);
                reward.add(new Reward(rname, rdate, edate, rdesc));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return reward;
    }
}