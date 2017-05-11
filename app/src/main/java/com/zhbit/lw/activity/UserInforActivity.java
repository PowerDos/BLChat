package com.zhbit.lw.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.zhbit.lw.blchat.R;

public class UserInforActivity extends AppCompatActivity {

    private TextView tvUserName;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_infor);

        initView();
        initData();
        initEvent();


    }

    private void initView() {
        tvUserName = (TextView) findViewById(R.id.userinfor_userName);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    private void initData() {
        tvUserName.setText(getIntent().getStringExtra("userName"));
    }

    private void initEvent() {

    }

}
