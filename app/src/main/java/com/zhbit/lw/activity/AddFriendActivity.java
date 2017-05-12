package com.zhbit.lw.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.zhbit.lw.blchat.R;

public class AddFriendActivity extends AppCompatActivity {

    private Toolbar toolbar;    // 顶部toolbar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);

        initView();     // 初始化视图
        initData();     // 初始化数据
        initEvent();    // 初始化点击事件

    }

    // 初始化视图
    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);

    }

    // 初始化数据
    private void initData() {
    }

    // 初始化点击事件
    private void initEvent() {
    }


}
