package edu.ewubd.quizzler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class LeaderboardAdapter extends BaseAdapter {
    private Context context;
    private List<LeaderboardItem> leaderList;

    public LeaderboardAdapter(Context context, List<LeaderboardItem> leaderList) {
        this.context = context;
        this.leaderList = leaderList;
    }

    @Override
    public int getCount() {
        return leaderList.size();
    }

    @Override
    public Object getItem(int position) {
        return leaderList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.leaderboard_item_layout, parent, false);
        }

        TextView name = convertView.findViewById(R.id.name);
        TextView score = convertView.findViewById(R.id.score);
        TextView rank = convertView.findViewById(R.id.rank);

        LeaderboardItem list = leaderList.get(position);
        name.setText(list.getName());
        score.setText(list.getScore());
        rank.setText(list.getRank());

        return convertView;
    }
}
