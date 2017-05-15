package com.zhbit.lw.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zhbit.lw.blchat.R;

import java.util.List;
import java.util.Map;

/**
 * Created by wjh on 17-5-13.
 */

public class NewFriendListAdapter extends BaseAdapter{

    private Context context;
    private List<Map<String, Object>> newFriendListData;

    public NewFriendListAdapter(Context context, List<Map<String, Object>> newFriendListData) {
        this.context = context;
        this.newFriendListData = newFriendListData;
    }

    @Override
    public int getCount() {
        return newFriendListData.size();
    }

    @Override
    public Object getItem(int position) {
        return newFriendListData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = View.inflate(context, R.layout.listview_row_newfriend, null);
        TextView tvUserName = (TextView) convertView.findViewById(R.id.lvRow_newfriend_userName);
        TextView tvRequestMsg = (TextView) convertView.findViewById(R.id.lvRow_newfriend_requestMsg);
        tvUserName.setText(newFriendListData.get(position).get("userName").toString());
        tvRequestMsg.setText(newFriendListData.get(position).get("requestMsg").toString());

        return convertView;
    }
}
