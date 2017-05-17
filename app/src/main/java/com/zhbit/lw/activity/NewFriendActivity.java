package com.zhbit.lw.activity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.zhbit.lw.adapter.NewFriendListAdapter;
import com.zhbit.lw.blchat.R;
import com.zhbit.lw.model.Model;
import com.zhbit.lw.model.dao.UserTable;
import com.zhbit.lw.ui.CustomToolbar;

import java.util.List;
import java.util.Map;

import static com.zhbit.lw.model.dao.FriendTable.FRIEND_ID;
import static com.zhbit.lw.model.dao.FriendTable.FRIEND_NAME;
import static com.zhbit.lw.model.dao.UserTable.USER_ID;

public class NewFriendActivity extends ListActivity {

    private ListView newFriendListView;     // 新好友列表视图
    private List<Map<String, Object>> newFriendListData;    // 新好友列表适配器

    private CustomToolbar customToolbar;

    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_friend);

        initView();     // 初始化试图
        initData();     // 初始化数据
        initEvent();    // 初始化点击事件

    }

    // 初始化视图
    private void initView() {
        // 顶部Toolbar
        customToolbar = (CustomToolbar) findViewById(R.id.newFriend_toolbar);
        // 新好友列表
        newFriendListView = getListView();
    }

    // 初始化数据
    private void initData() {
        // 设置顶部Toolbar的Overflow文字
        customToolbar.setOverflowTitle("添加好友");

        // 从ContactFragment中获取userId
        userId = getIntent().getIntExtra(USER_ID, -1);

        newFriendListData = Model.getInstance().getDbManager().getFriendTableDao().getNewFriendListById(userId);

        // 设置新好友列表适配器
        newFriendListView.setAdapter(new NewFriendListAdapter(this, newFriendListData));
    }

    // 初始化点击事件
    private void initEvent() {
        // 设置顶部Toolbar中Overflow的点击事件
        customToolbar.setOnOverflowClickListener(new CustomToolbar.OnOverflowClickListener() {
            @Override
            public void onOverflowClick() {
                startActivity(new Intent(NewFriendActivity.this, AddFriendActivity.class));
            }
        });

        // 添加新好友列表项的点击事件
        newFriendListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(NewFriendActivity.this, FriendInforActivity.class);
                intent.putExtra(USER_ID, userId);
                intent.putExtra(FRIEND_ID, Integer.parseInt(newFriendListData.get(position).get(FRIEND_ID).toString()));
                startActivity(intent);
            }
        });

    }
}
