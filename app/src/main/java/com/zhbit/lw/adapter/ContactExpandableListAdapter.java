package com.zhbit.lw.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.zhbit.lw.blchat.R;

import java.util.List;
import java.util.Map;

/**
 * Created by wjh on 17-5-14.
 */

public class ContactExpandableListAdapter extends BaseExpandableListAdapter{

    private Context context;
    private List<String> parentList;
    private List<List<String>> childList;

    public ContactExpandableListAdapter(Context context, List<String> parentList, List<List<String>> childList) {
        this.context = context;
        this.parentList = parentList;
        this.childList = childList;
    }

    @Override
    public int getGroupCount() {
        return parentList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childList.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return parentList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childList.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.expandlistview_row_group, null);
        }
        // 设置分组的名称和人数
        TextView groupName = (TextView) convertView.findViewById(R.id.expandLvRow_group_name);
        TextView groupCount = (TextView) convertView.findViewById(R.id.expandLvRow_group_count);
        groupName.setText(parentList.get(groupPosition));
        groupCount.setText(childList.get(groupPosition).size() + "人");

        convertView.setTag(R.id.expandLvRow_group_name, groupPosition);
        convertView.setTag(R.id.expandLvRow_contact_name, -1);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.expandlistview_row_contact, null);
        }
        TextView contactName = (TextView) convertView.findViewById(R.id.expandLvRow_contact_name);
        contactName.setText(childList.get(groupPosition).get(childPosition));

        convertView.setTag(R.id.expandLvRow_group_name, groupPosition);
        convertView.setTag(R.id.expandLvRow_contact_name, childPosition);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
