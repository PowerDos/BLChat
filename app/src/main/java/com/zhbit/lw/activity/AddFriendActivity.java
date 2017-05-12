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

        initView();
        initData();
        initEvent();

    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);

    }

    private void initData() {
    }

    private void initEvent() {
    }


}
