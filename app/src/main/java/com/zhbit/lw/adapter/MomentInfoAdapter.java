package com.zhbit.lw.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.zhbit.lw.blchat.R;

/**
 * Created by fl5900 on 2017/5/9.
 */

public class MomentInfoAdapter extends BaseAdapter{
    protected Context context;
    protected Cursor cursor;
    @Override
    public int getCount() {
        return 0;
    }

    public MomentInfoAdapter(Context ctxt, Cursor csr){
        context = ctxt;
        cursor = csr;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_moment_content, parent, false);
        }
        return convertView;
    }
}
