package com.psc.vote.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class RewardAdapter extends ArrayAdapter<Reward> {
    private final Context context;
    private final ArrayList<Reward> rewardList;

    public RewardAdapter(Context context, ArrayList<Reward> rewardList) {
        super(context, R.layout.rewards_item, rewardList);
        this.context = context;
        this.rewardList = rewardList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 1. Create inflater
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // 2. Get rowView from inflater
        View rowView = inflater.inflate(R.layout.rewards_item, parent, false);
        // 3. Get the two text view from the rowView
        TextView labelView = (TextView) rowView.findViewById(R.id.reward_name);
        TextView valueView = (TextView) rowView.findViewById(R.id.reward_date);
        TextView descView = (TextView) rowView.findViewById(R.id.reward_description);
        // 4. Set the text for textView
        labelView.setText(rewardList.get(position).getRewardName());
        valueView.setText(rewardList.get(position).getRewardDate());
        descView.setText(rewardList.get(position).getRewardDescription());
        // 5. return rowView
        return rowView;
    }
}