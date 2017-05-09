package com.zhbit.lw.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.zhbit.lw.blchat.Moment;
import com.zhbit.lw.blchat.R;

/**
 * Created by wjh on 17-5-6.
 */

public class FoundFragment extends Fragment implements View.OnClickListener{
    protected LinearLayout enterMoment;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.found_layout, container, false);
        enterMoment = (LinearLayout) view.findViewById(R.id.enterMoment);
        enterMoment.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.enterMoment:
                Intent moment = new Intent(getActivity(), Moment.class);
                startActivity(moment);
                break;
        }
    }
}
