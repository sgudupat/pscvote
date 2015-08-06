package com.psc.vote.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends ArrayAdapter<Product> {

    private final Context context;
    private final ArrayList<Product> itemsArrayList;

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
        TextView labelView = (TextView) rowView.findViewById(R.id.anchor_name);
        TextView valueView = (TextView) rowView.findViewById(R.id.client_name);
        TextView descView = (TextView) rowView.findViewById(R.id.campaign_id);
        // 4. Set the text for textView
        labelView.setText(itemsArrayList.get(position).getAnchorName());
        valueView.setText(itemsArrayList.get(position).getClientName());
        descView.setText(itemsArrayList.get(position).getCampaignId());
        // 5. return rowView
        return rowView;
    }
}