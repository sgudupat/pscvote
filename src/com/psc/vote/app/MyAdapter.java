package com.psc.vote.app;

import android.content.Context;
import android.content.Intent;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends ArrayAdapter<Product> {

    private final Context context;
    private final ArrayList<Product> itemsArrayList;
    TextView labelView;
    TextView valueView;
    TextView descView;

    public MyAdapter(Context context, ArrayList<Product> itemsArrayList) {
        super(context, R.layout.list_item, itemsArrayList);
        this.context = context;
        this.itemsArrayList = itemsArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 1. Create inflater
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // 2. Get rowView from inflater
        View rowView = inflater.inflate(R.layout.list_item, parent, false);
        // 3. Get the two text view from the rowView
        labelView = (TextView) rowView.findViewById(R.id.anchor_name);
        valueView = (TextView) rowView.findViewById(R.id.client_name);
        descView = (TextView) rowView.findViewById(R.id.campaign_id);

        labelView.setOnClickListener(onClickListener);
        SpannableString content = new SpannableString(itemsArrayList.get(position).getAnchorName());
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        labelView.setText(content);
        // 4. Set the text for textView
        valueView.setText(itemsArrayList.get(position).getClientName());
        descView.setText(itemsArrayList.get(position).getCampaignStatusDescription());
        // 5. return rowView
        return rowView;
    }

    private OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.i("search row click", " you clicked me");
            Log.i("anchor name", labelView.getText().toString());
            Log.i("client name", valueView.getText().toString());
            Log.i("campaign description", descView.getText().toString());
            Intent intent = new Intent(context, ClientDetailActivity.class);
            //TODO: Need all these to pass as params
            //intent.putExtra("anchorName", itemsArrayList.get().getAnchorName());
            //intent.putExtra("clientName", itemsArrayList.get(position).getClientName());
            //intent.putExtra("anchorCreationDate", itemsArrayList.get(position).getAnchorCreationDate());
            //intent.putExtra("websiteURL", itemsArrayList.get(position).getClientWebsiteAddress());
            //intent.putExtra("clientInfo", itemsArrayList.get(position).getClientInfo());
            context.startActivity(intent);
        }
    };
}