package com.zhbit.lw.blchat;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * ChatFragment: 聊天列表界面
 * Created by wjh on 17-5-6.
 */

public class ChatFragment extends Fragment{

    private String mTitle = "Default";
    public static final String TITLE = "title";


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (getArguments() != null) {
            mTitle = getArguments().getString(TITLE);
        }

        TextView tv = new TextView(getActivity());
        tv.setTextSize(30);
        tv.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
        tv.setText(mTitle);
        tv.setGravity(Gravity.CENTER);

        return tv;
    }

}
