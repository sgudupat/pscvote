package com.psc.vote.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchActivity extends Activity {

    ArrayList<Product> items = new ArrayList<Product>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("SearchActivity", "inside user sign in page");
        setContentView(R.layout.search);
        ////buildListView();
    }

    private void buildListView() {
        ListView listView = (ListView) findViewById(R.id.list_view);
        // Adding items to listview
        // 1. pass context and data to the custom adapter
        Log.i("inside buildlist", "listbuitl");
        MyAdapter adapter = new MyAdapter(SearchActivity.this, generateData());
        //2. setListAdapter
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView listText = (TextView) view.findViewById(R.id.campaign_id);
                String text = listText.getText().toString();
                Intent intent = new Intent(SearchActivity.this, AnchorActivity.class);
                intent.putExtra("campaignid", text);
                //intent.putExtra("selected-item", text);
                startActivity(intent);
            }
        });
    }

    private ArrayList<Product> generateData() {
        try {
            //Populate from Server
            String anchors = getAnchors();
            //Build the list
            JSONArray jsonArray = new JSONArray(anchors);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonobject = jsonArray.getJSONObject(i);
                String aname = jsonobject.getString("anchor_name");
                String client = jsonobject.getString("client_name");
                String cname = jsonobject.getString("campaigns");
                Log.i("anchor name", aname);
                Log.i("client name", client);
                Log.i("campaign name", cname);
                JSONArray jsonArray2 = new JSONArray(cname);
                for (int j = 0; j < jsonArray2.length(); j++) {
                    Log.i("client id", "inside campaign");
                    JSONObject jsonobject2 = jsonArray2.getJSONObject(j);
                    Log.i("client id", "json object built");
                    String cid = jsonobject2.getString("campaign_id");
                    Log.i("client id", cid);
                    items.add(new Product(aname, client, cid));
                }
            }
        } catch (Exception e) {
        }
        return items;
    }


    public void searchResult(View view) {
        buildListView();
    }

    private String getAnchors() {
        EditText input = (EditText) findViewById(R.id.inputSearch);
        Log.i("triggerLogin:", "triggerLogin");
        final ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
        postParameters.add(new BasicNameValuePair("anchorName", input.getText().toString()));
        try {
            Log.i("LoginPageActivity", "try");
            String response2 = SimpleHttpClient.executeHttpPost("/searchAnchor", postParameters);
            Log.i("Response 2:", response2);
            return response2;
        } catch (Exception e) {
            String errorMsg = e.getMessage() + "";
            Log.e("LoginPageActivity", errorMsg);
            return "fail";
        }
    }
}
