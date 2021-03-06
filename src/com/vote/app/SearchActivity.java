package com.vote.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import com.vote.app.AnchorActivity;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class SearchActivity extends Activity {

	ArrayList<Product> items = new ArrayList<Product>();
	String userName;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		userName = preferences.getString("username", "");
		ImageButton rewardButton = (ImageButton) findViewById(R.id.reward);
		rewardButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(view.getContext(),
						RewardActivity.class);
				startActivity(intent);
			}
		});

		ImageButton profileButton = (ImageButton) findViewById(R.id.profileImage);
		profileButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(view.getContext(),
						UserProfileActivity.class);
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// Changes 'back' button action
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// Include the code here
			AlertDialog.Builder alertDialog = new AlertDialog.Builder(
					SearchActivity.this);
			// Setting Dialog Title
			alertDialog.setTitle("Leave application?");
			// Setting Dialog Message
			alertDialog
					.setMessage("Are you sure you want to leave the application?");
			// Setting Icon to Dialog
			// alertDialog.setIcon(R.drawable.dialog_icon);
			// Setting Positive "Yes" Button
			alertDialog.setPositiveButton("YES",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							SharedPreferences preferences = PreferenceManager
									.getDefaultSharedPreferences(SearchActivity.this);
							preferences.edit().clear().commit();
                            launchIntent();
						}
					});
			// Setting Negative "NO" Button
			alertDialog.setNegativeButton("NO",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							// Write your code here to invoke NO event
							dialog.cancel();
						}
					});
			// Showing Alert Message
			alertDialog.show();
		}
		return true;
	}
	  protected void launchIntent() {
			// TODO Auto-generated method stub
	    	Intent intent = new Intent(this, UserHomePageActivity.class);
	    	intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
	    	intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    	startActivity(intent);
			
		}

	private void buildListView() {
		ListView listView = (ListView) findViewById(R.id.list_view);
		// Adding items to list view
		// 1. pass context and data to the custom adapter
		final MyAdapter adapter = new MyAdapter(SearchActivity.this,
				generateData());
		// 2. setListAdapter
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(SearchActivity.this,
						AnchorActivity.class);
				intent.putExtra("anchorName", adapter.getItem(position)
						.getAnchorName());
				intent.putExtra("clientName", adapter.getItem(position)
						.getClientName());
				intent.putExtra("campaignId", adapter.getItem(position)
						.getCampaignId());
				intent.putExtra("readOnly", adapter.getItem(position)
						.isCampaignExpired());
				intent.putExtra("status", adapter.getItem(position).getStatus());
				startActivity(intent);
			}
		});
	}

	private ArrayList<Product> generateData() {
		try {
			// Populate from Server
			String anchors = getAnchors();
			items.clear();
			// Build the list
			JSONArray jsonArray = new JSONArray(anchors);
			int jsonArrays=jsonArray.length();
			for (int i = 0; i < jsonArrays; i++) {
				JSONObject jsonobject = jsonArray.getJSONObject(i);
				String aname = jsonobject.getString("anchor_name");
				String client = jsonobject.getString("client_name");
				String anchorCreationDate = jsonobject
						.getString("creation_date"); // 2015-08-04
				String websiteURL = jsonobject.getString("website_url");
				String clientInfo = jsonobject.getString("client_info");
				String cname = jsonobject.getString("campaigns");
				JSONArray jsonArray2 = new JSONArray(cname);
				int jsonArray3=jsonArray2.length();
				for (int j = 0; j < jsonArray3; j++) {
					JSONObject jsonobject2 = jsonArray2.getJSONObject(j);
					String cid = jsonobject2.getString("campaign_id");
					String endDate = jsonobject2.getString("end_date"); // 2015-08-04
					SimpleDateFormat dateFormat = new SimpleDateFormat(
							"yyyy-MM-dd");
					String status = jsonobject2.getString("status"); // 2015-08-04
					items.add(new Product(aname, client, cid, dateFormat
							.parse(endDate), status, dateFormat
							.parse(anchorCreationDate), websiteURL, clientInfo));
				}
			}
		} catch (Exception e) {
			AlertDialog.Builder alertDialog = new AlertDialog.Builder(
					SearchActivity.this);
			// Setting Dialog Title
			alertDialog.setTitle("Error Message");
			// Setting Dialog Message
			alertDialog.setMessage(e.getMessage());
			// Setting Icon to Dialog
			// alertDialog.setIcon(R.drawable.dialog_icon);
			// Setting Positive "Yes" Button
			alertDialog.setPositiveButton("Ok",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							finish();
						}
					});
			// Setting Negative "NO" Button
			alertDialog.setNegativeButton("Cancel",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							// Write your code here to invoke NO event
							dialog.cancel();
						}
					});
			// Showing Alert Message
			alertDialog.show();
		}
		return items;
	}

	public void searchResult(View view) {
		buildListView();
	}

	public void recentAnchors(View view) {
		buildListView1();
	}

	public void trendingAnchors(View view) {
		EditText input = (EditText) findViewById(R.id.inputSearch);
		input.setText("");
		buildListView();
	}

	private String getAnchors() {		
		EditText input = (EditText) findViewById(R.id.inputSearch);
		final ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("anchorName", input.getText()
				.toString()));
		try {
			String response2 = SimpleHttpClient.executeHttpPost(
					"/searchAnchor", postParameters);			
			return response2;
		} catch (Exception e) {
			String errorMsg = e.getMessage() + "";
			Log.e("LoginPageActivity", errorMsg);
			return "fail";
		}
	}

	public void showProfile(View view) {

	}

	public void showRewardsSummary(View view) {

	}

	private void buildListView1() {
		ListView listView = (ListView) findViewById(R.id.list_view);
		// Adding items to list view
		// 1. pass context and data to the custom adapter

		final MyAdapter adapter = new MyAdapter(SearchActivity.this,
				generateData1());
		// 2. setListAdapter
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(SearchActivity.this,
						AnchorActivity.class);
				intent.putExtra("anchorName", adapter.getItem(position)
						.getAnchorName());
				intent.putExtra("clientName", adapter.getItem(position)
						.getClientName());
				intent.putExtra("campaignId", adapter.getItem(position)
						.getCampaignId());
				intent.putExtra("readOnly", adapter.getItem(position)
						.isCampaignExpired());
				intent.putExtra("status", adapter.getItem(position).getStatus());
				startActivity(intent);
			}
		});
	}

	private ArrayList<Product> generateData1() {
		try {
			// Populate from Server
			String anchors = getAnchors1();
			items.clear();
			// Build the list
			JSONArray jsonArrayr = new JSONArray(anchors);
			int jsonArrays2=jsonArrayr.length();
			for (int i = 0; i < jsonArrays2; i++) {
				if (i < 5) {
					JSONObject jsonobject = jsonArrayr.getJSONObject(i);
					String aname = jsonobject.getString("anchor_name");
					String client = jsonobject.getString("client_name");
					String anchorCreationDate = jsonobject
							.getString("creation_date"); // 2015-08-04
					String websiteURL = jsonobject.getString("website_url");
					String clientInfo = jsonobject.getString("client_info");
					String cname = jsonobject.getString("campaigns");
					JSONArray jsonArrayr2 = new JSONArray(cname);
					for (int j = 0; j < jsonArrayr2.length(); j++) {
						JSONObject jsonobject2 = jsonArrayr2.getJSONObject(j);
						String cid = jsonobject2.getString("campaign_id");
						String endDate = jsonobject2.getString("end_date"); // 2015-08-04
						SimpleDateFormat dateFormat = new SimpleDateFormat(
								"yyyy-MM-dd");
						String status = jsonobject2.getString("status"); // 2015-08-04
						items.add(new Product(aname, client, cid, dateFormat
								.parse(endDate), status, dateFormat
								.parse(anchorCreationDate), websiteURL,
								clientInfo));
					}
				}
			}

		} catch (Exception e) {
			Log.e("register", e.getMessage() + "");
		}
		return items;
	}

	private String getAnchors1() {		

		final ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("userName", userName));
		try {
			String response2 = SimpleHttpClient.executeHttpPost(
					"/recentSearchAnchors", postParameters);			
			return response2;
		} catch (Exception e) {			
			return "fail";
		}
	}

}
