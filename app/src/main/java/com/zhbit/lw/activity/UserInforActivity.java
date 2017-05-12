package com.zhbit.lw.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.zhbit.lw.blchat.R;

public class UserInforActivity extends AppCompatActivity {

    private TextView tvUserName;    // 用户名称视图
    private Toolbar toolbar;        // 顶部Toolbar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_infor);

        initView();       // 初始化试图
        initData();       // 初始化数据
        initEvent();      // 初始化事件监听器


    }

    // 初始化试图
    private void initView() {
        tvUserName = (TextView) findViewById(R.id.userinfor_userName);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    // 初始化数据
    private void initData() {
        tvUserName.setText(getIntent().getStringExtra("userName"));
    }

    // 初始化点击事件
    private void initEvent() {

    }

}
