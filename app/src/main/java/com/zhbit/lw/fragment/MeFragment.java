package com.zhbit.lw.fragment;

import android.content.Intent;
import android.media.MediaRouter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhbit.lw.activity.UserInforActivity;
import com.zhbit.lw.blchat.R;
import com.zhbit.lw.model.bean.UserInfo;
import com.zhbit.lw.model.Model;
import com.zhbit.lw.model.dao.UserTable;

/**
 * Created by wjh on 17-5-6.
 */

public class MeFragment extends Fragment{

    private View view;
    private ImageView ivUserHead;
    private TextView tvUserName, tvUserAccount;
    private LinearLayout layoutUserInfor;

    private int userId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_me, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();
        initData();
        initEvent();

    }

    private void initView() {
        ivUserHead = (ImageView) view.findViewById(R.id.userInfor_userHead);
        tvUserName = (TextView) view.findViewById(R.id.userInfor_userName);
        tvUserAccount = (TextView) view.findViewById(R.id.userInfor_userAccount);

        layoutUserInfor = (LinearLayout) view.findViewById(R.id.lineLayoutEditMyInfo);
    }

    private void initData() {
        // 从数据库获取数据
        userId = 1;
        UserInfo userInfo = Model.getInstance().getDbManager().getUserTableDao().getUserInforByAccount(userId);

        tvUserName.setText(userInfo.getUserName());
        tvUserAccount.setText("帐号：" + userInfo.getUserAccount());

    }

    private void initEvent() {
        layoutUserInfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UserInforActivity.class);
                intent.putExtra(UserTable.USER_ID, 1);
                startActivity(intent);
            }
        });
    }

}
