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
import com.booltrip.booltrip.model.User;

import java.util.ArrayList;

/**
 * Created by tpariau on 13/12/2014.
 */
public class UserListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<User> userItems;
    private ImageView imageView;

    public UserListAdapter(Context context, ArrayList<User> userItems){
        this.context = context;
        this.userItems = userItems;
    }

    @Override
    public int getCount() {
        return userItems.size();
    }

    @Override
    public Object getItem(int position) {
        return userItems.get(position);
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
            convertView = mInflater.inflate(R.layout.user_item, null);
        }
        TextView txtTitle = (TextView) convertView.findViewById(R.id.UserName);
        txtTitle.setText(userItems.get(position).getName());

        return convertView;
    }
}
