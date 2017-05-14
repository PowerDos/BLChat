package com.zhbit.lw.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.zhbit.lw.adapter.MomentInfoAdapter;
import com.zhbit.lw.blchat.R;
import com.zhbit.lw.entity.MomentInfo;
import com.zhbit.lw.model.Model;
import com.zhbit.lw.ui.CustomToolbar;

import java.util.List;

public class MomentActivity extends Activity {

    private CustomToolbar customToolbar;    // 顶部Toolbar
    private ListView lvMoment;          // 朋友圈列表
    private List<MomentInfo> momentInfos;
    private MomentInfoAdapter momentInfoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moment);

        initView();         // 初始化视图
        initData();         // 初始化数据
        initEvent();        // 初始化点击事件
    }

    // 初始化视图
    private void initView() {
        // 实例化顶部Toolbar
        customToolbar = (CustomToolbar) findViewById(R.id.moment_toolbar);

        // 实例化朋友圈列表
        lvMoment = (ListView) findViewById(R.id.lv_moment_info);
        lvMoment.addHeaderView(this.getLayoutInflater().inflate(R.layout.layout_moment_heading,null),
                null,false);
    }

    // 初始化数据
    private void initData() {
        // 获取朋友圈数据
        momentInfos = Model.getInstance().getDbManager(this).getMomentTableDao().getAllMomentInfo();
        momentInfoAdapter = new MomentInfoAdapter(MomentActivity.this, momentInfos);

        //设置Adapter显示
        lvMoment.setAdapter(momentInfoAdapter);
    }

    // 初始化点击事件
    private void initEvent() {
    }

}
