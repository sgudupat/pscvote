package com.vote.app;

import android.content.Context;
import android.content.Intent;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

        labelView.setOnClickListener(new AnchorListener(
                itemsArrayList.get(position).getAnchorName()
                , itemsArrayList.get(position).getClientName()
                , itemsArrayList.get(position).getAnchorCreationDate()
                , itemsArrayList.get(position).getClientWebsiteAddress()
                , itemsArrayList.get(position).getClientInfo()));
        SpannableString content = new SpannableString(itemsArrayList.get(position).getAnchorName());
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        labelView.setText(content);
        // 4. Set the text for textView
        valueView.setText(itemsArrayList.get(position).getClientName());
        descView.setText(itemsArrayList.get(position).getCampaignStatusDescription());
        // 5. return rowView
        return rowView;
    }

    public class AnchorListener implements View.OnClickListener {
        private String anchorName;
        private String clientName;
        private Date anchorCreationDate;
        private String websiteURL;
        private String clientInfo;

        public AnchorListener(String anchorName, String clientName, Date anchorCreationDate, String websiteURL, String clientInfo) {
            this.anchorName = anchorName;
            this.clientName = clientName;
            this.anchorCreationDate = anchorCreationDate;
            this.websiteURL = websiteURL;
            this.clientInfo = clientInfo;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, ClientDetailActivity.class);
            intent.putExtra("anchorName", anchorName);
            intent.putExtra("clientName", clientName);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            intent.putExtra("anchorCreationDate", dateFormat.format(anchorCreationDate));
            intent.putExtra("websiteURL", websiteURL);
            intent.putExtra("clientInfo", clientInfo);
            context.startActivity(intent);
        }
    }
}