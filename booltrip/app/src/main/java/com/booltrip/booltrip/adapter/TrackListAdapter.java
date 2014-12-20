package com.booltrip.booltrip.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.booltrip.booltrip.R;
import com.booltrip.booltrip.model.Track;

import java.util.ArrayList;

public class TrackListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Track> tracksItems;
    private ImageView imageView;

    public TrackListAdapter(Context context, ArrayList<Track> tracksItems){
        this.context = context;
        this.tracksItems = tracksItems;
    }

    @Override
    public int getCount() {
        return tracksItems.size();
    }

    @Override
    public Object getItem(int position) {
        return tracksItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.track_item, null);
        }
        TextView txtTitle = (TextView) convertView.findViewById(R.id.TrackTitle);
        tracksItems.get(position).setName("Trajet "+(position + 1));
        txtTitle.setText("Trajet "+(position + 1));
        TextView txtCost = (TextView) convertView.findViewById(R.id.TrackCost);
        txtCost.setText(tracksItems.get(position).getConsumption() + " g CO²");


        return convertView;
    }
}
