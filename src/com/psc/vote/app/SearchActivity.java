package com.psc.vote.app;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class SearchActivity extends Activity {
	
	 // List view
    private ListView lv;
     
    // Listview Adapter
    ArrayAdapter<String> adapter;
     
    // Search EditText
    EditText inputSearch;
     
     
    // ArrayList for Listview
    ArrayList<HashMap<String, String>> productList;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i("SearchActivity", "inside user sign in page");
		setContentView(R.layout.search);
	
	 String products[] = {"Dell Inspiron", "HTC One X", "HTC Wildfire S", "HTC Sense", "HTC Sensation XE",
             "iPhone 4S", "Samsung Galaxy Note 800",
             "Samsung Galaxy S3", "MacBook Air", "Mac Mini", "MacBook Pro"};

		lv = (ListView) findViewById(R.id.list_view);
		inputSearch = (EditText) findViewById(R.id.inputSearch);

			// Adding items to listview
			adapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.product_name, products);
			lv.setAdapter(adapter); 
			
			lv.setOnItemClickListener(new ListClickHandler());
			
			/**
			* Enabling Search Filter
			* */
			inputSearch.addTextChangedListener(new TextWatcher() {
			
			
			public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
			// When user changed the Text
				SearchActivity.this.adapter.getFilter().filter(cs);   
			}
			
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			 int arg3) {
			// TODO Auto-generated method stub
			
			}
			
			
			public void afterTextChanged(Editable arg0) {
			// TODO Auto-generated method stub                          
			}
			});
	}
	
	public class ListClickHandler implements OnItemClickListener{
		 
	  
	    public void onItemClick(AdapterView<?> adapter, View view, int position, long arg3) {
	        // TODO Auto-generated method stub
	        TextView listText = (TextView) view.findViewById(R.id.product_name);
	        String text = listText.getText().toString();
	        Intent intent = new Intent(SearchActivity.this, ShowActivity.class);
	        intent.putExtra("selected-item", text);
	        startActivity(intent);  
	         
	    }

		
	
	     
	}

}
