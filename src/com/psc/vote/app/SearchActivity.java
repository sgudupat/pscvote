package com.psc.vote.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class SearchActivity extends Activity {

    // Search EditText
    EditText inputSearch;

    ArrayList<Product> items = new ArrayList<Product>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("SearchActivity", "inside user sign in page");
        setContentView(R.layout.search);

        String products[] = {"Dell Inspiron", "HTC One X", "HTC Wildfire S", "HTC Sense", "HTC Sensation XE",
                "iPhone 4S", "Samsung Galaxy Note 800",
                "Samsung Galaxy S3", "MacBook Air", "Mac Mini", "MacBook Pro"};

        //lv = (ListView) findViewById(R.id.list_view);
        final ListView listView = (ListView) findViewById(R.id.list_view);

        // Adding items to listview
        //adapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.product_name, products);
        // 1. pass context and data to the custom adapter
        MyAdapter adapter = new MyAdapter(this, generateData());

        //2. setListAdapter
        listView.setAdapter(adapter);

        //lv.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view,
                                                                    int position, long id) {
                                                TextView listText = (TextView) view.findViewById(R.id.product_name);
                                                String text = listText.getText().toString();
                                                Intent intent = new Intent(SearchActivity.this, ShowActivity.class);
                                                intent.putExtra("selected-item", text);
                                                startActivity(intent);
                                            }
                                        }
        );

        inputSearch = (EditText) findViewById(R.id.inputSearch);
        Log.i("textchanged", "inside on text change");

        /**
         * Enabling Search Filter
         * */
        inputSearch.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                int textlength = cs.length();
                //SearchActivity.this.adapter.getFilter().filter(cs);
                ArrayList<Product> temp = new ArrayList<Product>();
                for (Product p : items) {
                    if (textlength <= p.getPname().length()) {
                        if (p.getPname().toLowerCase().contains(cs.toString().toLowerCase())) {
                            temp.add(p);
                        }
                    }
                }
                MyAdapter adapter1 = new MyAdapter(SearchActivity.this, temp);
                listView.setAdapter(adapter1);
            }

            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });
    }

    private ArrayList<Product> generateData() {
        items.add(new Product("Dell Inspiron", "Five thousand", "Dell phone"));
        items.add(new Product("HTC One X", "Ten thousand", "Htc mega pixel"));
        items.add(new Product("HTC Wildfire S", "Eight thousand", "Htc super model"));
        items.add(new Product("HTC Sense", "Eight thousand", "Htc super model"));
        items.add(new Product("HTC Sensation XE", "Eight thousand", "Htc super model"));
        items.add(new Product("Samsung Galaxy Note 800", "Eight thousand", "samsung super model"));
        items.add(new Product("Samsung Galaxy S3", "Eight thousand", "smasung galxy model"));
        items.add(new Product("HTC Wildfire S", "Eight thousand", "samsung  super model"));
        return items;
    }
	 public void searchResult(View view) {
		 
		 EditText input=(EditText) findViewById(R.id.inputSearch);
		  try {
	            Log.i("triggerLogin:", "triggerLogin");
	            final ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
	            postParameters.add(new BasicNameValuePair("anchorName", input.getText().toString()));
	          
	            final String response2 = null;
	            try {
	                Log.i("LoginPageActivity", "try");
	                new Thread(new Runnable() {
	                    public void run() {
	                        Log.i("Response 2:", "In New Thread");
	                        try {
	                             String response2 = SimpleHttpClient.executeHttpPost("/searchAnchor", postParameters);
	                             Log.i("Response 2:", response2);
	                           JSONObject jObject = new JSONObject(response2);
	                           Log.d("dfd",jObject.toString());
	           	             // JSONArray jArray = jObject.getJSONArray("myArray");
	                          //  
	                        } catch (Exception e) {
	                            Log.i("Response 2:Error:", e.getMessage());
	                        }
	                    }
	                }).start();
	                Log.i("LoginPageActivity", "call done");
	             /*   String res = response.toString();
		               Log.i("LoginPageActivity", res);
	               String resp = res.replaceAll("\\s+", "");
	                Log.i("LoginPageActivity", resp);*/
	                
	            } catch (Exception e) {
	                e.printStackTrace();
	                String errorMsg = e.getMessage();
	                Log.e("LoginPageActivity", errorMsg);
	            }
	            Log.i("After process:", "Done");
	        } catch (Exception e) {
	        }
		 
	 
	 }
}