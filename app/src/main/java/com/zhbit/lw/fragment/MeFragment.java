package com.zhbit.lw.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhbit.lw.blchat.R;
import com.zhbit.lw.entity.UserEntity;
import com.zhbit.lw.model.Model;

/**
 * Created by wjh on 17-5-6.
 */

public class MeFragment extends Fragment{

    private View view;
    private ImageView ivUserHead;
    private TextView tvUserName, tvUserAccount;

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
    }

    private void initData() {
        // 从数据库获取数据
        UserEntity userEntity = Model.getInstance().getDbManager(getActivity()).getUserTableDao().getUserInforById();

        // 判断是否获取成功
        if (userEntity == null) {
            Toast.makeText(getActivity(), "Null", Toast.LENGTH_SHORT).show();
        }else{
            tvUserName.setText(userEntity.getUserName());
            tvUserAccount.setText("帐号：" + userEntity.getUserAccount());
        }
    }

    private void initEvent() {
    }

}
